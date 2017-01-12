package com.shhxzq.fin.simulator.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.shhxzq.fin.simulator.biz.service.BankCommandService;
import com.shhxzq.fin.simulator.common.util.StringUtil;
import com.shhxzq.fin.simulator.mapper.BankCommandMapper;
import com.shhxzq.fin.simulator.model.constants.AppConstants;
import com.shhxzq.fin.simulator.model.constants.TranSt;
import com.shhxzq.fin.simulator.model.vo.BankCommand;
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
public class BankCommandServiceImpl extends BaseService<BankCommand> implements BankCommandService {

    @Autowired
    private BankCommandMapper bankCommandMapper;

    @Override
    public List<BankCommand> searchBankCommands(int pageNum, String bnkCo, String merSerialNo) {
        Example example = new Example(BankCommand.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(bnkCo)) {
            criteria.andEqualTo("bnkCo", bnkCo);
        }

        if (StringUtils.isNotEmpty(merSerialNo)) {
            criteria.andLike("merSerialNo", StringUtil.toLikeString(merSerialNo));
        }

        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return super.selectByExample(example);
    }

    @Override
    public BankCommand findBankCommandById(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    public void updateBankCommand(BankCommand bankCommand) {
        super.updateByPrimaryKeySelective(bankCommand);
    }

    @Override
    public void updateBankCommandTranSt() {
        Example example = new Example(BankCommand.class);

        BankCommand bankCommand = new BankCommand();
        bankCommand.setTranSt(TranSt.Y.name());

        bankCommandMapper.updateByExampleSelective(bankCommand, example);
    }

    @Override
    public BankCommand findBankCommandByMerSerialNo(String merSerialNo) {
        BankCommand bankCommand = new BankCommand();
        bankCommand.setMerSerialNo(merSerialNo);

        return super.selectOne(bankCommand);
    }

    @Override
    public void saveBankCommand(BankCommand bankCommand) {
        super.save(bankCommand);
    }

    @Override
    public List<BankCommand> findBankCommands4Dz(String tranCo, String bnkCo, String workDay) {
        BankCommand bankCommand = new BankCommand();
        bankCommand.setBnkCo(bnkCo);
        bankCommand.setTranCo(tranCo);
        bankCommand.setWorkDay(workDay);

        return super.select(bankCommand);
    }
}
