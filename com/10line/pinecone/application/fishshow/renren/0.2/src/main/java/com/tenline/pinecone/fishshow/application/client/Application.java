package com.tenline.pinecone.fishshow.application.client;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.util.Theme;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.fishshow.application.client.service.RenrenApi;
import com.tenline.pinecone.fishshow.application.client.service.RenrenApiAsync;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application
    implements EntryPoint
{

	private String sessionKey = "";
	private RenrenApiAsync rapi;

	private Mainframe mainFrame;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		GXT.setDefaultTheme(Theme.BLUE, true);
		
		this.sessionKey = Window.Location.getParameter("xn_sig_session_key");
		System.out.println("ddd");
//		this.sessionKey = "2.8619d0184d498e07ae1d0881ae764f7a.3600.1313938800-251760162";
		rapi = GWT.create(RenrenApi.class);
		rapi.login(sessionKey, new AsyncCallback<Void>() {

			public void onFailure(Throwable caught) {
				MessageBox.prompt("错误", "Failure login. sessionKey="
						+ sessionKey);
			}

			public void onSuccess(Void result) {
				mainFrame.setContent(sessionKey);
//				EnvConfig.init1(sessionKey);
			}
		});

		mainFrame = new Mainframe();
		RootPanel rootPanel = RootPanel.get("MainFrame");
		rootPanel.setSize("", "");
		rootPanel.add(mainFrame);
		
		mainFrame.el().mask("读取信息中，请稍等...");
		Timer t = new Timer(){
			@Override
			public void run() {
				mainFrame.el().unmask();
			}
		};
		t.schedule(3000);

	}
}
