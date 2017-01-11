package com.shhxzq.fin.simulator.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.shhxzq.fin.simulator.biz.service.BankChannelService;
import com.shhxzq.fin.simulator.common.util.StringUtil;
import com.shhxzq.fin.simulator.mapper.BankChannelMapper;
import com.shhxzq.fin.simulator.model.constants.AppConstants;
import com.shhxzq.fin.simulator.model.vo.BankChannel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/10
 */
@Service
public class BankChannelServiceImpl extends BaseService<BankChannel> implements BankChannelService {

    @Autowired
    private BankChannelMapper bankChannelMapper;

    @Override
    public List<BankChannel> searchBankChannels(int pageNum, String bnkNm) {
        Example example = new Example(BankChannel.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(bnkNm)) {
            criteria.andLike("bnkNm", StringUtil.toLikeString(bnkNm));
        }

        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return super.selectByExample(example);
    }

    @Override
    public void saveBankChannel(BankChannel bankChannel) {
        super.insertSelective(bankChannel);
    }

    @Override
    public BankChannel findBankChannelById(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    public void updateBankChannel(BankChannel bankChannel) {
        super.updateByPrimaryKeySelective(bankChannel);
    }

    @Override
    public boolean existsBnkCo(String bnkCo) {
        BankChannel bankChannel = new BankChannel();
        bankChannel.setBnkCo(bnkCo);

        return bankChannelMapper.selectCount(bankChannel) == 1;
    }

    @Override
    public List<BankChannel> findAllBankChannels() {
        Example example = new Example(BankChannel.class);
        example.createCriteria().andEqualTo("isDeleted", AppConstants.IS_DELETED_NO);

        example.setOrderByClause("created_time desc");
        return super.selectByExample(example);
    }

    @Override
    public BankChannel findBankChannelByBnkCo(String bnkCo) {
        BankChannel bankChannel = new BankChannel();
        bankChannel.setBnkCo(bnkCo);

        return super.selectOne(bankChannel);
    }
}
