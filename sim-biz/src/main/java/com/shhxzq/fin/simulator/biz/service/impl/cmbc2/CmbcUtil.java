package com.shhxzq.fin.simulator.biz.service.impl.cmbc2;

import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;

import java.util.Map;

/**
 * @author kangyonggan
 * @since 16/6/2
 */
public class CmbcUtil {

    public static BankCommand buildTransaction(Map<String, String> params, BankTran bankTran, String serNo, String code, String val) {
        BankCommand transaction = new BankCommand();
        transaction.setMerSerialNo(params.get("trnId"));// 基金公司流水号
        transaction.setBnkSerialNo(serNo);// 银联方流水号
        transaction.setBnkCo(bankTran.getBnkCo());// 银行编号
        transaction.setBnkNm(bankTran.getBnkNm());
        transaction.setRcvrAccoNo(params.get("acntNo"));// 交易账户(卡号)
        transaction.setTranCo(bankTran.getTranCo());// 交易类型
        transaction.setAmount(params.get("amount"));// 交易金额
        transaction.setRespCo(code);// 响应码: e.g. "0"

        String stat = "F";
        if ("0".equals(code)) {
            stat = "Y";
        } else if ("WEC02".equals(code)) {
            stat = "I";
        }

        transaction.setTranSt(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(params.get("dtClient").substring(0, 10).replaceAll("-", ""));// 工作日

        return transaction;
    }


    public static BankCommand payRequest2Transaction(Map<String, String> params, BankTran bankTran, String serNo, String code){
        BankCommand transaction = new BankCommand();
        transaction.setMerSerialNo(params.get("serialNo"));
        transaction.setBnkSerialNo(serNo);
        transaction.setBnkCo(bankTran.getBnkCo());
        transaction.setBnkNm(bankTran.getBnkNm());
        transaction.setRcvrAccoNo(params.get("signNo"));
        transaction.setTranCo(bankTran.getTranCo());
        transaction.setTranNm(bankTran.getTranNm());
        transaction.setAmount(params.get("amount"));
        transaction.setRespCo(code);

        String stat = "F";
        if("0001".equals(code)){
            stat = "Y";
        }

        transaction.setTranSt(stat);
        transaction.setWorkDay(params.get("date").substring(0,8));
        return transaction;
    }
}
