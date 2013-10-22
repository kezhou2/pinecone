/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Bill
 *
 */
public class MainActivity extends Activity {

	/**
	 * 
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		WebView view = (WebView) findViewById(R.id.portal);
		view.getSettings().setJavaScriptEnabled(true);
		view.loadUrl("http://www.pinecone.cc");
		view.setWebViewClient(new WebViewClient() {
			
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url); return true;
			}
			
		});
	}

}
