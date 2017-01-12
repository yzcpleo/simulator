package com.shhxzq.fin.simulator.web.controller.web;

import com.shhxzq.fin.simulator.biz.service.BankCommandService;
import com.shhxzq.fin.simulator.biz.service.BankTranService;
import com.shhxzq.fin.simulator.biz.util.BankCommandHelp;
import com.shhxzq.fin.simulator.model.constants.BankEnum;
import com.shhxzq.fin.simulator.model.dto.*;
import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;
import com.shhxzq.fin.simulator.web.util.CpUtil;
import com.shhxzq.fin.simulator.web.util.HttpUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 银联模拟器
 *
 * @author kangyonggan
 * @since 16/5/20
 */
@Controller
@RequestMapping("cp")
@Log4j2
public class CpController {

    @Autowired
    private BankCommandService bankCommandService;

    @Autowired
    private BankTranService bankTranService;

    /**
     * 鉴权
     *
     * @param cpVerifyRequest
     * @return
     */
    @RequestMapping(value = "verify", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String verify(CpVerifyRequest cpVerifyRequest) {
        log.info("============ 鉴权开始 ============");
        log.info("请求报文: {}", cpVerifyRequest);

        // 响应码
        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.CP.getBnkCo(), "verify");

        String code = "000";
        String val = "交易成功";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            code = bankTran.getRespCo();
            val = bankTran.getRespMsg();
            log.info("强制改变鉴权响应码为: {} - {}", code, val);
        } else {
            log.info("不强制改变鉴权响应码, 正常鉴权...");
        }

        // 银联方流水号
        String serNo = BankCommandHelp.genSerialNo();
        log.info("银联方流水号为: {}", serNo);

        BankCommand tran = bankCommandService.findBankCommandByMerSerialNo(cpVerifyRequest.getEncMsg().split("\\|")[5]);
        if (tran != null) {
            log.info("交易重复, 基金公司流水号为: {}", tran);
            code = "094";
            val = "重复交易";
        } else {
            log.info("交易不重复, 生成交易并入库...");
            // 交易内容
            BankCommand transaction = CpUtil.verifyRequestToTransaction(cpVerifyRequest, bankTran, code, "", serNo);
            log.info("交易内容为: {}", transaction);

            // 交易入库
            bankCommandService.saveBankCommand(transaction);
        }

        if ("000".equals(code)) {
            log.info("鉴权成功, 异步通知be...{}", code);
            asyncNoticeBe(cpVerifyRequest, code);
        } else if (code.startsWith("000_")) {
            log.info("鉴权成功, 异步通知be...{}", code);
            asyncNoticeBe(cpVerifyRequest, code.substring(4));
        } else {
            log.info("鉴权失败, 不异步通知be...{}", code);
        }

        // 返回主体
        String respCodeMsg = CpUtil.buildVerifyRespCodeMsg(cpVerifyRequest, code, val, serNo);
        log.info("返回主体: {}", respCodeMsg);

        // 返回签名
        String sign = respCodeMsg;
        log.info("返回签名: {}", sign);

        // 返回报文
        String msg = CpUtil.buildVerifyMsg(cpVerifyRequest, respCodeMsg, sign);
        log.info("返回报文: {}", msg);

