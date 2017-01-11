package com.shhxzq.fin.simulator.biz.aop;

import com.shhxzq.fin.simulator.biz.annotation.CacheDelete;
import com.shhxzq.fin.simulator.biz.annotation.CacheDeleteAll;
import com.shhxzq.fin.simulator.biz.annotation.CacheGetOrSave;
import com.shhxzq.fin.simulator.biz.service.RedisService;
import com.shhxzq.fin.simulator.biz.util.PropertiesUtil;
import com.shhxzq.fin.simulator.common.util.StringUtil;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 切于biz层所有方法上，需要手动加上@CacheXxx相关注解
 *
 * @author kangyonggan
 * @since 2016/11/30
 */
@Log4j2
public class CacheAop {

    @Setter
    private RedisService redisService;

    /**
     * redis键的前缀
     */
    private String prefix = PropertiesUtil.getProperties("redis.prefix.key") + ":";

    /**
     * 是否打开缓存
     */
    private boolean isOpenCache = PropertiesUtil.getProperties("cache.open").equals("Y");

    /**
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object args[] = joinPoint.getArgs();

        if (!isOpenCache) {
            return joinPoint.proceed(args);
        }

        Class clazz = joinPoint.getTarget().getClass();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = clazz.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        String targetName = "[" + clazz.getName() + "." + method.getName() + "]";

        CacheGetOrSave cacheGetOrSave = method.getAnnotation(CacheGetOrSave.class);
        if (cacheGetOrSave != null) {
            return doGetOrSave(joinPoint, targetName, cacheGetOrSave);
        }
        CacheDelete cacheDelete = method.getAnnotation(CacheDelete.class);
        if (cacheDelete != null) {
            doDelete(joinPoint, targetName, cacheDelete);
        }
        CacheDeleteAll cacheDeleteAll = method.getAnnotation(CacheDeleteAll.class);
        if (cacheDeleteAll != null) {
            doDeleteAll(joinPoint, targetName, cacheDeleteAll);
        }

        return joinPoint.proceed(args);
    }

    /**
     * 处理@CacheGetOrSave
     *
     * @param joinPoint
     * @param targetName
     * @param cacheGetOrSave
     * @return
     * @throws Throwable
     */
    private Object doGetOrSave(ProceedingJoinPoint joinPoint, String targetName, CacheGetOrSave cacheGetOrSave) throws Throwable {
        Object args[] = joinPoint.getArgs();
        String key = cacheGetOrSave.value();

        key = prefix + doKey(key, args);

        Object val = null;
        try {
            val = redisService.get(key);
        } catch (Exception e) {
            log.error("@CacheGetOrSave出错了", e);
        }
        if (val != null) {
            log.info(targetName + "走缓存,key=" + key);
            // 走缓存
            return val;
        }
        log.info("缓存中没数据，不走缓存");
        val = joinPoint.proceed(args);

        long timeout = cacheGetOrSave.timeout();

        try {
            // 把值放入缓存
            if (timeout > 0) {
                redisService.set(key, val, timeout);
            } else {
                redisService.set(key, val);
            }
        } catch (Exception e) {
            log.error("@CacheGetOrSave出错了", e);
        }

        return val;
    }

    /**
     * 处理key
     *
     * @param key
     * @param args
     * @return
     */
    private String doKey(String key, Object[] args) {
        try {
            String pattern = "\\{[\\w:]+}";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(key);
            while (m.find()) {
                String k = m.group(0);
                if (k.contains(":")) {
                    String arr[] = k.substring(1, k.length() - 1).split(":");
                    Object obj = args[Integer.parseInt(arr[0])];
                    Method method = obj.getClass().getMethod("get" + StringUtil.firstToUpperCase(arr[1]));
                    Object val = method.invoke(obj);
                    if (val != null) {
                        key = key.replace(k, val.toString());
                    }
                } else {
                    key = key.replace(k, args[Integer.parseInt(k.substring(1, k.length() - 1))].toString());
                }
            }
        } catch (Exception e) {
            log.error("处理key时异常, key=" + key, e);
        }

        return key;
    }

    /**
     * 处理@CacheDelete
     *
     * @param joinPoint
     * @param targetName
     * @param cacheDelete
     */
    private void doDelete(ProceedingJoinPoint joinPoint, String targetName, CacheDelete cacheDelete) {
        String key = doKey(cacheDelete.value(), joinPoint.getArgs());
        try {
            for (String k : key.split("\\|\\|")) {
                redisService.delete(prefix + k.trim());
            }
            log.info(targetName + "清除缓存,key=" + key);
        } catch (Exception e) {
            log.error("@CacheDelete出错了", e);
        }
    }

    /**
     * 处理@CacheDeleteAll
     *
     * @param joinPoint
     * @param targetName
     * @param cacheDeleteAll
     */
    private void doDeleteAll(ProceedingJoinPoint joinPoint, String targetName, CacheDeleteAll cacheDeleteAll) {
        String key = doKey(cacheDeleteAll.value(), joinPoint.getArgs());
        try {
            for (String k : key.split("\\|\\|")) {
                redisService.deleteAll(prefix + k.trim() + "*");
            }
            log.info(targetName + "清除所有缓存,key=" + key);
        } catch (Exception e) {
            log.error("@CacheDeleteAllLike出错了", e);
        }
    }
}
