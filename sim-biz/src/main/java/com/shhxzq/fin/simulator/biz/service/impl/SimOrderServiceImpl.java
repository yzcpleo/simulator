package com.shhxzq.fin.simulator.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.shhxzq.fin.simulator.biz.service.SimOrderService;
import com.shhxzq.fin.simulator.biz.util.PropertiesUtil;
import com.shhxzq.fin.simulator.common.util.DateUtils;
import com.shhxzq.fin.simulator.common.util.StringUtil;
import com.shhxzq.fin.simulator.model.constants.AppConstants;
import com.shhxzq.fin.simulator.model.vo.SimOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/12
 */
@Service
public class SimOrderServiceImpl extends BaseService<SimOrder> implements SimOrderService {

    @Override
    public List<SimOrder> findOrders(int pageNow, String orderType) {
        Example example = new Example(SimOrder.class);

        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(orderType)) {
            criteria.andEqualTo("orderType", orderType);
        }

        example.setOrderByClause("id desc");
        PageHelper.startPage(pageNow, AppConstants.PAGE_SIZE);
        return super.selectByExample(example);
    }

    @Override
    public SimOrder findOrder(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    public void updateOrder(SimOrder order) {
        super.updateByPrimaryKeySelective(order);
    }

    @Override
    public void deleteOrder(Long id) {
        super.deleteByPrimaryKey(id);
    }

    @Override
    public void saveOrder(SimOrder order) {
        super.insertSelective(order);
    }

    @Override
    public String genFile(String orderType) {

        List<SimOrder> orders = findFileOrders(orderType);

        String path = "download/cft/";
        String fileName = "90020000_" + "accountapplication" + "_" + DateUtils.getCurrentDate() + ".txt";
        if ("2".equals(orderType)) {
            fileName = "90020000_" + "tradeapplication" + "_" + DateUtils.getCurrentDate() + ".txt";
        }
        String head = null;
        try {
            head = buildFileHead(orderType, orders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean flag = genFile(PropertiesUtil.getProperties(AppConstants.FILE_PATH_ROOT) + path, fileName, head, orders);

        return fileName;
    }


    public static boolean genFile(String path, String fileName, String head, List<SimOrder> orders) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(path + fileName));
            if (StringUtils.isNotEmpty(head)) {
                out.write(head);
                out.newLine();
            }
            for (SimOrder order : orders) {
                out.write(replaceWithOrders(order));
                out.newLine();
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    private static String replaceWithOrders(SimOrder order){
        StringBuffer temp = new StringBuffer();
        if(order.getOrderType().equals("1")){
            temp.append(order.getCompanyNo()).append("|");
            temp.append(order.getSubCompanyNo()).append("|");
            temp.append(order.getPartnerCustNo()).append("|");
            temp.append(order.getCustType()).append("|");
            temp.append(order.getName()).append("|");
            temp.append(order.getCertType()).append("|");
            temp.append(order.getCertNo()).append("|");
            temp.append(order.getMobile()).append("|");
            temp.append(order.getBankCardNo()).append("|");
            temp.append(order.getRemark());
        } else {
            temp.append(order.getCompanyNo()).append("|");
            temp.append(order.getSubCompanyNo()).append("|");
            temp.append(order.getSerialNo()).append("|");
            temp.append(order.getPartnerCustNo()).append("|");
            temp.append(order.getTradeAcct()).append("|");
            temp.append(order.getProdId()).append("|");
            temp.append(order.getAmount()).append("|");
            temp.append(order.getShare()).append("|");
            temp.append(order.getPaymentType()).append("|");
            temp.append(order.getBankCardNo()).append("|");
            temp.append(order.getChargeType()).append("|");
            temp.append(order.getApkind());
        }
        return temp.toString();


    }


    private String buildFileHead(String orderType, List<SimOrder> orders) throws Exception {
        String head = "总笔数:" + orders.size() + "\n" + "机构号|子机构号|合作方客户编号|客户类型|客户姓名|证件类型|证件号码|手机号码|银行卡号|附加信息|交易账号|应答码|应答信息";
        if ("2".equals(orderType)) {
            head = "总笔数:" + orders.size() + "|总申请金额" + getAmount(orders, "orderType", true, "2") + "|总申请份额" + getShare(orders, "orderType", true, "2") + "\n" + "机构号|子机构号|订单号|合作方客户编号|交易账号|产品代码|申请金额|申请份额|支付方式|银行卡号|银行通道|收费方式|业务码";

        }
        return head;
    }


    private String getShare(List<SimOrder> orders, String field, boolean eq, String... vals) throws Exception {
        Class clazz = SimOrder.class;
        BigDecimal total = new BigDecimal(0);
        for (SimOrder order : orders) {
            String val = String.valueOf(clazz.getMethod("get" + StringUtil.capitalize(field)).invoke(order));
            if (eq && StringUtil.in(val, vals)) {
                BigDecimal amount = new BigDecimal(order.getShare());
                total = total.add(amount);
            } else if (!eq && !StringUtil.in(val, vals)) {
                BigDecimal amount = new BigDecimal(order.getShare());
                total = total.add(amount);
            }
        }
        return String.valueOf(total);
    }


    private String getAmount(List<SimOrder> orders, String field, boolean eq, String... vals) throws Exception {
        Class clazz = SimOrder.class;
        BigDecimal total = new BigDecimal(0);
        for (SimOrder order : orders) {
            String val = String.valueOf(clazz.getMethod("get" + StringUtil.capitalize(field)).invoke(order));
            if (eq && StringUtil.in(val, vals)) {
                BigDecimal amount = new BigDecimal(order.getAmount());
                total = total.add(amount);
            } else if (!eq && !StringUtil.in(val, vals)) {
                BigDecimal amount = new BigDecimal(order.getAmount());
                total = total.add(amount);
            }
        }
        return String.valueOf(total);
    }

    private List<SimOrder> findFileOrders(String orderType) {
        Example example = new Example(SimOrder.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(orderType)) {
            criteria.andEqualTo("orderType", orderType);
        }
        example.setOrderByClause("id desc");
        return super.selectByExample(example);
    }
}