        log.info("============ 鉴权结束 ============");
        return msg;
    }

    /**
     * 申购(代扣)
     *
     * @param cpPayRequest
     * @return
     */
    @RequestMapping(value = "pay", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String pay(CpPayRequest cpPayRequest) {
        log.info("============ 申购开始 ============");
        log.info("请求报文: {}", cpPayRequest);

        // 响应码
        String code = "00";
        String val = "响应成功";

        // 状态码
        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.CP.getBnkCo(), "pay");
        String stat = "1001";
        String valStat = "交易成功";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            stat = bankTran.getRespCo();
            valStat = bankTran.getRespMsg();
            log.info("强制改变申购状态码为: {} - {}", stat, val);
        } else {
            log.info("不强制改变申购状态码, 正常申购...");
        }

        // 银联方流水号
        String serNo = BankCommandHelp.genSerialNo();
        log.info("银联方流水号为: {}", serNo);

        BankCommand tran = bankCommandService.findBankCommandByMerSerialNo(cpPayRequest.getOrderNo());
        if (tran != null) {
            log.info("交易重复, 基金公司流水号为: {}", tran);
            code = "94";
            val = "重复业务";
            stat = "2094";
            valStat = "重复业务";
        } else {
            log.info("交易不重复, 生成交易并入库...");
            // 交易内容
            BankCommand transaction = CpUtil.payRequestToTransaction(cpPayRequest, bankTran, code, stat, serNo);
            log.info("交易内容为: {}", transaction);

            // 交易入库
            bankCommandService.saveBankCommand(transaction);
        }

        // 返回主体
        String body = CpUtil.buildPayBody(cpPayRequest, code, stat, valStat);
        log.info("返回主体: {}", body);

        // 返回报文
        String msg = CpUtil.buildPayMsg(body, code, stat);
        log.info("返回报文: {}", msg);

        log.info("============ 申购结束 ============");
        return msg;
    }

    /**
     * 赎回(代付)
     *
     * @param cpRedeemRequest
     * @return
     */
    @RequestMapping(value = "redeem", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String redeem(CpRedeemRequest cpRedeemRequest) {
        log.info("============ 赎回开始 ============");
        log.info("请求报文: {}", cpRedeemRequest);

        // 响应码
        String code = "0000";
        String val = "响应成功";

        // 状态码
        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.CP.getBnkCo(), "redeem");
        String stat = "s";
        String valStat = "交易成功";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            stat = bankTran.getRespCo();
            valStat = bankTran.getRespMsg();
            log.info("强制改变赎回状态码为: {} - {}", stat, val);
        } else {
            log.info("不强制改变赎回状态码, 正常赎回...");
        }

        // 银联方流水号
        String serNo = BankCommandHelp.genSerialNo();
        log.info("银联方流水号为: {}", serNo);

        BankCommand tran = bankCommandService.findBankCommandByMerSerialNo(cpRedeemRequest.getMerSeqId());
        if (tran != null) {
            log.info("交易重复, 基金公司流水号为: {}", tran);
            code = "0105";
            val = "重复交易";
        } else {
            log.info("交易不重复, 生成交易并入库...");
            // 交易内容
            BankCommand transaction = CpUtil.redeemRequestToTransaction(cpRedeemRequest, bankTran, code, stat, serNo);
            log.info("交易内容为: {}", transaction);

            // 交易入库
            bankCommandService.saveBankCommand(transaction);
        }

        // 返回报文
        String msg = CpUtil.buildRedeemMsg(cpRedeemRequest, code, serNo, stat);
        log.info("返回报文: {}", msg);

        log.info("============ 赎回结束 ============");
        return msg;
    }

    /**
     * 代付单笔查询
     *
     * @param cpQueryRequest
     * @return
     */
    @RequestMapping(value = "query", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String query(CpQueryRequest cpQueryRequest) {
        log.info("============ 代付单查开始 ============");
        log.info("请求报文: {}", cpQueryRequest);

        BankCommand transaction = bankCommandService.findBankCommandByMerSerialNo(cpQueryRequest.getMerSeqId());
        log.info("查询的交易: {}", transaction);

        // 银联方流水号
        String serNo = BankCommandHelp.genSerialNo();
        log.info("银联方流水号为: {}", serNo);

        // 返回报文
        String msg = CpUtil.buildQueryMsg(cpQueryRequest, transaction, serNo);
        log.info("返回报文: {}", msg);

        log.info("============ 代付单查结束 ============");
        return msg;
    }

    /**
     * 代扣单笔查询
     *
     * @param cpQuery2Request
     * @return
     */
    @RequestMapping(value = "query2", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String query2(CpQuery2Request cpQuery2Request) {
        log.info("============ 代扣单查开始 ============");
        log.info("请求报文: {}", cpQuery2Request);

        BankCommand transaction = bankCommandService.findBankCommandByMerSerialNo(cpQuery2Request.getOrderNo());
        log.info("查询的交易: {}", transaction);

        // 银联方流水号
        String serNo = BankCommandHelp.genSerialNo();
        log.info("银联方流水号为: {}", serNo);

        // 返回报文
        String msg = CpUtil.buildQuery2Msg(cpQuery2Request, transaction, serNo);
        log.info("返回报文: {}", msg);

        log.info("============ 代扣单查结束 ============");
        return msg;
    }

    /**
     * 异步通知be
     *
     * @param cpVerifyRequest
     */
    private void asyncNoticeBe(CpVerifyRequest cpVerifyRequest, String code) {
        String param = CpUtil.buildVerifyParam(cpVerifyRequest, code);
        log.info("通知参数: {}", param);
        new Thread() {
            public void run() {
                try {
                    Long sleep = 4L;
                    sleep(sleep);
                    log.info("异步通知be睡了{}ms", sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String result = HttpUtil.sendPost(cpVerifyRequest.getReturnUrl(), param);
                if ("chinapayok".equals(result)) {
                    log.info("异步通知be成功!");
                } else {
                    log.info("异步通知be失败!");
                }
            }
        }.start();
    }


}
