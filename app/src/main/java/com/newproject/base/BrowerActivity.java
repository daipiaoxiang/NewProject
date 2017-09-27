package com.newproject.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.newproject.AppAction;
import com.newproject.R;
import com.newproject.widget.TDevice;
import com.newproject.widget.Toast;


@SuppressLint({"SetJavaScriptEnabled","JavascriptInterface"})
public class BrowerActivity extends BaseActivity {

	//调用传(地址,类名)

	@Override
	protected int getLayoutId() {
		return R.layout.activity_base_web;
	}

	protected WebView mWebView;
	protected ProgressBar mProgressBar;

	protected void initView() {
		mWebView = (WebView) findViewById(R.id.web_view);
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
		// 设置不调用外部浏览器
		mWebView.setWebViewClient(new WebViewClient());
		mWebView.setWebChromeClient(new WebChromeClient());
		// 是否可以缩放
		WebSettings settings = mWebView.getSettings();
		settings.setSupportZoom(false);
		// 是否显示缩放控制
		settings.setBuiltInZoomControls(false);
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		settings.setDomStorageEnabled(true);
		settings.setDatabaseEnabled(true);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			settings.setDatabasePath("/data/data/" + mWebView.getContext().getPackageName() + "/databases/");
		}
		settings.setJavaScriptEnabled(true);
		if (!TDevice.hasInternet()) {
			Toast.show("请检查你的网络连接");
		}

		mWebView.addJavascriptInterface(new MyWebJSBridge(this), "WebJSBridge");

		String url = getObject(String.class);
		if (url != null) {
			mWebView.loadUrl(url);
		}

		mRightButton.setText("关闭");
		mRightButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	public class WebViewClient extends android.webkit.WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
//			if (url != null && url.startsWith("mailto:")
//					|| url.startsWith("geo:") || url.startsWith("tel:")) {
//				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//				startActivity(intent);
//				return true;
//			}
			if (url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://")) {
				return false;
			}
			try {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(intent);
			} catch (Exception e) {

			}
//			return true;
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}

	}

	public class WebChromeClient extends android.webkit.WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress < 100) {
				if (mProgressBar.getVisibility() == View.GONE)
					mProgressBar.setVisibility(View.VISIBLE);
				// Animation animation = new Animation();
				mProgressBar.setProgress(newProgress);
			} else {
				mProgressBar.setProgress(100);
				AlphaAnimation animation = new AlphaAnimation(1, 0);
				animation.setDuration(1000);
				mProgressBar.startAnimation(animation);
				mProgressBar.setVisibility(View.GONE);
			}
			super.onProgressChanged(view, newProgress);
		}

		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			setTitle(title);
		}

	}
	@Override
	protected void onBroadcastReceive(String action, Context context, Intent intent) {
		if (AppAction.PAY_SUCCESS.equals(action)) {
			mWebView.loadUrl("javascript:paySuccess(-1)");
		}
	}

	@Override
	public void onBackPressed() {
		if (mWebView.canGoBack()) {
			mWebView.goBack();
			mRightButton.setVisibility(View.VISIBLE);
		} else {
			super.onBackPressed();
		}


	}

}
