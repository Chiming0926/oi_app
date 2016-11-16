package com.aloha.stock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebView extends Activity{
	WebViewClient mWebViewClient = new WebViewClient(){};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		
		WebView webview = (WebView)findViewById(R.id.webview);
		webview.setWebChromeClient(new WebChromeClient());
		webview.setWebViewClient(mWebViewClient);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setLoadsImagesAutomatically(true);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setAllowFileAccess(true);
		webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webview.setHorizontalScrollBarEnabled(false);
		webview.setHorizontalScrollbarOverlay(false);
		
		webview.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
			    return true;
			}
		});
		webview.setLongClickable(false);
		
		Intent i = this.getIntent();
		String url = i.getStringExtra("URL");
		webview.loadUrl(url);
	}

}
