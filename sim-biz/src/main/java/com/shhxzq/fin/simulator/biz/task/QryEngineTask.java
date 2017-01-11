package com.shhxzq.fin.simulator.biz.task;

import com.shhxzq.fin.simulator.biz.service.BankCommandService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author kangyonggan
 * @since 2017/1/11
 */
@Component
@Log4j2
public class QryEngineTask {

    @Autowired
    private BankCommandService bankCommandService;

    /**
     * 每分钟执行一次，把BankCommand交易全部置为成功,tranSt = 'Y'
     * <p>
     * cron表达式：* * * * * *（共6位，使用空格隔开，具体如下）
     * cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23) *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT)
     */
    @Scheduled(cron = "0 * * * * *")
    public void start() {
        log.info("模拟器查询引擎执行开始");

        try {
            bankCommandService.updateBankCommandTranSt();
        } catch (Exception e) {
            log.error("模拟器查询引擎执行发送异常", e);
        }

        log.info("模拟器查询引擎执行结束");
    }

}
