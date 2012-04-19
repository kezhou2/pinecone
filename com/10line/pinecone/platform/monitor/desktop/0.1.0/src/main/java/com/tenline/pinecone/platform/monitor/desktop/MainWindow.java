/**
 * 
 */
package com.tenline.pinecone.platform.monitor.desktop;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;
import org.osgi.service.obr.Resource;

import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.monitor.APIHelper;
import com.tenline.pinecone.platform.monitor.BundleHelper;

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
	 * UI Controls
	 */
	private JButton showDevicesButton;

	/**
	 * 
	 */
	public MainWindow() {
		endpointAdmin = new EndpointAdmin();
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
					User user = APIHelper.login("", "");
					if (user != null) {
						showDevicesButton.setEnabled(true);
						endpointAdmin.initialize(user);
					} else {
						showDevicesButton.setEnabled(false);
					}
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
			Hashtable<String, Resource> items = BundleHelper.getRemoteBundles();
			for (Enumeration<String> keys = items.keys(); keys.hasMoreElements();) {
				String key = keys.nextElement();
				comboBox.addItem(items.get(key).getPresentationName() + " (" + items.get(key).getSymbolicName()
						+ "-" + items.get(key).getVersion().toString() + ")");
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
						String[] identifier = temp.substring(temp.indexOf("(") + 1, temp.length() - 1).split("-");
						endpointAdmin.initializeEndpoint(APIHelper.createDevice(identifier[0], identifier[1]));
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						logger.error(ex.getMessage());
					}
				}
			});
		}

	}

}
