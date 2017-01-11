package com.shhxzq.fin.simulator.biz.service;

import com.shhxzq.fin.simulator.model.vo.BankChannel;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/10
 */
public interface BankChannelService {

    /**
     * 搜索银行通道
     *
     * @param pageNum
     * @param bnkNm
     * @return
     */
    List<BankChannel> searchBankChannels(int pageNum, String bnkNm);

    /**
     * 保存银行通道
     *
     * @param bankChannel
     */
    void saveBankChannel(BankChannel bankChannel);

    /**
     * 查询银行通道（包括逻辑删除的）
     *
     * @param id
     * @return
     */
    BankChannel findBankChannelById(Long id);

    /**
     * 更新银行通道
     *
     * @param bankChannel
     */
    void updateBankChannel(BankChannel bankChannel);

    /**
     * 校验银行代码是否存在
     *
     * @param bnkCo
     * @return
     */
    boolean existsBnkCo(String bnkCo);

    /**
     * 查找所有的银行通道
     *
     * @return
     */
    List<BankChannel> findAllBankChannels();

    /**
     * 查找银行通道
     *
     * @param bnkCo
     * @return
     */
    BankChannel findBankChannelByBnkCo(String bnkCo);
}
