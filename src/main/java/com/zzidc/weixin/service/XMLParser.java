package com.zzidc.weixin.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

    public static Map<String,Object> getMapFromXML(String xmlString) throws ParserConfigurationException, IOException, SAXException {

        //这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is =  getStringStream(xmlString);
        Document document = builder.parse(is);

        //获取到document里面的全部结点
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        Map<String, Object> map = new HashMap<String, Object>();
        int i=0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if(node instanceof Element){
                map.put(node.getNodeName(),node.getTextContent());
            }
            i++;
        }
        return map;

    }
    public static InputStream getStringStream(String sInputString) throws UnsupportedEncodingException {
    	ByteArrayInputStream tInputStringStream = null;
    	if (sInputString != null && !sInputString.trim().equals("")) {
    		tInputStringStream = new ByteArrayInputStream(sInputString.getBytes("UTF-8"));
    	}
    	return tInputStringStream;
    }
    /**
     * XML节点名包含或一致Bean属性名
     * icesummer
     * 2015-7-15上午11:56:28
     * @param xmlString
     * @param tClass
     * @return
     */
    public static Object getObjectFromXML(String xmlString, Class<?> tClass) {
        //将从API返回的XML数据映射到Java对象
    	//这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
        try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream is =  getStringStream(xmlString);
			Document document = builder.parse(is);

			//获取到document里面的全部结点
			NodeList allNodes = document.getFirstChild().getChildNodes();
			Node node;
			/*XStream xStreamForResponseData = new XStream();
			xStreamForResponseData.alias("xml", tClass);
			xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
			return xStreamForResponseData.fromXML(xml);*/
			Map<String, Object> map = new HashMap<String, Object>();
			int i = 0;
			while (i < allNodes.getLength()) {
				node = allNodes.item(i);
				if (node instanceof Element) {
					map.put(node.getNodeName(), node.getTextContent());
				}
				i++;
			}
			Class<?> cls = tClass.getClass();
			Field[] fields = cls.getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				if (map.get(f.getName()) != null && map.get(f.getName()) != "") {
					f.set(f.getName(), map.get(f.getName()));
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
        return tClass;
    }

}
