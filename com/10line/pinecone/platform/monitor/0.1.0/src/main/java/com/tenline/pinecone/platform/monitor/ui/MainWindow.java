/**
 * 
 */
package com.tenline.pinecone.platform.monitor.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;
import com.tenline.pinecone.platform.monitor.BundleHelper;
import com.tenline.pinecone.platform.monitor.EndpointAdmin;
import com.tenline.pinecone.platform.monitor.IConstants;
import com.tenline.pinecone.platform.monitor.ServiceHelper;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.ItemAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.VariableAPI;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * Endpoint Admin
	 */
	private EndpointAdmin endpointAdmin;

	/**
	 * Web Service API
	 */
	private User user;
	private UserAPI userAPI;

	private Device device;
	private DeviceAPI deviceAPI;

	private Variable variable;
	private VariableAPI variableAPI;

	private Item item;
	private ItemAPI itemAPI;

	/**
	 * UI Controls
	 */
	private JButton showDevicesButton;

	/**
	 * 
	 */
	public MainWindow() {
		endpointAdmin = new EndpointAdmin();
		userAPI = new UserAPI(IConstants.WEB_SERVICE_HOST,
				IConstants.WEB_SERVICE_PORT, new APIListener() {

					@Override
					public void onMessage(Object message) {
						// TODO Auto-generated method stub
						user = (User) ((Collection<?>) message).toArray()[0];
						showDevicesButton.setEnabled(true);
						endpointAdmin.initialize(user);
					}

					@Override
					public void onError(String error) {
						// TODO Auto-generated method stub
						showDevicesButton.setEnabled(false);
						logger.error(error);
					}

				});
		deviceAPI = new DeviceAPI(IConstants.WEB_SERVICE_HOST,
				IConstants.WEB_SERVICE_PORT, new APIListener() {

					@Override
					public void onMessage(Object message) {
						// TODO Auto-generated method stub
						device = (Device) message;
						logger.info("Device: " + device.getId());
					}

					@Override
					public void onError(String error) {
						// TODO Auto-generated method stub
						logger.error(error);
					}

				});
		variableAPI = new VariableAPI(IConstants.WEB_SERVICE_HOST,
				IConstants.WEB_SERVICE_PORT, new APIListener() {

					@Override
					public void onMessage(Object message) {
						// TODO Auto-generated method stub
						variable = (Variable) message;
						logger.info("Variable: " + variable.getId());
					}

					@Override
					public void onError(String error) {
						// TODO Auto-generated method stub
						logger.error(error);
					}

				});
		itemAPI = new ItemAPI(IConstants.WEB_SERVICE_HOST,
				IConstants.WEB_SERVICE_PORT, new APIListener() {

					@Override
					public void onMessage(Object message) {
						// TODO Auto-generated method stub
						item = (Item) message;
						logger.info("Item: " + item.getId());
					}

					@Override
					public void onError(String error) {
						// TODO Auto-generated method stub
						logger.error(error);
					}

				});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 395, 427);
		initializeMainPanel();
	}

	/**
	 * 
	 */
	public void close() {
		endpointAdmin.close();
	}

	/**
	 * 
	 */
	private void initializeMainPanel() {
		final JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		setContentPane(panel);
		JLabel label = new JLabel("SNS Id:");
		label.setBounds(41, 32, 73, 15);
		panel.add(label);
		final JTextField textField = new JTextField();
		textField.setBounds(124, 29, 119, 23);
		panel.add(textField);
		textField.setColumns(10);
		textField.setText("251760162"); // SNS Id
		JButton button = new JButton("Login");
		button.setActionCommand("Login");
		button.setBounds(260, 29, 80, 23);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					userAPI.show("snsId=='" + textField.getText() + "'");
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					logger.error(ex.getMessage());
				}
			}
		});
		panel.add(button);
		showDevicesButton = new JButton("Show Devices");
		showDevicesButton.setActionCommand("Show Devices");
		showDevicesButton.setBounds(100, 100, 130, 25);
		showDevicesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DeviceDialog().setVisible(true);
			}
		});
		panel.add(showDevicesButton);
		showDevicesButton.setEnabled(false);
	}

	private class DeviceDialog extends JDialog {

		private JComboBox comboBox;

		/**
		 * 
		 */
		public DeviceDialog() {
			super(MainWindow.this);
			setBounds(100, 100, 462, 202);
			getContentPane().setLayout(new BorderLayout());
			initializeDevicesPanel();
			initializeOperationPanel();
		}

		/**
		 * 
		 */
		private void initializeDevicesPanel() {
			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			panel.setLayout(null);
			getContentPane().add(panel, BorderLayout.CENTER);
			comboBox = new JComboBox();
			Device[] items = BundleHelper.getRemoteBundles();
			for (Device item : items) {
				comboBox.addItem(item.getName() + " (" + item.getSymbolicName()
						+ "-" + item.getVersion() + ")");
			}
			comboBox.setBounds(26, 71, 398, 32);
			panel.add(comboBox);
			JLabel label = new JLabel("Devices:");
			label.setBounds(26, 29, 82, 15);
			panel.add(label);
		}

		/**
		 * 
		 */
		private void initializeOperationPanel() {
			JPanel panel = new JPanel();
			panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(panel, BorderLayout.SOUTH);
			JButton button = new JButton("Add Device");
			button.setActionCommand("Add Device");
			panel.add(button);
			getRootPane().setDefaultButton(button);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						String temp = comboBox.getSelectedItem().toString();
						String[] identifier = temp.substring(
								temp.indexOf("(") + 1, temp.length() - 1)
								.split("-");
						Bundle bundle = BundleHelper.getBundle(identifier[0],
								identifier[1]);
						Device metaData = ((AbstractProtocolBuilder) ServiceHelper.waitForService
								(AbstractProtocolBuilder.class, bundle.getSymbolicName(), 
								bundle.getVersion().toString())).getMetaData();
						device = new Device();
						device.setName(metaData.getName());
						device.setSymbolicName(metaData.getSymbolicName());
						device.setVersion(metaData.getVersion());
						device.setUser(user);
						deviceAPI.create(device);
						for (Variable vMetaData : metaData.getVariables()) {
							variable = new Variable();
							variable.setName(vMetaData.getName());
							variable.setType(vMetaData.getType());
							variable.setDevice(device);
							variableAPI.create(variable);
							if (vMetaData.getItems() != null) {
								for (Item iMetaData : vMetaData.getItems()) {
									item = new Item();
									item.setText(iMetaData.getText());
									item.setValue(iMetaData.getValue());
									item.setVariable(variable);
									itemAPI.create(item);
								}
							}
						}
						endpointAdmin.initializeEndpoint(device);
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						logger.error(ex.getMessage());
					}
				}
			});
		}

	}

}
