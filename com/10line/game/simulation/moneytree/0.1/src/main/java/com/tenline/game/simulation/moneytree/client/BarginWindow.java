package com.tenline.game.simulation.moneytree.client;

import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.util.Margins;

public class BarginWindow extends Window {
	public BarginWindow() {
		setHeading("买种子");
		setSize("300", "170");
		FormLayout formLayout = new FormLayout();
		formLayout.setLabelAlign(LabelAlign.TOP);
		setLayout(formLayout);
		
		TextField txtfldNewTextfield = new TextField();
		FormData fd_txtfldNewTextfield = new FormData("100%");
		fd_txtfldNewTextfield.setMargins(new Margins(5, 5, 5, 5));
		add(txtfldNewTextfield, fd_txtfldNewTextfield);
		txtfldNewTextfield.setFieldLabel("请输入需要卖出的种子数：");
		
		TextField txtfldNewTextfield_1 = new TextField();
		FormData fd_txtfldNewTextfield_1 = new FormData("100%");
		fd_txtfldNewTextfield_1.setMargins(new Margins(5, 5, 5, 5));
		add(txtfldNewTextfield_1, fd_txtfldNewTextfield_1);
		txtfldNewTextfield_1.setFieldLabel("请输入网银账号");
		
		Button comfirmBtn = new Button("购买");
		this.addButton(comfirmBtn);
	}

}
