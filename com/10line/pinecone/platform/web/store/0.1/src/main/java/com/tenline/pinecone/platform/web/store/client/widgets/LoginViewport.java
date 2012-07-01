/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.Store;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

/**
 * @author Bill
 *
 */
public class LoginViewport extends AbstractViewport {

	/**
	 * 
	 */
	public LoginViewport() {
		super();
		// TODO Auto-generated constructor stub
		LayoutContainer centerContainer = new LayoutContainer(new FitLayout());
		centerContainer.add(new VideoDemo(), new FitData(20));
		body.add(centerContainer, new BorderLayoutData(LayoutRegion.CENTER));
		LayoutContainer eastContainer = new LayoutContainer(new VBoxLayout());
		eastContainer.add(new UserForm(), new VBoxLayoutData(new Margins(20)));
		body.add(eastContainer, new BorderLayoutData(LayoutRegion.EAST, 350));
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class VideoDemo extends ContentPanel {
		
		private VideoDemo() {
			setHeading(((Messages) Registry.get(Messages.class.getName())).videoDemo());
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
			setLabelSeparator("");
			setLabelWidth(30);
			setHeading(((Messages) Registry.get(Messages.class.getName())).login());
			
			final TextField<String> accountField = new TextField<String>();
			accountField.setAllowBlank(false);
			accountField.setEmptyText(((Messages) Registry.get(Messages.class.getName())).accountEmptyText());
			accountField.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).account());
			accountField.setRegex("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
			accountField.getMessages().setBlankText(((Messages) Registry.get(Messages.class.getName())).accountBlankWarning());
			accountField.getMessages().setRegexText(((Messages) Registry.get(Messages.class.getName())).accountRegexWarning());
			
			final TextField<String> passwordField = new TextField<String>();
			passwordField.setPassword(true);
			passwordField.setAllowBlank(false);		
			passwordField.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).password());
			passwordField.getMessages().setBlankText(((Messages) Registry.get(Messages.class.getName())).passwordBlankWarning());	
			
			final Button loginButton = new Button(((Messages) Registry.get(Messages.class.getName())).login());
			final Button registerButton = new Button(((Messages) Registry.get(Messages.class.getName())).register());
			loginButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
					if (UserForm.this.isValid()) {
						BeanModelFactory factory = Registry.get(Store.USER_MODEL_FACTORY);
						BeanModel model = factory.createModel(new User());
						model.set("email", accountField.getValue());
						model.set("password", passwordField.getValue());
						Dispatcher.get().dispatch(UserEvents.LOGIN, model);
					}
				}
				
			});
			registerButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
					Dispatcher.get().dispatch(new AppEvent(WidgetEvents.UPDATE_REGISTER_TO_PANEL));
				}
				
			});
			
			add(accountField);
			add(passwordField);
			addButton(loginButton);
			addButton(registerButton);
		}
		
	}

}
