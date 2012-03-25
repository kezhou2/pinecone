package com.tenline.game.simulation.moneytree.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.tenline.game.simulation.moneytree.shared.Order;

public class OrderPersistence {
	private static final String ORDER_XML_PATH="orders.xml";
	
	private static Document doc = null;
	private static XPath xpath = null;
	
	private static OrderPersistence instance = null;
	
	private OrderPersistence() throws ParserConfigurationException, SAXException, IOException{
		System.out.println("Cur path:"+System.getProperty("user.dir"));
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        factory.setNamespaceAware(true);  
        DocumentBuilder builder = factory.newDocumentBuilder();  
  
        File file = new File(ORDER_XML_PATH);  
        if(!file.exists()){
        	creatNewFile();
        }
        
        doc = builder.parse(file);  
        XPathFactory pathFactory = XPathFactory.newInstance();  
        xpath = pathFactory.newXPath();  
	}
	
	public static OrderPersistence getInstance() throws ParserConfigurationException, SAXException, IOException{
		if(instance == null){
			instance = new OrderPersistence();
		}
		
		return instance;
	}
	
	public void flush() throws TransformerException{
		TransformerFactory tfFactory =  TransformerFactory.newInstance();  
        Transformer tf = tfFactory.newTransformer();  
        StreamResult sResult = new StreamResult(new File(ORDER_XML_PATH));  
        DOMSource source = new DOMSource(doc);  
        tf.setOutputProperty(OutputKeys.VERSION, "1.0");  
        tf.setOutputProperty(OutputKeys.ENCODING, "utf-8");  
        tf.setOutputProperty(OutputKeys.INDENT, "yes");  
        tf.transform(source, sResult);
	}
	
	@SuppressWarnings("unused")
	public void addOrder(Order order) throws XPathExpressionException, TransformerException{
		String exp = "//orders";
		XPathExpression pathExpression = xpath.compile(exp);  
        Object result = pathExpression.evaluate(doc, XPathConstants.NODE);
        if(result != null){
        	Node node = (Node) result;
        	Element el0 = doc.createElement("order");
        	Element el1 = doc.createElement("orderid");
        	el1.setTextContent(order.getOrderId());
        	Element el2 = doc.createElement("userid");
        	el2.setTextContent(order.getUserId());
        	Element el3 = doc.createElement("amount");
        	el3.setTextContent(order.getAmount());
        	Element el4 = doc.createElement("sessionkey");
        	el4.setTextContent(order.getSessionKey());
        	el0.appendChild(el1);
        	el0.appendChild(el2);
        	el0.appendChild(el3);
        	el0.appendChild(el4);
        	doc.getDocumentElement().appendChild(el0);	
        	
//        	TransformerFactory tfFactory =  TransformerFactory.newInstance();  
//            Transformer tf = tfFactory.newTransformer();  
//            StreamResult sResult = new StreamResult(new File(ORDER_XML_PATH));  
//            DOMSource source = new DOMSource(doc);  
//            tf.setOutputProperty(OutputKeys.VERSION, "1.0");  
//            tf.setOutputProperty(OutputKeys.ENCODING, "utf-8");  
//            tf.setOutputProperty(OutputKeys.INDENT, "yes");  
//            tf.transform(source, sResult);
        }
	}
	
	public Order getOrderById(String orderid) throws XPathExpressionException{
		String exp = "/orders/order[orderid="+orderid+"]/orderid/text()";
		XPathExpression pathExpression = xpath.compile(exp);  
        Object result = pathExpression.evaluate(doc, XPathConstants.NODE);  
        if(result != null){
        	System.out.println("===============================Found");
        	Node node = (Node)result;
        	Order o = new Order();
        	o.setOrderId(node.getNodeValue());
        	
        	exp = "//order[orderid="+orderid+"]/userid/text()";
        	pathExpression = xpath.compile(exp);  
            result = pathExpression.evaluate(doc, XPathConstants.NODE);
            if(result != null){
            	o.setUserId(((Node)result).getNodeValue());
            }else{
            	return null;
            }
            
            exp = "//order[orderid="+orderid+"]/amount/text()";
        	pathExpression = xpath.compile(exp);  
            result = pathExpression.evaluate(doc, XPathConstants.NODE);
            if(result != null){
            	o.setAmount(((Node)result).getNodeValue());
            }else{
            	return null;
            }
            
            exp = "//order[orderid="+orderid+"]/sessionkey/text()";
        	pathExpression = xpath.compile(exp);  
            result = pathExpression.evaluate(doc, XPathConstants.NODE);
            if(result != null){
            	o.setSessionKey(((Node)result).getNodeValue());
            }else{
            	return null;
            }
        	
        	return o;
        }else{
        	System.out.println("===============================No Found");
        	return null;
        }
	}
	
	public void creatNewFile(){
		// 得到DOM解析器的工厂实例
		// javax.xml.parsers.DocumentBuilderFactory类的实例就是我们要的解析器工厂
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();

		try {

			// 通过javax.xml.parsers.DocumentBuilderFactory实例的静态方法newDocumentBuilder()得到DOM解析器
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();

			// 把要解析的XML文档转化为输入流，以便DOM解析器解析它
			// InputStream is = new FileInputStream(path);

			// 解析XML文档的输入流，得到一个Document
			// 由XML文档的输入流得到一个org.w3c.dom.Document对象，以后的处理都是对Document对象进行的
			Document doc = dombuilder.newDocument();

			// 创建根节点
			Element root = doc.createElement("orders");
			doc.appendChild(root);

//			// 创建第二级节点
//			Element orderElement = doc.createElement("order");
//
//			// 创建第三级节点
//			Element orderidElement = doc.createElement("orderid");
//			orderidElement.appendChild(doc.createTextNode("90"));
//			orderElement.appendChild(orderidElement);
//
//			Element useridElement = doc.createElement("userid");
//			useridElement.appendChild(doc.createTextNode("90%"));
//			orderElement.appendChild(useridElement);
//			
//			Element amountElement = doc.createElement("amount");
//			amountElement.appendChild(doc.createTextNode("1"));
//			orderElement.appendChild(amountElement);
//
//			root.appendChild(orderElement);

			// 将创建的doc文档输出成文件
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(doc);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			PrintWriter pw = new PrintWriter(new FileOutputStream(ORDER_XML_PATH));
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		try {
			
			OrderPersistence op = OrderPersistence.getInstance();
			Order o = new Order();
			o.setOrderId("94");
			o.setUserId("dssd");
			o.setAmount("6");
			o.setSessionKey("sdsds");
			op.addOrder(o);
			
			System.out.println(op.getOrderById("94").getAmount());
			
			op.flush();
//			OrderPersistence op1 = OrderPersistence.getInstance();
//			OrderPersistence op2 = OrderPersistence.getInstance();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
