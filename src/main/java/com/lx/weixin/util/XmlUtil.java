package com.lx.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

/**
 * XML工具类
 * @author lx
 *
 */
public class XmlUtil {

	/**
	 * 将xml转换为Map
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		InputStream ins = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document document = reader.read(ins);
		Element rootElement = document.getRootElement();
		List<Element> elements = rootElement.elements();
		
		for (Element element : elements) {
			resultMap.put(element.getName(), element.getText());
		}
		ins.close();
		return resultMap;
	}
	
	/**
	 * 将对象转换为xml
	 * @param object
	 * @return
	 */
	public static String objToXml(Object object, String xmlRootElementAlias) {
		
		XStream xStream = new XStream();
		xStream.alias(xmlRootElementAlias, object.getClass());
		String xml = xStream.toXML(object);
		return xml;
	}
	
	/**
	 * 将对象转换为xml，支持设置xml的多个元素的别名
	 * @param object
	 * @return
	 */
	public static String objToXml(Object object, Map<String, Object> xmlElementAliasMap) {
		
		XStream xStream = new XStream();
		
		Set<String> aliasSet = xmlElementAliasMap.keySet();
		for (String alias : aliasSet) {
			xStream.alias(alias, xmlElementAliasMap.get(alias).getClass());
		}
		String xml = xStream.toXML(object);
		return xml;
	}
}
