/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor.tool;

import org.apache.log4j.PropertyConfigurator;

/**
 * @author Administrator
 * 
 */
public class FilePath {
	public static String Log4JPath = "../config/log4j.properties";
	static {
		PropertyConfigurator.configure(FilePath.Log4JPath);
	}
	public static String DevicePath = "/device.xml";
	public static String SocketPath = "/socket.xml";
	public static String PortPath = "/port.xml";
	public static String CommandPath = "/commands.xml";
	public static String ControlPointPath = "/controlPoints.xml";
	public static String StatePointPath = "/statePoints.xml";
	public static String PointTypePath = "/pointTypes.xml";
}
