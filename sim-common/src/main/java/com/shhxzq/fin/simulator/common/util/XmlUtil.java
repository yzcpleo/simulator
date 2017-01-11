package com.shhxzq.fin.simulator.common.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/1/11
 */
public class XmlUtil {

    /**
     * 解析xml报文
     *
     * @param xml
     * @return
     */
    public static Map<String, String> parseXml(String xml) {
        Map<String, String> map = new HashMap();
        try {
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            buildMap(root, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap();
        }
        return map;
    }

    private static void buildMap(Element element, Map<String, String> map) {
        List<Element> list = element.elements();
        if (element.attributeCount() > 0) {
            map.put(element.getName(), element.attribute(0).getValue());
        } else {
            map.put(element.getName(), element.getTextTrim());
        }

        for (Element e : list) {
            buildMap(e, map);
        }
    }

}
