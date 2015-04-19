package com.jobfinder.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import net.sf.json.JSONObject;

public class Xml2JsonUtil {
	/**
	 * parse xml string to json string
	 * 
	 * @param xml
	 *            xml string
	 * @return sucess return json string;fail return null
	 */
	@SuppressWarnings("unchecked")
	public static String xml2JSON(String xml) {
		JSONObject obj = new JSONObject();
		try {
			InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			obj.put(root.getName(), iterateElement(root));
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * an interation function
	 * 
	 * @param element
	 *            : org.jdom.Element
	 * @return java.util.Map instance
	 */
	@SuppressWarnings("unchecked")
	private static Map iterateElement(Element element) {
		List jiedian = element.getChildren();
		Element et = null;
		Map obj = new HashMap();
		List list = null;
		for (int i = 0; i < jiedian.size(); i++) {
			list = new LinkedList();
			et = (Element) jiedian.get(i);
			if (et.getTextTrim().equals("")) {
				if (et.getChildren().size() == 0)
					continue;
				if (obj.containsKey(et.getName())) {
					list = (List) obj.get(et.getName());
				}
				list.add(iterateElement(et));
				obj.put(et.getName(), list);
			} else {
				if (obj.containsKey(et.getName())) {
					list = (List) obj.get(et.getName());
				}
				list.add(et.getTextTrim());
				obj.put(et.getName(), list);
			}
		}
		return obj;
	}

	// test
	public static void main(String[] args) {
		System.out.println(Xml2JsonUtil.xml2JSON("<MapSet>"
				+ "<MapGroup id='Sheboygan'>" + "<Map>"
				+ "<Type>MapGuideddddddd</Type>"
				+ "<SingleTile>true</SingleTile>" + "<Extension>"
				+ "<ResourceId>ddd</ResourceId>" + "</Extension>" + "</Map>"
				+ "<Map>" + "<Type>ccc</Type>" + "<SingleTile>ggg</SingleTile>"
				+ "<Extension>" + "<ResourceId>aaa</ResourceId>"
				+ "</Extension>" + "</Map>" + "<Extension />" + "</MapGroup>"
				+ "<ddd>" + "33333333" + "</ddd>" + "<ddd>" + "444" + "</ddd>"
				+ "</MapSet>"));
	}
}