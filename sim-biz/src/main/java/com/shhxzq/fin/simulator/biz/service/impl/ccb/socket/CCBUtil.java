package com.shhxzq.fin.simulator.biz.service.impl.ccb.socket;

import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;


public class CCBUtil {

    public static BankCommand buildTransaction(Tx request, BankTran bankTran, String serNo, String code) {
        BankCommand transaction = new BankCommand();
        transaction.setMerSerialNo(request.getRequestSn());// 基金公司流水号
        transaction.setBnkSerialNo(serNo);// 银联方流水号
        transaction.setBnkCo(bankTran.getBnkCo());// 银行编号
        transaction.setBnkNm(bankTran.getBnkNm());
        transaction.setRcvrAccoNo(request.getBody().getAccNo1());// 交易账户(卡号)
        transaction.setTranCo(bankTran.getTranCo());// 交易类型
        transaction.setTranNm(bankTran.getTranNm());
        transaction.setAmount(request.getBody().getAmount());// 交易金额
        transaction.setRespCo(code);// 响应码: e.g. "0"

        String stat = "F";
        if ("000000".equals(code)) {
            stat = "Y";
        }

        transaction.setTranSt(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(request.getBody().getBillCode());// 工作日

        return transaction;
    }


}
