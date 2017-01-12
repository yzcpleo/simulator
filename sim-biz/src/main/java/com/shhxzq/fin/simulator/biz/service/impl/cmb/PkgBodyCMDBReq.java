package com.shhxzq.fin.simulator.biz.service.impl.cmb;

/**
 * <p>Title: 资金处理系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author 
 * @version 1.0
 */
public class PkgBodyCMDBReq extends PkgCommon
{
    private String orgCorpDate; //原商户交易日期
    private String orgCorpFlowNo; //原商户交易流水
    private String orgCustCode;//原协议号

    public PkgBodyCMDBReq()
    {
        //初始化变量
        Init();
    }

    public PkgBodyCMDBReq(String inputStr) throws Exception
    {
        if (inputStr == null)
        {
            throw new Exception("报文返回字符串为空！");
        }

        //初始化变量
        Init();


        /**
         * 赋值并转换成byte数组
         */
        bankMessage = inputStr;
        byteBankMessage = inputStr.getBytes(); //转换成byte数组

        /**
         * 初始化计数器和银行返回信息长度
         */
        lenthCount = 0;
        byteBankMessageLen = byteBankMessage.length;

        /**
         * 注意，以下一定要按照招行文档的顺序，否则数据分析肯定报错！！
         */
        orgCorpDate = getFieldValue(orgCorpDate);
        orgCorpFlowNo = getFieldValue(orgCorpFlowNo);
        orgCustCode = getFieldValue(orgCustCode);

    }

    public void Init()
    {
        orgCorpDate = new String(new char[8]); //交易日期
        orgCorpFlowNo = new String(new char[30]); //交易流水
        orgCustCode = new String(new char[20]); //协议号
    }

    public void setOrgCorpDate(String orgCorpDate) throws Exception
    {
        orgCorpDate = getFillStr(this.orgCorpDate, orgCorpDate);

        this.orgCorpDate = orgCorpDate;
    }

    public void setOrgCorpFlowNo(String orgCorpFlowNo)
            throws Exception {
        orgCorpFlowNo = getFillStr(this.orgCorpFlowNo, orgCorpFlowNo);

        this.orgCorpFlowNo = orgCorpFlowNo;
    }

    public void setOrgCustCode(String orgCustCode)
            throws Exception {
        orgCustCode = getFillStr(this.orgCustCode, orgCustCode);

        this.orgCustCode = orgCustCode;
    }

    public String getOrgCorpDate()
    {
        return orgCorpDate;
    }

    public String getOrgCorpFlowNo()
    {
        return orgCorpFlowNo;
    }

    public String getOrgCustCode()
    {
        return orgCustCode;
    }

    /**
     * 获取包体
     * @return String
     */
    public String getPackage()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(orgCorpDate);
        sb.append(orgCorpFlowNo);
        sb.append(orgCustCode);

        return sb.toString();
    }}
