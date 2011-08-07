package com.tenline.pinecone.platform.osgi.monitor.model.pointtype;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.tenline.pinecone.platform.osgi.monitor.model.Point;

public class Numbers extends Point {
	protected boolean isLoToHi() {
		return getSequence() == null || getSequence().equalsIgnoreCase(LO_TO_HI);
	}
	
	protected boolean isHiToLo() {
		return getSequence().equalsIgnoreCase(HI_TO_LO);
	}
	
	public boolean isValid(Object value) {
		String valueStr = (String) value;
		if (!valueStr.matches("[^ ]*")) {
			JOptionPane.showMessageDialog(new JPanel(), getAlias() + "不能包含空格！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else if (!valueStr.matches("[^。]*")) {
			JOptionPane.showMessageDialog(new JPanel(), getAlias() + "不能包含句号！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else if (!valueStr.matches("[0-9-.]*")) {
			JOptionPane.showMessageDialog(new JPanel(), getAlias() + "只能输入数字！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else if (!(valueStr.matches("[-]{0,1}[1-9.]*[0]{0,1}[.]{1}[0-9.]*") || valueStr.matches("[-]{0,1}[0]{1}") || valueStr
				.matches("[-]{0,1}[1-9]{1}[0-9.]*"))) {
			JOptionPane.showMessageDialog(new JPanel(), getAlias() + "数字不能以零开头！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else if (valueStr.matches("[-]{0,1}[.]{1}[0-9-.]*") || valueStr.equalsIgnoreCase("-0")
				|| valueStr.equalsIgnoreCase("-0.0")) {
			JOptionPane.showMessageDialog(new JPanel(), getAlias() + "数字格式非法！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else if (!valueStr.matches("[-]{0,1}[0-9]*[.]{0,1}[0-9]*")) {
			JOptionPane.showMessageDialog(new JPanel(), getAlias() + "负号、小数点只能输入一遍！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else {
			return true;
		}
	}
}
