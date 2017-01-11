package com.shhxzq.fin.simulator.biz.service;

import com.shhxzq.fin.simulator.model.vo.BankResp;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/11
 */
public interface BankRespService {

    /**
     * 搜索错误码
     *
     * @param pageNum
     * @param bnkCo
     * @return
     */
    List<BankResp> searchBankResps(int pageNum, String bnkCo);

    /**
     * 保存错误码
     *
     * @param bankResp
     */
    void saveBankResp(BankResp bankResp);

    /**
     * 查找错误码
     *
     * @param id
     * @return
     */
    BankResp findBankRespById(Long id);

    /**
     * 更新错误码
     *
     * @param bankResp
     */
    void updateBankResp(BankResp bankResp);

    /**
     * 交易错误码是否存在
     *
     * @param bnkCo
     * @param respCo
     * @return
     */
    boolean existsRespCo(String bnkCo, String respCo);

    /**
     * 物理删除错误码
     *
     * @param id
     */
    void deleteBankRespById(Long id);

    /**
     * 查找银行通道的所有错误码
     *
     * @param bnkCo
     * @return
     */
    List<BankResp> findAllBankRespsByBnkCo(String bnkCo);

    /**
     * 根据银行代码和错误码查找错误码信息
     *
     * @param respCo
     * @param bnkCo
     * @return
     */
    BankResp findBankRespByRespCoAndBakCo(String respCo, String bnkCo);

    /**
     * 批量报文错误码
     *
     * @param bnkCo
     * @param fileName
     */
    void saveBankResps(String bnkCo, String fileName);
}
