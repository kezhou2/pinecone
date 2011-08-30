package com.tenline.pinecone.fishshow.application.client;

import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.button.ButtonGroup;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;

public class CameraTabItem extends TabItem {
	
	private Button upBtn;
	private Button downBtn; 
	private Button leftBtn;
	private Button rightBtn;
	private LayoutContainer layoutContainer_2;
	private CameraPanel cameraPanel;
	
	public CameraTabItem() {
		setLayout(new BorderLayout());
		
		LayoutContainer layoutContainer = new LayoutContainer();
		layoutContainer.setLayout(new RowLayout(Orientation.VERTICAL));
		
		FieldSet fieldSet = new FieldSet();
		fieldSet.setLayout(new TableLayout(3));
		fieldSet.add(new Text());
		
		upBtn = new Button();
		upBtn.setIcon(EnvConfig.ICONS.upArrow());
		upBtn.setHeight(24);
		upBtn.setWidth(24);
		upBtn.setSize("32", "32");
		fieldSet.add(upBtn);
		fieldSet.add(new Text());
		
		leftBtn = new Button();
		leftBtn.setIcon(EnvConfig.ICONS.leftArrow());
		leftBtn.setHeight(24);
		leftBtn.setWidth(24);
		leftBtn.setSize("32", "32");
		fieldSet.add(leftBtn);
		fieldSet.add(new Text());
		
		rightBtn = new Button();
		rightBtn.setIcon(EnvConfig.ICONS.rightArrow());
		rightBtn.setHeight(24);
		rightBtn.setWidth(24);
		rightBtn.setSize("32", "32");
		fieldSet.add(rightBtn);
		fieldSet.add(new Text());
		
		downBtn = new Button();
		downBtn.setIcon(EnvConfig.ICONS.downArrow());
		downBtn.setHeight(24);
		downBtn.setWidth(24);
		downBtn.setSize("32", "32");
		fieldSet.add(downBtn);
		fieldSet.add(new Text());
		
		
		layoutContainer.add(fieldSet);
		fieldSet.setHeading("方向控制");
		fieldSet.setCollapsible(true);
		BorderLayoutData bld_layoutContainer = new BorderLayoutData(LayoutRegion.EAST,120);
		bld_layoutContainer.setMargins(new Margins(3, 3, 3, 3));
		add(layoutContainer, bld_layoutContainer);
		layoutContainer.setBorders(true);
		
		LayoutContainer layoutContainer_1 = new LayoutContainer();
		layoutContainer_1.setLayout(new RowLayout(Orientation.VERTICAL));
		
		layoutContainer_2 = new LayoutContainer();
		layoutContainer_2.setLayout(new BorderLayout());
		
		cameraPanel = new CameraPanel();
		BorderLayoutData bld_cameraPanel = new BorderLayoutData(LayoutRegion.CENTER);
		bld_cameraPanel.setMargins(new Margins(5, 5, 5, 5));
		layoutContainer_2.add(cameraPanel, bld_cameraPanel);
		layoutContainer_1.add(layoutContainer_2, new RowData(Style.DEFAULT, 300.0, new Margins()));
		layoutContainer_2.setBorders(true);
		BorderLayoutData bld_layoutContainer_1 = new BorderLayoutData(LayoutRegion.CENTER);
		bld_layoutContainer_1.setMargins(new Margins(3, 0, 3, 3));
		add(layoutContainer_1, bld_layoutContainer_1);
		layoutContainer_1.setBorders(true);
	}

}
