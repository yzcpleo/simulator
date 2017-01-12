package com.shhxzq.fin.simulator.biz.service.impl.sh;


import com.shhxzq.fin.simulator.common.util.DateUtils;
import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;


public class SHUtil {

    public static BankCommand buildTransaction(Banksh request, BankTran bankTran, String serNo, String code) {
        BankCommand bankCommand = new BankCommand();
        bankCommand.setMerSerialNo(request.getMessage().getId());// 基金公司流水号
//        bankCommand.setMerId(bank.getMerId());// 商户号
        bankCommand.setBnkSerialNo(serNo);// 银行流水号
        bankCommand.setBnkCo(bankTran.getBnkCo());// 银行编号
        bankCommand.setBnkNm(bankTran.getBnkNm());
        bankCommand.setRcvrAccoNo(request.getMessage().getRequest().getCardCode());// 交易账户(卡号)
        bankCommand.setTranCo(bankTran.getTranCo());// 交易类型
        bankCommand.setTranNm(bankTran.getTranNm());
        bankCommand.setAmount(request.getMessage().getRequest().getAmount());// 交易金额
        bankCommand.setRespCo(code);// 响应码: e.g. "0"

        String stat = "F";
        if ("SUCC".equals(code)) {
            stat = "Y";
        }

        bankCommand.setTranSt(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        bankCommand.setWorkDay(DateUtils.getCurrentDate());// 工作日

        return bankCommand;
    }




}
