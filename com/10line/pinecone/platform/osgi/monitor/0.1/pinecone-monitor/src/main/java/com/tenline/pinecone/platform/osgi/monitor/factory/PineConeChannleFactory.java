package com.tenline.pinecone.platform.osgi.monitor.factory;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.osgi.monitor.model.ChannelObject;
import com.tenline.pinecone.platform.osgi.monitor.tool.CommandReceiveQueue;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.ChannelAPI;

public class PineConeChannleFactory {
	private static Logger logger = Logger
			.getLogger(PineConeChannleFactory.class);
	/**
	 * subscribe channel
	 */
	private HashMap<String, ChannelAPI> subChannelMap;
	/**
	 * publish channel
	 */
	private HashMap<String, ChannelAPI> pubChannelMap;

	/**
	 * instance
	 */
	private static PineConeChannleFactory instance;

	/**
	 * @return
	 */
	public static PineConeChannleFactory getInstance() {
		if (instance == null) {
			instance = new PineConeChannleFactory();
		}
		return instance;
	}

	/**
	 * factory method
	 */
	private PineConeChannleFactory() {
		subChannelMap = new HashMap<String, ChannelAPI>();
		pubChannelMap = new HashMap<String, ChannelAPI>();
	}

	/**
	 * @param deivceCmdID
	 * @return state channel
	 */
	public ChannelAPI getStatePublishCNL(String deivceCmdID) {
		if (!pubChannelMap.containsKey(deivceCmdID)) {
			ChannelAPI channelAPI = new ChannelAPI(
					"pinecone.web.service.10line.cc", "80", new APIListener() {
						@Override
						public void onMessage(Object message) {
							logger.info("Publish result : "
									+ message.toString());
						}

						@Override
						public void onError(String error) {
							logger.error("Publish result : " + error);
						}

					});
			pubChannelMap.put(deivceCmdID, channelAPI);
		}
		return pubChannelMap.get(deivceCmdID);
	}

	/**
	 * @param variableId
	 * @return sub channel
	 */
	private ChannelAPI getCommandSubscribeCNL(final String variableId) {
		if (!subChannelMap.containsKey(variableId)) {
			ChannelAPI channelAPI = new ChannelAPI(
					"pinecone.web.service.10line.cc", "80", new APIListener() {
						@Override
						public void onMessage(Object message) {
							logger.info("get subscribe ok: " + variableId
									+ " = " + message.toString());
							CommandReceiveQueue.getInstance().add(
									new ChannelObject(variableId, message));
						}

						@Override
						public void onError(String error) {
							logger.error("get subscribe error: " + variableId
									+ " : " + error);
						}

					});
			subChannelMap.put(variableId, channelAPI);
		}
		return subChannelMap.get(variableId);
	}

	public void subscribe(String variableId) {
		try {
			getCommandSubscribeCNL(variableId).subscribe(variableId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
