package com.tenline.pinecone.platform.osgi.monitor.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * <p>
 * Description：载入指定的XML文件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company:航天恒星科技有限公司软件中心
 * </p>
 * <p>
 * LastEdit:2010.11.17
 * </p>
 * 
 * @author qi xinhu
 */
public class XMLLoad {
	private static Logger logger = Logger.getLogger(XMLLoad.class);

	public static Document getXMLDocument(String xmlFilePath) {
		SAXBuilder saxb = new SAXBuilder();
		Document doc = null;
		try {
			doc = saxb.build(new FileInputStream(xmlFilePath));
			// build(new File(xmlFilePath));
			// build(new FileInputStream(xmlFilePath));
		} catch (FileNotFoundException e) {
			System.out.println(System.getProperty("user.dir"));
			logger.warn(e.toString(), e);
			return null;
		} catch (JDOMException e) {
			logger.warn(e.toString(), e);
			return null;
		} catch (IOException e) {
			logger.warn(e.toString(), e);
			return null;
		}
		return doc;
	}

}
