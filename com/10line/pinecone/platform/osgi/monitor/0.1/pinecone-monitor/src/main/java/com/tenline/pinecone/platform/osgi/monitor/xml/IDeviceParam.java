package com.tenline.pinecone.platform.osgi.monitor.xml;

import java.util.List;

import com.tenline.pinecone.platform.osgi.monitor.model.Command;
import com.tenline.pinecone.platform.osgi.monitor.model.Point;
import com.tenline.pinecone.platform.osgi.monitor.model.point.PointType;

public interface IDeviceParam {

	/**
	 * @param dataType
	 * @param filePath
	 * @return
	 */
	public abstract PointType getPointType(String dataType, String basePath,String filePath);

	/**
	 * @todo get control point
	 * @param pointId
	 *            String
	 * @return Point
	 */
	public abstract Point getControlPoint(String pointId);

	/**
	 * @todo get state point
	 * @param pointId
	 *            String
	 * @return Point
	 */
	public abstract Point getStatePoint(String pointId);

	public abstract Command getCommand(String deviceId, String commandId);

	public abstract List<Command> getCommands();

}