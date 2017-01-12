package com.shhxzq.fin.simulator.biz.service.impl.pab;

import com.shhxzq.fin.simulator.common.util.DateUtils;
import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;

import java.util.Date;


public class PABUtil {

    public static BankCommand buildTransaction(Result request, BankTran bankTran, String serNo, String code) {
        BankCommand transaction = new BankCommand();
        transaction.setMerSerialNo(request.getThirdVoucher());// 基金公司流水号
        transaction.setBnkSerialNo(serNo);// 银联方流水号
        transaction.setBnkCo(bankTran.getBnkCo());// 银行编号
        transaction.setBnkNm(bankTran.getBnkNm());
        if("pay".equals(bankTran.getTranCo())){
            transaction.setRcvrAccoNo(request.getHoResultSet4047R().getOppAccNo());// 交易账户(卡号)
            transaction.setAmount(String.valueOf(request.getHoResultSet4047R().getAmount().toString()));// 交易金额
            transaction.setRespCo(code);// 响应码: e.g. "0"
            String stat = "F";
            if ("000000".equals(code)) {
                stat = "Y";
                transaction.setRespCo("0000");
            }
            transaction.setTranSt(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        } else {
            transaction.setRcvrAccoNo(request.getOutAcctNo());
            transaction.setAmount(String.valueOf(request.getTranAmount()));
            transaction.setRespCo(code);
            String stat = "F";
            if("000000".equals(code)) {
                stat = "Y";
            }
            transaction.setTranSt(stat);
        }
        transaction.setTranCo(bankTran.getTranCo());// 交易类型
        transaction.setTranNm(bankTran.getTranNm());
        transaction.setWorkDay(DateUtils.getCurrentDate());// 工作日

        return transaction;
    }




}
