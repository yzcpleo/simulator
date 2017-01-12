package com.shhxzq.fin.simulator.biz.util;

import com.shhxzq.fin.simulator.biz.service.RedisService;
import com.shhxzq.fin.simulator.common.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kangyonggan
 * @since 2017/1/11
 */
@Component
public class BankCommandHelp {

    private static final String KEY_SERIAL_NO = "SIM:SERIAL:NO";

    @Autowired
    private static RedisService redisService;

    /**
     * 自动生成流水号
     *
     * @return
     */
    public static String genSerialNo() {
//        String nextVal = String.valueOf(redisService.incr(KEY_SERIAL_NO));
        String nextVal = new SimpleDateFormat("mmssSSS").format(new Date());
        return DateUtils.getCurrentDate() + StringUtils.leftPad(nextVal, 8, "0");
    }
}
