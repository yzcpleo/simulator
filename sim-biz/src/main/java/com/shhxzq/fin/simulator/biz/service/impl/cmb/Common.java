package com.shhxzq.fin.simulator.biz.service.impl.cmb;

import java.io.*;
import java.math.BigDecimal;

/**
 * <p>Title: Common</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2009</p>
 *
 */

public class Common {

    private static String msg(String f, String v) {
        return "属性设置错误：字符过长,最大长度为:" + f.length() + ",当前传递长度为:" + v.length();
    }

    /**
     * 获取变长字符串，不足位右补O
     *
     * @param oldStr String
     * @param newStr String
     * @return String
     * @throws Exception
     */
    public static String getUncertStrWithRightZero(String oldStr, String newStr) throws
            Exception {
        String returnStr = null;
        String fillStr = "0";
        String fillType = "R";
        String returnStrLen = new String(new char[2]);

        byte[] byteReturnStrLen = null;

        /**
         * 拼装字符串主体前的长度
         */
        if (newStr != null) {
            byteReturnStrLen = newStr.getBytes();
            //长度值不足两位左补零
            returnStrLen = getStrWithFillStr(returnStrLen, String.valueOf(byteReturnStrLen.length), fillStr, "L");
        }

        /**
         * 拼装字符串主体
         */
        returnStr = getStrWithFillStr(oldStr, newStr, fillStr, fillType);
        /**
         * 返回字符串＝长度+字符串主体
         */
        returnStr = returnStrLen + returnStr;

        return returnStr;
    }

    /**
     * 对字符串左补齐
     *
     * @param oldStr String
     * @param newStr String
     * @return String
     * @throws Exception
     */
    public static String getStrWithLeftBlank(String oldStr, String newStr) throws
            Exception {
        String returnStr = null;
        String fillStr = " ";
        String fillType = "L";

        returnStr = getStrWithFillStr(oldStr, newStr, fillStr, fillType);

        return returnStr;
    }

    /**
     * 对字符串右补齐
     *
     * @param oldStr String
     * @param newStr String
     * @return String
     * @throws Exception
     */
    public static String getStrWithRightBlank(String oldStr, String newStr) throws
            Exception {
        String returnStr = null;
        String fillStr = " ";
        String fillType = "R";

        returnStr = getStrWithFillStr(oldStr, newStr, fillStr, fillType);

        return returnStr;
    }

    /**
     * 对字符串左补齐0
     *
     * @param oldStr String
     * @param newStr String
     * @return String
     * @throws Exception
     */
    public static String getStrWithLeftZero(String oldStr, String newStr) throws
            Exception {
        String returnStr = null;
        String fillStr = "0";
        String fillType = "L";

        returnStr = getStrWithFillStr(oldStr, newStr, fillStr, fillType);

        return returnStr;
    }


    /**
     * 对字符串左补齐，或右补齐规定的字母
     *
     * @param oldStr   String
     * @param newStr   String
     * @param fillStr  String
     * @param fillType String
     * @return String
     * @throws Exception
     */
    private static String getStrWithFillStr(String oldStr, String newStr,
                                            String fillStr, String fillType) throws
            Exception {
        String returnStr = newStr;
        StringBuffer sbFillStr = new StringBuffer();

        int oldStrLen = 0;
        int newStrLen = 0;

        //默认是左补齐
        if (fillType == null) {
            fillType = "L";
        }

        //空字符串用空格代替
        if (newStr == null) {
            newStr = "";
        }

        if ((oldStr != null) && (newStr != null)) {
            if (oldStr.length() < newStr.length()) {
                throw new Exception(msg(oldStr, newStr));
            }

            if (newStr.length() < oldStr.length()) {
                //oldStrLen = oldStr.length();
                //newStrLen = newStr.length();
                //取字节数
                oldStrLen = oldStr.getBytes().length;
                newStrLen = newStr.getBytes().length;

                //如果是右补齐，先添加完原字符串后再补字符
                if (fillType.equals("R")) {
                    sbFillStr.append(newStr);
                }

                //增加需要的字符
                for (int i = newStrLen; i < oldStrLen; i++) {
                    sbFillStr.append(fillStr);
                }

                //如果是左补齐，则补完字符后再添加原字符串
                if (fillType.equals("L")) {
                    sbFillStr.append(newStr);
                }

                returnStr = sbFillStr.toString();
            }
        }

        return returnStr;
    }

    /**
     * 金额要求以分为单位,这里将指令池的金额格式转换成银行的格式
     * 此方法可以作为通用的方法
     *
     * @param money String
     * @return String
     */
    public String moneyCommandFormatToBankFormat(String inputMoney) {
        BigDecimal money = new BigDecimal(inputMoney);
        BigDecimal multMoney = new BigDecimal("100");
        String returnMoney = money.multiply(multMoney).toString();

        try {
            if (Integer.valueOf(returnMoney.substring(returnMoney.indexOf(".") + 1)).intValue() == 0) {
                returnMoney = returnMoney.substring(0, returnMoney.indexOf("."));
            }
        } catch (Exception e) {
        }

        return returnMoney;
    }

    /**
     * 金额要求以分为单位,这里将工行要求的格式转换成指令池的金额格式
     * 此方法可以作为通用的方法
     *
     * @param inputMoney String
     * @return String
     */
    public String moneyBankFormatToCommandFormat(String inputMoney) {
        BigDecimal money = new BigDecimal(inputMoney);
        BigDecimal divMoney = new BigDecimal("100");

        money = money.divide(divMoney, 2, BigDecimal.ROUND_HALF_UP);
        String returnMoney = money.toString(); //String.valueOf(money.doubleValue() / 100);

        try {
            if (Integer.valueOf(returnMoney.substring(returnMoney.indexOf(".") + 1)).intValue() == 0) {
                returnMoney = returnMoney.substring(0, returnMoney.indexOf("."));
            }
        } catch (Exception e) {
        }

        return returnMoney;
    }

    /**
     * 转换成指令池需要的数字
     *
     * @param inputMoney String
     * @return String
     */
    public String decimalBankFormatToCommandFormat(String inputMoney) {
        BigDecimal money = new BigDecimal(inputMoney);

        String returnMoney = money.toString(); //String.valueOf(money.doubleValue() / 100);

        return returnMoney;
    }

    /**
     * 将字符串转换成十六进制
     *
     * @param intputString String
     * @return String
     * @throws Exception
     */
    public String stringToHexString(String intputString) throws Exception {
        if (intputString == null) {
            throw new Exception("调用stringToHexString时出错，原因：intputString为空！");
        }

        byte[] byteInputStrings = intputString.getBytes();
        byte byteInputString;

        String tempString = "";
        StringBuffer sbReturnString = new StringBuffer();

        for (int i = 0; i < byteInputStrings.length; i++) {
            tempString = "";
            byteInputString = byteInputStrings[i];

            tempString = Integer.toHexString(byteInputString);

            if (tempString.length() < 2) {
                tempString = "0" + tempString;
            }

            tempString = tempString.substring(tempString.length() - 2, tempString.length());

            sbReturnString.append(tempString);
        }

        return sbReturnString.toString().toUpperCase();
    }

    public static boolean saveString2File(InputStream ins,
                                          String fileName) {
        if (ins == null) {
            return false;
        }
        if (fileName == null) {
            return false;
        }
        try {
            File file = new File(fileName);
            OutputStream outs = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = ins.read(b)) != -1) {
                outs.write(b, 0, len);
            }
            outs.flush();
            outs.close();
            ins.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }


}
