package com.shhxzq.fin.simulator.biz.service;

import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/11
 */
public interface BankCommandService {

    /**
     * 搜索交易流水
     *
     * @param pageNum
     * @param bnkCo
     * @param merSerialNo
     * @return
     */
    List<BankCommand> searchBankCommands(int pageNum, String bnkCo, String merSerialNo);

    /**
     * 查找交易流水（包括已删除的）
     *
     * @param id
     * @return
     */
    BankCommand findBankCommandById(Long id);

    /**
     * 更新交易流水
     *
     * @param bankCommand
     */
    void updateBankCommand(BankCommand bankCommand);

    /**
     * 把所有交易的交易状态置为Y
     */
    void updateBankCommandTranSt();

    /**
     * 根据商户流水号查询交易
     *
     * @param merSerialNo
     * @return
     */
    BankCommand findBankCommandByMerSerialNo(String merSerialNo);

    /**
     * 保存交易流水
     *
     * @param command
     */
    void saveBankCommand(BankCommand command);

    /**
     * 查询交易4对账
     *
     * @param tranCo
     * @param bnkCo
     * @param workDay
     * @return
     */
    List<BankCommand> findBankCommands4Dz(String tranCo, String bnkCo, String workDay);

    /**
     * 分页查询交易4对账
     *
     * @param bnkCo
     * @param tranCo
     * @param start
     * @param end
     * @return
     */
    List<BankCommand> findBankCommands4DzByPage(String bnkCo, String tranCo, String start, String end);
}
