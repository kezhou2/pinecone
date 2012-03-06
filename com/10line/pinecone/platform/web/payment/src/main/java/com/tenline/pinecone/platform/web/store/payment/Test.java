package com.tenline.pinecone.platform.web.store.payment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String date = "2012-03-03";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			Date dat1 = sdf.parse(date);
			date = "2012-03-13";
			Date dat2 = sdf.parse(date);
			System.out.println(dat1.compareTo(dat2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
