package com.shhxzq.fin.simulator.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.shhxzq.fin.simulator.biz.service.BankChannelService;
import com.shhxzq.fin.simulator.biz.service.BankTranService;
import com.shhxzq.fin.simulator.mapper.BankTranMapper;
import com.shhxzq.fin.simulator.model.constants.AppConstants;
import com.shhxzq.fin.simulator.model.vo.BankChannel;
import com.shhxzq.fin.simulator.model.vo.BankTran;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/11
 */
@Service
public class BankTranServiceImpl extends BaseService<BankTran> implements BankTranService {

    @Autowired
    private BankTranMapper bankTranMapper;

    @Autowired
    private BankChannelService bankChannelService;

    @Override
    public List<BankTran> searchBankTrans(int pageNum, String bnkCo) {
        Example example = new Example(BankTran.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(bnkCo)) {
            criteria.andEqualTo("bnkCo", bnkCo);
        }

        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return super.selectByExample(example);
    }

    @Override
    public void updateBankTran(BankTran bankTran) {
        super.updateByPrimaryKeySelective(bankTran);
    }

    @Override
    public BankTran findBankTranById(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    public boolean existsTranCo(String bnkCo, String tranCo) {
        BankTran bankTran = new BankTran();
        bankTran.setBnkCo(bnkCo);
        bankTran.setTranCo(tranCo);

        return bankTranMapper.selectCount(bankTran) == 1;
    }

    @Override
    public void saveBankTran(BankTran bankTran) {
        BankChannel bankChannel = bankChannelService.findBankChannelByBnkCo(bankTran.getBnkCo());
        bankTran.setBnkNm(bankChannel.getBnkNm());

        super.insertSelective(bankTran);
    }

    @Override
    public BankTran findBankTranByBnkCoAndTranCo(String bnkCo, String tranCo) {
        BankTran bankTran = new BankTran();
        bankTran.setBnkCo(bnkCo);
        bankTran.setTranCo(tranCo);

        return super.selectOne(bankTran);
    }

    @Override
    public List<BankTran> findbankTran4Gen() {
        BankTran bankTran = new BankTran();
        bankTran.setIsGenDz((byte) 1);

        return super.select(bankTran);
    }

    @Override
    public List<BankTran> findbankTran4Push() {
        BankTran bankTran = new BankTran();
        bankTran.setIsPushDz((byte) 1);

        return super.select(bankTran);
    }
}
