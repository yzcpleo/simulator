package com.shhxzq.fin.simulator.biz.service.impl.ceb;

import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;


public class CebUtil {

    public static BankCommand buildTransaction(MessageSuit messageSuit, BankTran bankTran, String serNo, String code) {
        BankCommand transaction = new BankCommand();
        transaction.setMerSerialNo(messageSuit.getMessage().getPlain().getSerialNo());// 基金公司流水号
//        transaction.setMerId(bank.getMerId());// 商户号
        transaction.setBnkSerialNo(serNo);// 银联方流水号
        transaction.setBnkCo(bankTran.getBnkCo());// 银行编号
        transaction.setBnkNm(bankTran.getBnkNm());
        transaction.setRcvrAccoNo(messageSuit.getMessage().getPlain().getMerId());// 交易账户(卡号)
        transaction.setTranCo(bankTran.getTranCo());// 交易类型
        transaction.setAmount(messageSuit.getMessage().getPlain().getAmount());// 交易金额
        transaction.setRespCo(code);// 响应码: e.g. "0"

        String stat = "F";
        if ("".equals(code)) {
            stat = "Y";
        }

        transaction.setTranSt(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(messageSuit.getMessage().getPlain().getDate().substring(0, 7));// 工作日

        return transaction;
    }


    public static BankCommand buildredeemTransaction(Transaction cebTransaction, BankTran bankTran, String serNo, String code) {
        BankCommand transaction = new BankCommand();
        transaction.setMerSerialNo(cebTransaction.getTransHead().getBatchID());// 基金公司流水号
        transaction.setBnkSerialNo(serNo);// 银联方流水号
        transaction.setBnkCo(bankTran.getBnkCo());// 银行编号
        transaction.setBnkNm(bankTran.getBnkNm());
        transaction.setRcvrAccoNo(cebTransaction.getTransContent().getReqData().getAccountNo());// 交易账户(卡号)
        transaction.setTranCo(bankTran.getTranCo());// 交易类型
        transaction.setTranNm(bankTran.getTranNm());
        transaction.setAmount(cebTransaction.getTransContent().getReqData().getAmount());// 交易金额
        transaction.setRespCo(code);// 响应码: e.g. "0"

        String stat = "F";
        if ("0000".equals(code)) {
            stat = "Y";
        }

        transaction.setTranSt(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(cebTransaction.getTransHead().getJnlDate());// 工作日

        return transaction;
    }



}
