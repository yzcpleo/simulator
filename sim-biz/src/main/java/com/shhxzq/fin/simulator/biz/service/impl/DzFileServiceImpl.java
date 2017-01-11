package com.shhxzq.fin.simulator.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.shhxzq.fin.simulator.biz.service.DzFileService;
import com.shhxzq.fin.simulator.model.constants.AppConstants;
import com.shhxzq.fin.simulator.model.vo.DzFile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/11
 */
@Service
public class DzFileServiceImpl extends BaseService<DzFile> implements DzFileService {

    @Override
    public List<DzFile> searchDzFiles(int pageNum, String bnkCo) {
        Example example = new Example(DzFile.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(bnkCo)) {
            criteria.andEqualTo("bnkCo", bnkCo);
        }

        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return super.selectByExample(example);
    }
}
