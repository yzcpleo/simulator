package com.shhxzq.fin.simulator.biz.service;

import com.shhxzq.fin.simulator.model.vo.DzFile;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/11
 */
public interface DzFileService {

    /**
     * 搜索对账文件
     *
     * @param pageNum
     * @param bnkCo
     * @return
     */
    List<DzFile> searchDzFiles(int pageNum, String bnkCo);
}
