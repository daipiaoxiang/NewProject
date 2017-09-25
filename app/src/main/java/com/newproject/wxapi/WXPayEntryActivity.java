package com.newproject.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.newproject.AppAction;
import com.newproject.AppConfig;
import com.newproject.MyApp;
import com.newproject.pay.wxapi.Constants;
import com.newproject.widget.Toast;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample";
	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = new View(this);
		setContentView(v);

		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		// 0 成功；-1错误；-2用户取消
		// 判断返回错误码，如果支付成功则去后台查询支付结果再展示用户实际支付结果。
		// 注意一定不能以客户端返回作为用户支付的结果，应以服务器端的接收的支付通知或查询API返回的结果为准。

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			// AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// builder.setTitle(R.string.tip);
			// builder.setMessage(getString(R.string.pay_result_callback_msg,
			// resp.errStr +";code=" + String.valueOf(resp.errCode)));
			// builder.show();
			switch (resp.errCode) {
			case AppConfig.WeiXinPayResult.success:
				Intent intent = new Intent(AppAction.PAY_SUCCESS);
				MyApp.getApp().sendBroadcast(intent);
				break;

			case AppConfig.WeiXinPayResult.fail:
				Toast.show("支付失败！");
				break;
			// 取消付款
			case AppConfig.WeiXinPayResult.cancel:
				break;

			default:
				break;
			}
			finish();
		}
	}
}