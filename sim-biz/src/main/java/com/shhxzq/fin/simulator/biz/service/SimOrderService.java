package com.shhxzq.fin.simulator.biz.service;

import com.shhxzq.fin.simulator.model.vo.SimOrder;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/12
 */
public interface SimOrderService {
    List<SimOrder> findOrders(int pageNow, String orderType);

    SimOrder findOrder(Long id);

    void updateOrder(SimOrder order);

    void deleteOrder(Long id);

    void saveOrder(SimOrder order);

    String genFile(String orderType);
}
