package com.huishi.security.camera.huishi.interfaces;

import java.io.InputStream;

import com.tenline.pinecone.platform.monitor.Publisher;

public interface CameraControl {
	public void snapshot(Publisher publisher);
	
	public void videostream(Publisher publisher);
	
	public void headUp();
	
	public void headDown();
	
	public void headLeft();
	
	public void headRight();
}
