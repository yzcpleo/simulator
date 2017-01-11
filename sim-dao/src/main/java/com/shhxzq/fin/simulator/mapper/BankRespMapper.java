package com.shhxzq.fin.simulator.mapper;

import com.shhxzq.fin.simulator.model.vo.BankResp;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRespMapper extends MyMapper<BankResp> {

    /**
     * 批量保存错误码
     *
     * @param bnkCo
     * @param bnkNm
     * @param resps
     */
    void insertBankResps(@Param("bnkCo") String bnkCo, @Param("bnkNm") String bnkNm, @Param("resps") List<KeyValuePair> resps);
}