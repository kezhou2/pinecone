package com.tenline.pinecone.fishshow.application.client.resource;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

@SuppressWarnings("deprecation")
public interface IconsConfig extends ImageBundle {
	@Resource("communication12.png")
	AbstractImagePrototype communication12();

	@Resource("camera12.png")
	AbstractImagePrototype camera12();
	
	@Resource("message12.png")
	AbstractImagePrototype message12();
	
	@Resource("screenshooter12.png")
	AbstractImagePrototype screenshooter12();
	
	@Resource("mail12.png")
	AbstractImagePrototype mail12();
	
	@Resource("up_arrow.png")
	AbstractImagePrototype upArrow();
	
	@Resource("left_arrow.png")
	AbstractImagePrototype leftArrow();
	
	@Resource("right_arrow.png")
	AbstractImagePrototype rightArrow();
	
	@Resource("down_arrow.png")
	AbstractImagePrototype downArrow();
	
	@Resource("contact.jpg")
	AbstractImagePrototype contact();
	
	@Resource("fishTank.jpg")
	AbstractImagePrototype fishTank();
	
}
