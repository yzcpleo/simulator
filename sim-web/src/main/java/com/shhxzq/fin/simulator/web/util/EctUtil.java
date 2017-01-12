package com.shhxzq.fin.simulator.web.util;

import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;

import java.util.Map;

/**
 * @author kangyonggan
 * @since 16/6/2
 */
public class EctUtil {

    public static BankCommand buildTransaction(Map<String, String> params, BankTran bankTran, String serNo, String code, String val) {
        BankCommand command = new BankCommand();
        command.setMerSerialNo(params.get("transSeq"));// 基金公司流水号
//        command.setMerId("");// 商户号
        command.setBnkSerialNo(serNo);// 银联方流水号
        command.setBnkCo(bankTran.getBnkCo());// 银行编号
        command.setBnkNm(bankTran.getBnkNm());
        command.setRcvrAccoNo(params.get("transAcct"));// 交易账户(卡号)
        command.setTranCo(bankTran.getTranCo());// 交易类型
        command.setTranNm(bankTran.getTranNm());
        command.setAmount(params.get("transAmt"));// 交易金额
        command.setRespCo(code);// 响应码: e.g. "50050000"

        String stat = "Y";
        if (!"50050000".equals(code)) {
            stat = "I";
        }

        command.setTranSt(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        command.setWorkDay(params.get("transDate"));// 工作日

        return command;
    }
}
