package com.shhxzq.fin.simulator.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.shhxzq.fin.simulator.biz.service.BankChannelService;
import com.shhxzq.fin.simulator.biz.service.BankRespService;
import com.shhxzq.fin.simulator.biz.util.PropertiesUtil;
import com.shhxzq.fin.simulator.common.util.Excel;
import com.shhxzq.fin.simulator.mapper.BankRespMapper;
import com.shhxzq.fin.simulator.model.constants.AppConstants;
import com.shhxzq.fin.simulator.model.vo.BankChannel;
import com.shhxzq.fin.simulator.model.vo.BankResp;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/11
 */
@Service
@Log4j2
public class BankRespServiceImpl extends BaseService<BankResp> implements BankRespService {

    @Autowired
    private BankRespMapper bankRespMapper;

    @Autowired
    private BankChannelService bankChannelService;

    @Override
    public List<BankResp> searchBankResps(int pageNum, String bnkCo) {
        Example example = new Example(BankChannel.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(bnkCo)) {
            criteria.andEqualTo("bnkCo", bnkCo);
        }

        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return super.selectByExample(example);
    }

    @Override
    public void saveBankResp(BankResp bankResp) {
        BankChannel bankChannel = bankChannelService.findBankChannelByBnkCo(bankResp.getBnkCo());
        bankResp.setBnkNm(bankChannel.getBnkNm());

        super.insertSelective(bankResp);
    }

    @Override
    public BankResp findBankRespById(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    public void updateBankResp(BankResp bankResp) {
        super.updateByPrimaryKeySelective(bankResp);
    }

    @Override
    public boolean existsRespCo(String bnkCo, String respCo) {
        BankResp bankResp = new BankResp();
        bankResp.setBnkCo(bnkCo);
        bankResp.setRespCo(respCo);

        return bankRespMapper.selectCount(bankResp) == 1;
    }

    @Override
    public void deleteBankRespById(Long id) {
        super.deleteByPrimaryKey(id);
    }

    @Override
    public List<BankResp> findAllBankRespsByBnkCo(String bnkCo) {
        BankResp bankResp = new BankResp();
        bankResp.setBnkCo(bnkCo);
        bankResp.setIsDeleted(AppConstants.IS_DELETED_NO);

        return super.select(bankResp);
    }

    @Override
    public BankResp findBankRespByRespCoAndBakCo(String respCo, String bnkCo) {
        BankResp bankResp = new BankResp();
        bankResp.setRespCo(respCo);
        bankResp.setBnkCo(bnkCo);

        return super.selectOne(bankResp);
    }

    @Override
    public void saveBankResps(String bnkCo, String fileName) {
        BankChannel bankChannel = bankChannelService.findBankChannelByBnkCo(bnkCo);

        List<String[]> data;
        try {
            data = Excel.excelToList(PropertiesUtil.getProperties(AppConstants.FILE_PATH_ROOT) + fileName, 3, true);
        } catch (Exception e) {
            log.error("Excel文件解析错误", e);
            return;
        }

        if (data.isEmpty()) {
            return;
        }

        // 删除老的错误码
        deleteBankRespsByBnkCo(bankChannel.getBnkCo());

        bankRespMapper.insertBankResps(bankChannel.getBnkCo(), bankChannel.getBnkNm(), asListKeyValue(data));
    }

    /**
     * 批量删除错误码
     *
     * @param bnkCo
     */
    private void deleteBankRespsByBnkCo(String bnkCo) {
        BankResp bankResp = new BankResp();
        bankResp.setBnkCo(bnkCo);

        super.delete(bankResp);
    }

    /**
     * List<String[]>转List<KeyValuePair>
     *
     * @param data
     */
    private List<KeyValuePair> asListKeyValue(List<String[]> data) {
        List<KeyValuePair> list = new ArrayList();

        for (String[] arr : data) {
            if (StringUtils.isNotEmpty(arr[0])) {
                list.add(new KeyValuePair(arr[0], arr[1]));
            }
        }

        return list;
    }
}
