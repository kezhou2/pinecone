package com.tenline.game.simulation.moneytree.client;

import java.util.Date;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.tenline.game.simulation.moneytree.shared.Constant;

public class MainContainer extends LayoutContainer{
//	private IconsConfig ICONS;
	private Text text;
	private LayoutContainer imageContainer;
	private Date startDate = null;
	private DataServiceAsync service;
	private Timer harvestTimer;
	private Timer clockTimer;
	private int leftTime;
	private String treeStatus;
	private Image image;
//	private Image greenImage;
//	private Image ripedImage;
	private String baseUrl;

	public MainContainer() {
		
		this.baseUrl = GWT.getHostPageBaseURL();
		System.out.println("*****Base url:"+this.baseUrl);
		service = GWT.create(DataService.class);
		
		setSize("600", "500");
		setLayout(new BorderLayout());
		this.setStyleAttribute("background", "white");
		
		LayoutContainer layoutContainer = new LayoutContainer();
		layoutContainer.setLayout(new CenterLayout());
		
		text = new Text(Constant.STR_LOADING);
		layoutContainer.add(text);
		text.setSize("400", "20");
		text.setStyleAttribute("font-size", "20px");
		text.setStyleAttribute("text-align", "center");
		add(layoutContainer, new BorderLayoutData(LayoutRegion.NORTH, 50.0f));
		
		LayoutContainer layoutContainer_1 = new LayoutContainer();
		layoutContainer_1.setLayout(new FitLayout());
		
		LayoutContainer layoutContainer_2 = new LayoutContainer();
		layoutContainer_2.setStyleAttribute("text-align", "center");
		layoutContainer_2.setLayout(new FitLayout());
		
		imageContainer = new LayoutContainer();
		imageContainer.setLayout(new FitLayout());
		image = new Image();
		setLoadingImage();
		imageContainer.add(image);
		layoutContainer_2.add(imageContainer);
		layoutContainer_1.add(layoutContainer_2, new FitData(50, 150, 50, 150));
		add(layoutContainer_1, new BorderLayoutData(LayoutRegion.CENTER));
		
		LayoutContainer statusContainer = new LayoutContainer();
		add(statusContainer, new BorderLayoutData(LayoutRegion.SOUTH, 100.0f));
		
		treeStatus = Constant.STATUS_NOT_PLANT;
		
		refresh();
	}
	
	public void refresh(){
		service.getPlantDate(EnvConfig.getSelfApp(), new AsyncCallback<Date>(){

			public void onFailure(Throwable caught) {
				MessageBox.info("错误", "调用后台服务获取数据失败", null);
			}

			public void onSuccess(Date result) {
				System.out.println(result);
				
				startDate = result;
				
				if(harvestTimer != null){
					harvestTimer.cancel();
				}
				
				harvestTimer = new Timer(){
					@Override
					public void run() {
						System.out.println("Start refresh...");
						if(startDate != null){
							service.getGoldTreeStatus(EnvConfig.getSelfApp(), new AsyncCallback<String>(){

								public void onFailure(Throwable caught) {
									MessageBox.info("错误", "调用后台服务获取数据失败", null);
								}

								public void onSuccess(String result) {
									System.out.println("Get tree status:"+result);
									treeStatus = result;
									if(treeStatus.equals(Constant.STATUS_GROWING)){
										Date nowDate = new Date();
										leftTime = Constant.HARVEST_TIME - 
											((int) (nowDate.getTime() - startDate.getTime()));
										harvestTimer.schedule(leftTime);
										text.setText(Constant.STR_GROWING);
										setGrowingImage();
									}else if(treeStatus.equals(Constant.STATUS_HARVESTED)){
										text.setText(Constant.STR_RIPED);
										setRipedImage();
									}else if(treeStatus.equals(Constant.STATUS_NOT_PLANT)){
										text.setText(Constant.STR_NONE);
										setNoPlantImage();
									}
								}
								
							});
						}else{
							text.setText(Constant.STR_NONE);
						}
						
					}
					
				};
				harvestTimer.schedule(1000);
				
				clockTimer = new Timer(){

					@Override
					public void run() {
						if(treeStatus != null){
							if(treeStatus.equals(Constant.STATUS_GROWING)){
								int h = leftTime/1000/60/60;
								int m = (leftTime-h*1000*60*60)/1000/60;
								int s = (leftTime-h*1000*60*60-m*1000*60)/1000;
								System.out.println(""+h+"/"+m+"/"+s);
								text.setText(Constant.STR_GROWING+
										h+"小时"+m+"分"+s+"秒"
										);
							}
						}
						leftTime = leftTime - 5000; 
						clockTimer.schedule(5000);
					}
					
				};
				clockTimer.schedule(1000);
			}
		});
	}
	
	public boolean plant(){
		if(startDate != null && (treeStatus.equals(Constant.STATUS_GROWING)||
				treeStatus.equals(Constant.STATUS_HARVESTED))){

			MessageBox.info("错误", "已经种植了摇钱树，需要收获后才能重新种植", null);
			return false;
		}
		
		service.plantGoldTree(EnvConfig.getSelfApp(), new AsyncCallback<Boolean>(){

			public void onFailure(Throwable caught) {
				MessageBox.info("错误", "调用后台服务种植摇钱树失败", null);
				//TODO 调用人人网的人人都支付接口确认种植失败
			}

			public void onSuccess(Boolean result) {
				if(result){
					MessageBox.info("确认", "种植摇钱树成功", null);
					//TODO 调用人人网的人人都支付接口确认已经种植成功
					
					refresh();
				}else{
					//TODO 调用人人网的人人都支付接口确认种植失败
				}
			}
		});
		
		return true;
	}
	
	public boolean harvest(){
		if(!treeStatus.equals(Constant.STATUS_HARVESTED)){

			MessageBox.info("错误", "摇钱树还未成熟，无法收获", null);
			return false;
		}
		
		service.harvest(EnvConfig.getSelfApp(), new AsyncCallback<Integer>(){

			public void onFailure(Throwable caught) {
				MessageBox.info("错误", "调用后台服务收获摇钱树失败", null);
			}

			public void onSuccess(Integer result) {
				// TODO 更新界面
				MessageBox.info("恭喜", "您收获了"+result+"个果实。", null);
				
				refresh();
			}
			
		});
		return true;
	}
	
	private void setNoPlantImage(){
		image.setUrl(baseUrl+"/images/GreenTree.jpg");
	}
	
	private void setGrowingImage(){
		image.setUrl(baseUrl+"/images/GreenTree.jpg");
	}
	
	private void setRipedImage(){
		image.setUrl(baseUrl+"/images/RipedTree.jpg");
	}

	private void setLoadingImage(){
		image.setUrl(baseUrl+"/images/RipedTree.jpg");
	}
//	private void setNoPlantImage(){
//		image.setUrl("/images/GreenTree.jpg");
//	}
//	
//	private void setGrowingImage(){
//		image.setUrl("/images/GreenTree.jpg");
//	}
//	
//	private void setRipedImage(){
//		image.setUrl("/images/RipedTree.jpg");
//	}
//
//	private void setLoadingImage(){
//		image.setUrl("/images/RipedTree.jpg");
//	}
}
