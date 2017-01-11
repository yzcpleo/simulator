package com.shhxzq.fin.simulator.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/11/30
 */
public class AopUtil {
    public static String getStringFromRequest(Object[] args) {
        String req = "";
        for (Object arg : args) {
            if (arg == null) {
                req = req + "null,";
                continue;
            } else if (arg instanceof List == true) {
                req = req + JSON.toJSONString(arg);
            } else if (arg.getClass().isArray()) {
                req = req + JSONArray.toJSONString(arg);
            } else if (arg instanceof Enum) {
                req = req + JSON.toJSONString(arg) + ",";
            } else if (!(arg instanceof String)
                    && !(arg instanceof BigDecimal)
                    && !(arg instanceof Boolean)
                    && !(arg instanceof Integer)
                    && (arg instanceof Object)) {
                req = req + JSON.toJSONString(arg) + ",";
            } else {
                req = req + arg.toString() + ",";
            }
        }

        if (StringUtils.isNotEmpty(req) && req.length() > 100) {
            return req.hashCode() + "";
        } else {
            return req;
        }
    }

    public static String getStringFromResponse(Object arg) {
        String rsp = "";
        if (arg == null) {
            rsp = rsp + "null,";
            return rsp;
        } else if (arg instanceof List) {
            rsp = rsp + JSON.toJSONString(arg);
            return rsp;
        } else if (arg instanceof Enum) {
            rsp = rsp + JSON.toJSONString(arg);
            return rsp;
        } else if (!(arg instanceof String)
                && !(arg instanceof BigDecimal)
                && !(arg instanceof Boolean)
                && !(arg instanceof Integer)
                && (arg instanceof Object)) {
            rsp = rsp + JSON.toJSONString(arg) + ",";
        } else {
            rsp = rsp + arg.toString() + ",";
        }
        return rsp;
    }
}
