/**
 * 
 */
package com.tenline.pinecone.platform.monitor.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;

import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.monitor.IConstants;
import com.tenline.pinecone.platform.sdk.ModelAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	/**
	 * Endpoint Admin
	 */
	private EndpointAdmin endpointAdmin;
	
	/**
	 * Screen Size
	 */
	private Dimension screenSize;
	
	/**
	 * Web Service API
	 */
	private ModelAPI modelAPI;

	/**
	 * 
	 */
	public MainWindow() {
		super("Device Console");
		// TODO Auto-generated constructor stub
		center();
		endpointAdmin = new EndpointAdmin();
		modelAPI = new ModelAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, IConstants.WEB_SERVICE_CONTEXT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(new MenuBar());
		setContentPane(new MainPanel());
		setVisible(true);
		new LoginDialog().setVisible(true);
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
	private void center() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width / 2, screenSize.height / 2);
	    setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class MenuBar extends JMenuBar {
		
		private MenuBar() {
			JMenu menu = new JMenu("User");
			menu.setMnemonic('U');
			JMenuItem menuItem = new JMenuItem("Login");
		    menuItem.setMnemonic('L');
		    menuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					// TODO Auto-generated method stub
					new LoginDialog().setVisible(true);
				}
				
	        });
		    menu.add(menuItem);
		    menu.addSeparator();
	        menuItem = new JMenuItem("Exit");
	        menuItem.setMnemonic('x');
	        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_MASK));
	        menuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
				
	        });
	        menu.add(menuItem);
			add(menu);
			menu = new JMenu("Help");
			menu.setMnemonic('H');
			menuItem = new JMenuItem("About");
	        menuItem.setMnemonic('A');
	        menu.add(menuItem);
			add(menu);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class LoginDialog extends JDialog {
		  
		private LoginDialog() {
			super(MainWindow.this, "Login", true);
			setSize(getOwner().getWidth() / 2, getOwner().getHeight() / 2);
			setLocationRelativeTo(getOwner());
			setLayout(new BorderLayout());
			add(new LoginPanel(this), BorderLayout.CENTER);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class LoginPanel extends JPanel {
		
		private LoginPanel(final LoginDialog dialog) {
			setLayout(new GridLayout(3, 2, 3, 3));
			setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));
		    add(new JLabel("Email:"));
		    final JTextField emailField = new JTextField();
		    add(emailField);
		    add(new JLabel("Password:"));
		    final JPasswordField passwordField = new JPasswordField();
		    add(passwordField);
		    JButton submitButton = new JButton("Submit");
		    submitButton.addActionListener(new ActionListener() {

				@Override
				@SuppressWarnings("unchecked")
				public void actionPerformed(ActionEvent event) {
					// TODO Auto-generated method stub
					try {
						String filter = "email=='" + emailField.getText() +
						"'&&password=='" + passwordField.getPassword().toString() + "'";
						APIResponse response = modelAPI.show(User.class, filter);
						if (response.isDone()) {
							Collection<User> users = (Collection<User>) response.getMessage();
							System.out.println(users.size());
							if (users.size() == 1) dialog.setVisible(false);	
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		    	
		    });
		    add(submitButton);
		    JButton cancelButton = new JButton("Cancel");
		    cancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					// TODO Auto-generated method stub
					dialog.setVisible(false);
				}
		    	
		    });
		    add(cancelButton);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class MainPanel extends JPanel {
		
		private MainPanel() {
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
			add(new DevicePanel(), BorderLayout.CENTER);
			add(new DriverPanel(), BorderLayout.WEST);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class DriverPanel extends JScrollPane {
		
		private DriverPanel() {
			super(new JTree());
			setPreferredSize(new Dimension((int) screenSize.getWidth() / 6, (int) screenSize.getHeight() / 2));
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class DevicePanel extends JScrollPane {
		
		private DevicePanel() {
			super(new JTable(new Object[][]{}, new Object[]{ "Name", "Address", "Port" }));
//			JTable deviceTable = (JTable) getViewport().getView();
		}
		
	}

}
