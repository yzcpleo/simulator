package com.shhxzq.fin.simulator.biz.service.impl.spdb;

import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by houjiagang on 16/7/12.
 */
public class SpdbUtil {

    public static BankCommand request2Transaction(HashMap<String, String> map, BankTran bankTran, String serNo, String retCode) {
        BankCommand transaction = new BankCommand();
        transaction.setMerSerialNo(map.get("TermSsn"));
//        transaction.setMerId(bank.getMerId());
        transaction.setBnkSerialNo(serNo);
        transaction.setBnkCo(bankTran.getBnkCo());
        transaction.setRcvrAccoNo(map.get("IdNo"));
        transaction.setTranCo(bankTran.getTranCo());
        transaction.setAmount(map.get("TranAmt"));
        transaction.setRespCo(retCode);

        String stat = "F";
        if ("00".equals(retCode)) {
            stat = "Y";
        }
        transaction.setTranCo(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(map.get("MercDtTm").substring(0, 8));// 工作日

        return transaction;

    }

    public static BankCommand cRequest2Transaction(CompanyRequest request, BankTran bankTran, String serNo, String retCode) {
        SimpleDateFormat formate = new SimpleDateFormat("yyyyMMdd");

        BankCommand transaction = new BankCommand();
        if ("redeem8801".equals(bankTran.getBnkCo())) {
            transaction.setMerSerialNo(request.getBody().getSignature().getBody().getElecChequeNo());
        } else {
            transaction.setMerSerialNo(request.getBody().getSignature().getBody().getElectronNumber());
        }

//        transaction.setMerId(bank.getMerId());
        transaction.setBnkSerialNo(serNo);
        transaction.setBnkCo(bankTran.getBnkCo());
        transaction.setRcvrAccoNo(request.getBody().getSignature().getBody().getAcctNo());
        transaction.setTranCo(bankTran.getTranCo());
        transaction.setAmount(request.getBody().getSignature().getBody().getAmount());
        transaction.setRespCo(retCode);

        String stat = "F";
        if ("1".equals(retCode)) {
            stat = "Y";
        }
        transaction.setTranCo(stat);
        transaction.setWorkDay(formate.format(new Date()));

        return transaction;
    }


}
