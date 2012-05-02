/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.Messages;

/**
 * @author Bill
 *
 */
public class MainViewport extends Viewport {

	/**
	 * 
	 */
	public MainViewport() {
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout());
		add(new HomePage(), new BorderLayoutData(LayoutRegion.CENTER));
		add(new Footer(), new BorderLayoutData(LayoutRegion.SOUTH, 50));
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class HomePage extends ContentPanel {
		
		private HomePage() {
			setIcon(((Images) Registry.get(Images.class.getName())).logo());
			setLayout(new BorderLayout());
			add(new VideoDemo(), new BorderLayoutData(LayoutRegion.CENTER));
			add(new UserForm(), new BorderLayoutData(LayoutRegion.EAST, 350));
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class VideoDemo extends ContentPanel {
		
		private VideoDemo() {
			setHeaderVisible(false);
			setUrl("http://player.youku.com/player.php/sid/XMzMzNjU3Mzgw/v.swf");
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class UserForm extends FormPanel {
		
		private UserForm() {
			setHeaderVisible(false);
			
			TextField<String> accountField = new TextField<String>();
			accountField.setAllowBlank(false);
			accountField.setEmptyText(((Messages) Registry.get(Messages.class.getName())).accountEmptyText());
			accountField.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).account());
			accountField.setRegex("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
			accountField.getMessages().setBlankText(((Messages) Registry.get(Messages.class.getName())).accountBlankWarning());
			accountField.getMessages().setRegexText(((Messages) Registry.get(Messages.class.getName())).accountRegexWarning());
			
			TextField<String> passwordField = new TextField<String>();
			passwordField.setPassword(true);
			passwordField.setAllowBlank(false);		
			passwordField.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).password());
			passwordField.getMessages().setBlankText(((Messages) Registry.get(Messages.class.getName())).passwordBlankWarning());	
			
			Button loginButton = new Button(((Messages) Registry.get(Messages.class.getName())).login());
			loginButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
					// Fire Event
				}
				
			});
			Button registerButton = new Button(((Messages) Registry.get(Messages.class.getName())).register());
			registerButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
					// Fire Event
				}
				
			});
			
			add(accountField);
			add(passwordField);
			add(new AdapterField(loginButton));
			add(new AdapterField(registerButton));
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class Footer extends ContentPanel {
		
		private Footer() {
			setHeaderVisible(false);
			setLayout(new CenterLayout());
			add(new LabelField(((Messages) Registry.get(Messages.class.getName())).copyright()));
		}
		
	}

}
