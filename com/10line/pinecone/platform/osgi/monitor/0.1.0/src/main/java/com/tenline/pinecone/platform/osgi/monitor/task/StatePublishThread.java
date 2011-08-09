/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor.task;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.Record;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.osgi.monitor.factory.PineConeChannleFactory;
import com.tenline.pinecone.platform.osgi.monitor.model.Command;
import com.tenline.pinecone.platform.osgi.monitor.model.Point;
import com.tenline.pinecone.platform.osgi.monitor.model.point.PointComboBoxGroup;
import com.tenline.pinecone.platform.osgi.monitor.service.RecordService;
import com.tenline.pinecone.platform.osgi.monitor.tool.StateSendQueue;

/**
 * @author Administrator
 * 
 */
public class StatePublishThread implements Runnable {
	private static Logger logger = Logger.getLogger(StatePublishThread.class);
	private boolean flag = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (flag) {
			try {
				Object message = StateSendQueue.getInstance().poll();
				if (message == null) {
					Thread.sleep(1000);
					continue;
				}
				Command command = (Command) message;
				publish(command);
				saveRecordDao(command);
				Thread.sleep(100);
			} catch (Exception e) {
				logger.error(e.toString(), e);
			}

		}
	}

	/**
	 * @param command
	 * @return publish
	 */
	private void publish(Command command) {
		try {
			List<Point> stateList = command.getStatePointList();
			for (Point p : stateList) {
				String id = p.getVariable().getId()+ "-" + "monitor";
				logger.info("Publish : " + id + ", values: " + p.getValue());
				PineConeChannleFactory
						.getInstance()
						.getStatePublishCNL(id)
						.publish(id, "text/plain",
								p.getValue().toString().getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param command
	 * @reeturn save command to db
	 */
	private void saveRecordDao(Command command) {
		try {
			List<Point> stateLists = command.getStatePointList();
			for (Point p : stateLists) {
				Variable variable = p.getVariable();
				if (p.getGuiComboxItems().size() <= 1) {
					Record rec = new Record();
					rec.setTimestamp(new Date());
					rec.setValue(p.getValue().toString());
					rec.setVariable(variable);
					RecordService.getInstance().saveRecord(rec);
				} else {
					LinkedList<PointComboBoxGroup> comboxItems = p
							.getGuiComboxItems();
					for (PointComboBoxGroup group : comboxItems) {
						Record rec = new Record();
						rec.setTimestamp(new Date());
						rec.setVariable(variable);
						rec.setValue(group.getValue());
						RecordService.getInstance().saveRecord(rec);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
