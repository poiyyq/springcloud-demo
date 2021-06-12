package com.epoch.webservices.utils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * xml工具类
 * @author yanyq
 * @date 2021年06月10日
 */
public class XmlUtils {
    private static String xmlStatement = "<?xml version=\"1.0\" encoding=\"GBK\"?>";


    public static void main(String[] args) {
//        Map<String,Object> data = new HashMap<>();
//        Map<String,Object> pgk = new HashMap<>();
//        Map<String,Object> checkcode = new HashMap<>();
//        data.put("PGK",pgk);
//        pgk.put("CHECKCODE",123);
//        System.out.println(createXmls(data));

        String xmls = "<PGK>\n" +
                "<DATA>\n" +
                "<CBSERPPGK>\n" +
                "<INFO>\n" +
                "<FUNNAM>OCEPYIMP</FUNNAM>\n" +
                "</INFO>\n" +
                "<OCPAYADDX>\n" +
                "<BUSREF>0000000001</BUSREF>\n" +
                "<PBCACC>1249732008</PBCACC>\n" +
                "<PAYCCY>CNY</PAYCCY>\n" +
                "<PAYAMT>123.00</PAYAMT>\n" +
                "<ORGCCY>HKD</ORGCCY>\n" +
                "<ORGAMT>150.00</ORGAMT>\n" +
                "<RBKNAM>CITIBANK N.A.</RBKNAM>\n" +
                "<REVACC>1249732024</REVACC>\n" +
                "<REVNAM>MOBVISTA INTERNATIONAL</REVNAM>\n" +
                "<RBKBIC>CITIHKAXXXX</RBKBIC>\n" +
                "<RBKADR>HONG KONG</RBKADR>\n" +
                "<REVADR>MOBVISTA INTERNATIONAL</REVADR>\n" +
                "<EXCDTE>2021-05-13</EXCDTE>\n" +
                "<AGTFLG>N</AGTFLG>\n" +
                "<TXTDSM>TEST</TXTDSM>\n" +
                "<PAYCHL>1</PAYCHL>\n" +
                "<PAYTYP>1</PAYTYP>\n" +
                "<PAYSON>N</PAYSON>\n" +
                "<FEESID>OUR</FEESID>\n" +
                "<FEEACC>99123</FEEACC>\n" +
                "<ISIBAN>N</ISIBAN>\n" +
                "<PAYEID>110033</PAYEID>\n" +
                "<PAYPOP>121</PAYPOP>\n" +
                "<REVPOP>CSTR</REVPOP>\n" +
                "<TXTDSM>TEST</TXTDSM>\n" +
                "<INSCOD>TT</INSCOD>\n" +
                "</OCPAYADDX>\n" +
                "<OCREVDTAX>\n" +
                "<SQRNBR>1</SQRNBR>\n" +
                "<BUSREF>0000000001</BUSREF>\n" +
                "<TRSAMT>123.00</TRSAMT>\n" +
                "<REVNAM>MOBVISTA INTERNATIONAL</REVNAM>\n" +
                "<REVACC>1249732024</REVACC>\n" +
                "<RBKBIC>CITIHKAXXXX</RBKBIC>\n" +
                "<ORGAMT>150.00</ORGAMT>\n" +
                "<ORGCCY>HKD</ORGCCY>\n" +
                "<FEESID>OUR</FEESID>\n" +
                "<INFTXT>TEST</INFTXT>\n" +
                "<CNTCOD>CN</CNTCOD>\n" +
                "</OCREVDTAX>\n" +
                "</CBSERPPGK>\n" +
                "</DATA>\n" +
                "<CHECKCODE>Z105D8555</CHECKCODE>\n" +
                "</PGK>";
        Multimap<String, Object> map = parseXmlsAsMap(xmls);
    }


    /**
     * 递归获取根据map结构自动生成xml报文
     * @param data
     * @return
     */
    public static String recursionCreateXmls(Map<String,Object> data){
        StringBuilder sb = new StringBuilder();
        Set<String> keys = data.keySet();
        for (String key : keys) {
            sb.append("<").append(key).append(">");
            // 填值
            Object val = data.get(key);
            if(val!=null){
                if(val instanceof Map){
                    // 循环遍历
                    sb.append(recursionCreateXmls((Map<String,Object>)val));
                } else {
                    sb.append(val);
                }
            }
            sb.append("</").append(key).append(">");
        }
        return sb.toString();
    }

    /**
     * 根据map结构自动生成xml报文
     * @param data
     * @return
     */
    public static String createXmls(Map<String,Object> data, String xmlStatement){
        return xmlStatement + recursionCreateXmls(data);
    }
    /**
     * 根据map结构自动生成xml报文
     * @param data
     * @return
     */
    public static String createXmls(Map<String,Object> data){
        return createXmls(data,xmlStatement);
    }

    /**
     * 生成不带声明的xml字符串
     * @param dataMap
     * @return
     */
    public static String createXmlsWithoutStatement(Map<String, Object> dataMap) {
        return createXmls(dataMap,"");
    }

    /**
     * 把xmls格式数据转化成map
     * @param xmls
     * @return
     */
    public static Multimap<String, Object> parseXmlsAsMap(String xmls) {
        Multimap<String,Object> map = ArrayListMultimap.create();
        Document rootDoc;
        try {
            rootDoc = DocumentHelper.parseText(xmls);
            Iterator<Element> iterator = rootDoc.nodeIterator();
            while(iterator.hasNext()){
                Element doc = iterator.next();
                recursionCreateMap(doc,map);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 递归生成Map
     * @param element
     * @param map
     */
    private static void recursionCreateMap(Element element, Multimap<String, Object> map) {
        Iterator<Element> iterator = element.elementIterator();
        String name = element.getName();
        if(iterator.hasNext()){
            while(iterator.hasNext()){
                // 还有子节点
                Multimap<String,Object> childMap = ArrayListMultimap.create();
                map.put(name,childMap);
                Element child = iterator.next();
                recursionCreateMap(child,childMap);
            }
        } else {
            // 若无子节点，则取当前元素
            String value = element.getTextTrim();
            map.put(name,value);
        }
    }

}
