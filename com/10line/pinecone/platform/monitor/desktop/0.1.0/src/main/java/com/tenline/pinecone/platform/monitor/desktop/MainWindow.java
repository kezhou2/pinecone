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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Driver;
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
	 * Screen Size
	 */
	private Dimension screenSize;
	
	/**
	 * Web Service API
	 */
	private ModelAPI modelAPI;
	
	/**
	 * OSGI Bundle
	 */
	private Bundle bundle;
	
	/**
	 * Current User
	 */
	private User currentUser;
	
	/**
	 * Driver Tree
	 */
	private DriverTree driverTree;

	/**
	 * 
	 * @param bundleContext
	 * @throws Exception 
	 */
	public MainWindow(BundleContext bundleContext) throws Exception {
		// TODO Auto-generated constructor stub
		center();
		bundle = bundleContext.getBundle();
		modelAPI = new ModelAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, IConstants.WEB_SERVICE_CONTEXT);
		driverTree = new DriverTree();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(bundle.getHeaders().get("Device-Console").toString());
		setJMenuBar(new MenuBar());
		setContentPane(new MainPanel());
		setVisible(true);
		new LoginDialog().setVisible(true);
	}
	
	/**
	 * 
	 */
	public void close() {
		////////
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
			JMenu menu = new JMenu(bundle.getHeaders().get("User").toString());
			menu.setMnemonic('U');
			JMenuItem menuItem = new JMenuItem(bundle.getHeaders().get("Login").toString());
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
	        menuItem = new JMenuItem(bundle.getHeaders().get("Exit").toString());
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
			menu = new JMenu(bundle.getHeaders().get("Help").toString());
			menu.setMnemonic('H');
			menuItem = new JMenuItem(bundle.getHeaders().get("About").toString());
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
			super(MainWindow.this, bundle.getHeaders().get("Login").toString(), true);
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
		    add(new JLabel(bundle.getHeaders().get("Email").toString() + ":"));
		    final JTextField emailField = new JTextField();
		    add(emailField);
		    add(new JLabel(bundle.getHeaders().get("Password").toString() + ":"));
		    final JPasswordField passwordField = new JPasswordField();
		    add(passwordField);
		    JButton submitButton = new JButton(bundle.getHeaders().get("Submit").toString());
		    submitButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					// TODO Auto-generated method stub
					try {
						User user = login(emailField.getText(), String.valueOf(passwordField.getPassword()));
						if (user != null) {
							currentUser = user;
							driverTree.buildTreeNode(" ("+ user.getEmail() +")");
							dialog.setVisible(false);	
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		    	
		    });
		    add(submitButton);
		    JButton cancelButton = new JButton(bundle.getHeaders().get("Cancel").toString());
		    cancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					// TODO Auto-generated method stub
					dialog.setVisible(false);
				}
		    	
		    });
		    add(cancelButton);
		}
		
		@SuppressWarnings("unchecked")
		private User login(String email, String password) throws Exception {
			String filter = "email=='" + email + "'&&password=='" + password + "'";
			APIResponse response = modelAPI.show(User.class, filter);
			User user = null;
			if (response.isDone()) {
				Collection<User> users = (Collection<User>) response.getMessage();
				if (users.size() == 1) {
					user = (User) users.toArray()[0];
				} 
			}
			return user;
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class MainPanel extends JPanel {
		
		private MainPanel() throws Exception {
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
			add(new JScrollPane(driverTree), BorderLayout.CENTER);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class DriverTree extends JTree {
		
		/**
		 * @throws Exception 
		 * 
		 */
		private DriverTree() throws Exception {
			buildTreeNode("");
			addTreeSelectionListener(new TreeSelectionListener() {

				@Override
				public void valueChanged(TreeSelectionEvent event) {
					// TODO Auto-generated method stub
					TreeNode node = (TreeNode) event.getPath().getLastPathComponent();
					if (node.isLeaf() && currentUser != null) {
						if (popupDialogToAddDevice() == JOptionPane.YES_OPTION) {
							// Add Device
						}
					}
				}
				
			});
		}
		
		private int popupDialogToAddDevice() {
			String message = bundle.getHeaders().get("Add-Device-Message").toString();
			String title = bundle.getHeaders().get("Add-Device-Title").toString();
			int optionType = JOptionPane.YES_NO_OPTION;
			int messageType = JOptionPane.QUESTION_MESSAGE;
			return JOptionPane.showConfirmDialog(MainWindow.this, message, title, optionType, messageType);
		}
		
		private void buildTreeNode(Object rootNodeName) throws Exception {
			rootNodeName = bundle.getHeaders().get("Driver").toString() + rootNodeName;
			setModel(new DefaultTreeModel(buildRootNode(rootNodeName)));
		}
		
		@SuppressWarnings("unchecked")
		private DefaultMutableTreeNode buildRootNode(Object rootNodeName) throws Exception {
			DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootNodeName);
			APIResponse response = modelAPI.show(Category.class, "all");
			if (response.isDone()) {
				for (Category category : (Collection<Category>) response.getMessage()) {
					rootNode.add(buildCategoryNode(category));
				}
			}
			return rootNode;
		}
		
		@SuppressWarnings("unchecked")
		private CategoryNode buildCategoryNode(Category category) throws Exception {
			CategoryNode categoryNode = new CategoryNode(category);
			APIResponse response = modelAPI.show(Driver.class, "category.id=='"+category.getId()+"'");
			if (response.isDone()) {
				for (Driver driver : (Collection<Driver>) response.getMessage()) {
					categoryNode.add(buildDriverNode(driver));
				}
			}
			return categoryNode;
		}
		
		private DriverNode buildDriverNode(Driver driver) {
			return new DriverNode(driver);
		}
		
		private class CategoryNode extends DefaultMutableTreeNode {
			
			private CategoryNode(Category category) {
				super(category.getType() + "." + category.getName() + "." +
				category.getDomain() + "." + category.getSubdomain());
			}
			
		}
		
		private class DriverNode extends DefaultMutableTreeNode {
			
			private Driver driver;
			private DriverNode(Driver driver) {
				super(driver.getAlias() + "-" + driver.getVersion());
				this.driver = driver;
			}
			@SuppressWarnings("unused")
			public Driver getDriver() { return driver; }
			
		}
		
	}

}
