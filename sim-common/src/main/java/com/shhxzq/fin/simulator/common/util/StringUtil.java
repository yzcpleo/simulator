package com.shhxzq.fin.simulator.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author kangyonggan
 * @since 2016/12/10
 */
public class StringUtil {

    /**
     * 驼峰单词转数组
     *
     * @param word
     * @return
     */
    public static String[] camelToArray(String word) {
        if (StringUtils.isEmpty(word)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        char chars[] = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (Character.isUpperCase(ch)) {
                sb.append("_").append(Character.toLowerCase(ch));
            } else {
                sb.append(ch);
            }
        }

        if (sb.toString().startsWith("_")) {
            sb.deleteCharAt(0);
        }
        if (sb.toString().endsWith("_")) {
            sb.deleteCharAt(sb.lastIndexOf("_"));
        }

        return sb.toString().split("_");
    }

    /**
     * <pre>
     * StringUtil.isEmpty(null) = true
     * StringUtil.isEmpty("") = true
     * StringUtil.isEmpty("    ") = true
     * StringUtil.isEmpty("abc") = false
     * </pre>
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * <pre>
     * StringUtil.capitalize(null)  = null
     * StringUtil.capitalize("")    = ""
     * StringUtil.capitalize("cat") = "Cat"
     * StringUtil.capitalize("cAt") = "CAt"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char firstChar = str.charAt(0);
        if (Character.isTitleCase(firstChar)) {
            return str;
        }

        return new StringBuilder(str.length())
                .append(Character.toTitleCase(firstChar))
                .append(str.substring(1))
                .toString();
    }

    /**
     * 首字母变大写
     *
     * @param str
     * @return
     */
    public static String firstToUpperCase(String str) {
        if (str == null || str.trim().length() == 0) {
            return str;
        }
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 两边都加上%
     *
     * @param str
     * @return
     */
    public static String toLikeString(String str) {
        if (str == null) {
            str = "";
        }
        return String.format("%%%s%%", str);
    }

    /**
     * 判断str是否在arr中
     *
     * @param str
     * @param arr
     * @return
     */
    public static boolean in(String str, String... arr) {
        for (String s : arr) {
            if (s.equals(str)) {
                return true;
            }
        }
        return false;
    }

}
