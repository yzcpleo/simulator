package com.shhxzq.fin.simulator.biz.service.impl.icbc;


import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;


public class IcbcUtil {

    public static BankCommand buildTransaction(CMS cms, BankTran bankTran, String serNo, String code) {
        BankCommand transaction = new BankCommand();
        transaction.setMerSerialNo(cms.getEBody().getPub().getFSeqno());// 基金公司流水号
        transaction.setBnkSerialNo(serNo);// 银联方流水号
        transaction.setBnkCo(bankTran.getBnkCo());// 银行编号
        transaction.setBnkNm(bankTran.getBnkNm());
        transaction.setRcvrAccoNo(cms.getEBody().getIn().getUserAcc());// 交易账户(卡号)
        transaction.setTranCo(bankTran.getTranCo());// 交易类型
        transaction.setTranNm(bankTran.getTranNm());
        transaction.setAmount(cms.getEBody().getIn().getTotalAmount());// 交易金额
        transaction.setRespCo(code);// 响应码: e.g. "0"

        String stat = "F";
        if ("00000".equals(code)) {
            stat = "Y";
        }

        transaction.setTranSt(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(cms.getEBody().getPub().getTranDate());// 工作日

        return transaction;
    }

    public static BankCommand buildRedeemTransaction(CMS cms, BankTran bankTran, String serNo, String code) {
        BankCommand transaction = new BankCommand();
        transaction.setMerSerialNo(cms.getEBody().getPub().getFSeqno());// 基金公司流水号
        transaction.setBnkCo(serNo);// 银联方流水号
        transaction.setBnkCo(bankTran.getBnkCo());// 银行编号
        transaction.setBnkNm(bankTran.getBnkNm());
        transaction.setRcvrAccoNo(cms.getEBody().getIn().getQuery().getRecAccNo());// 交易账户(卡号)
        transaction.setTranCo(bankTran.getTranCo());// 交易类型
        transaction.setTranNm(bankTran.getTranNm());
        transaction.setAmount(cms.getEBody().getIn().getQuery().getPayAmt());// 交易金额
        transaction.setRespCo(code);// 响应码: e.g. "0"

        String stat = "F";
        if ("7".equals(code)) {
            stat = "Y";
        }

        transaction.setTranSt(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(cms.getEBody().getPub().getTranDate());// 工作日

        return transaction;
    }


}
