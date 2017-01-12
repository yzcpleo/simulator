package com.shhxzq.fin.simulator.biz.service.impl.gdny;

import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;

import java.util.HashMap;

/**
 * Created by houjiagang on 16/7/12.
 */
public class GDNYUtil {

    public static BankCommand request2Transaction(HashMap<String, String> map, BankTran bankTran, String serNo, String retCode){
        BankCommand transaction = new BankCommand();
        transaction.setMerSerialNo(map.get("SEQ_NO"));
        transaction.setBnkSerialNo(serNo);
        transaction.setBnkCo(bankTran.getBnkCo());
        transaction.setBnkNm(bankTran.getBnkNm());
        transaction.setRcvrAccoNo(map.get("CARD_NO"));
        if("redeem".equals(bankTran.getBnkCo())){
            transaction.setRcvrAccoNo(map.get("ACCT_NO"));
        }
        transaction.setTranCo(bankTran.getTranCo());
        transaction.setTranNm(bankTran.getTranNm());
        transaction.setAmount(map.get("TX_AMT"));
        transaction.setRespCo(retCode);

        String stat = "F";
        if ("00".equals(retCode)) {
            stat = "Y";
        }
        transaction.setTranSt(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(map.get("TRANS_TIME").substring(0,8));// 工作日

        return transaction;

    }


}
