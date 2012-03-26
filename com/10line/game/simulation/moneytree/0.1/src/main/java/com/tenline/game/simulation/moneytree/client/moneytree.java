package com.tenline.game.simulation.moneytree.client;

import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.model.Application;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class moneytree implements EntryPoint {


  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
	  final String userId = "liugy";
		
		
//		String sig_user = Window.Location.getParameter("xn_sig_user");
		String sig_user = "251760162";
		System.out.println("!!!!!xn_sig_user:"+sig_user);
		EnvConfig.setRenrenUserId(sig_user);
//		String sig_session_key = Window.Location.getParameter("xn_sig_session_key");
		String sig_session_key = "2.d73d83172f6141e38994758bf18a6437.3600.1332774000-251760162";
		System.out.println("!!!!!xn_sig_session_key:"+sig_session_key);
		EnvConfig.setSessionKey(sig_session_key);
//		MessageBox.info("", "sig_user:"+sig_user+"\nsig_session_key:"+sig_session_key, null);
//		RootPanel.get().add(new TestViewport());
		
		EnvConfig.getRPCService().initUser(userId, new AsyncCallback<Application>(){
			
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				MessageBox.info("错误", "无法调用后台服务，初始化应用失败", null);
			}

			public void onSuccess(Application result) {
				if(result != null){
					EnvConfig.setSelfApp(result);
					EnvConfig.setSelfUser(result.getUser());
					MainContainer mainContainer = new MainContainer();
					RootPanel.get("gold_tree").add(mainContainer);
					MenuContainer menuContainer = new MenuContainer(mainContainer);
					RootPanel.get("menu").add(menuContainer);
				}else{
					MessageBox.info("错误", "无法获取应用信息，初始化应用失败", null);
				}
			}
		});
  }
}
