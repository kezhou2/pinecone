/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
 * @author Bill
 *
 */
@SuppressWarnings("deprecation")
public interface Images extends ImageBundle {

	@Resource("logo.png")
	AbstractImagePrototype logo();
	@Resource("user.png")
	AbstractImagePrototype user();
	@Resource("notification.png")
	AbstractImagePrototype notification();
	@Resource("email.png")
	AbstractImagePrototype email();
	@Resource("application.png")
	AbstractImagePrototype application();
	@Resource("account.png")
	AbstractImagePrototype account();
	@Resource("password.png")
	AbstractImagePrototype password();
	@Resource("store.png")
	AbstractImagePrototype store();
	@Resource("consumerIcon.png")
	AbstractImagePrototype consumerIcon();
}
