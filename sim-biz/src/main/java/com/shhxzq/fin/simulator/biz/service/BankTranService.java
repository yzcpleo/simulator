package com.shhxzq.fin.simulator.biz.service;

import com.shhxzq.fin.simulator.model.vo.BankTran;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/11
 */
public interface BankTranService {

    /**
     * 搜索交易类型
     *
     * @param pageNum
     * @param bnkCo
     * @return
     */
    List<BankTran> searchBankTrans(int pageNum, String bnkCo);

    /**
     * 更新交易类型
     *
     * @param bankTran
     */
    void updateBankTran(BankTran bankTran);

    /**
     * 查找交易类型（包括已删除的）
     *
     * @param id
     * @return
     */
    BankTran findBankTranById(Long id);

    /**
     * 校验是否存在银行交易类型
     *
     * @param bnkCo
     * @param tranCo
     * @return
     */
    boolean existsTranCo(String bnkCo, String tranCo);

    /**
     * 保存交易类型
     *
     * @param bankTran
     */
    void saveBankTran(BankTran bankTran);

    /**
     * 查询交易类型
     *
     * @param bnkCo
     * @param tranCo
     * @return
     */
    BankTran findBankTranByBnkCoAndTranCo(String bnkCo, String tranCo);
}
