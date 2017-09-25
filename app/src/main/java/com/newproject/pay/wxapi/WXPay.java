package com.newproject.pay.wxapi;


import android.app.Activity;
import android.util.Log;
import android.util.Xml;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WXPay {
	
    //appid
    //请同时修改  androidmanifest.xml里面，.PayActivityd里的属性<data android:scheme="wxb4ba3c02aa476ea1"/>为新设置的appid
  public static final String APP_ID = "wx3e3c678d3fdc59d9";


//AppSecret：7771dead5617f087e70d3c4f0d9e1572
//  测试版应用签名：509F84E8A6509F70299853E2798F0F29
//  应用签名：fa5e227d1ac390bf028432de27bf968e（修改https://open.weixin.qq.com/cgi-bin/appdetail?t=manage/detail&type=app&lang=zh_CN&token=cd47e12053cfd382a5fcf88d6c14ea8454f6d82a&appid=wxa70cbbfc5d3c28e4）
  //  包名：com.aec188.pcw_store
  
  //商户号
   public static final String MCH_ID = "1381445802";


//  API密钥，在商户平台设置
//    public static final  String API_KEY="412fde4e9c2e2bb619514ecea142e449";
    public static final String API_KEY="Aec188sqlyouAec188sqlyouAec188sq";

//	public static void pay(final Activity aty, final String orderNo, final ApiBase.Data<JSONObject> l) {
		
//		packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
//		packageParams.add(new BasicNameValuePair("body", "weixin"));
//		packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
//		packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
//		packageParams.add(new BasicNameValuePair("notify_url", AppConfig.TONGLIAN_NOTIFY_URL));//TODO 需要修改
//		packageParams.add(new BasicNameValuePair("out_trade_no",genOutTradNo()));
//		packageParams.add(new BasicNameValuePair("spbill_create_ip",TDevice.getIpAddress()));
//		packageParams.add(new BasicNameValuePair("total_fee", "" + totalFee));
//		packageParams.add(new BasicNameValuePair("trade_type", "APP"));
//
//		Api.payByWeixin(orderNo, new ApiBase.Data<JSONObject>() {
//
//			@Override
//			public void onData(JSONObject json) {
//				try {
//
//					TLog.e("prepare_id"+json.getString("prepay_id"));
//					PayReq req = genPayReq(json.getString("prepay_id"));//result.get("prepay_id")
//					sendPayReq(aty,req);
//					l.onData(json);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					error(AppError.JSONFormatError);
//				}
//			}
//
//			@Override
//			public void error(AppError error) {
//				l.error(error);
//			}
//
//			@Override
//			public Object getTag() {
//				// TODO Auto-generated method stub
//				return l.getTag();
//			}
//		});
		
		
//		Api.getCheckCode("", 0, new Success() {
//			
//			@Override
//			public void success(JSONObject json) {
//				l.success(json);
//				// TODO Auto-generated method stub
//				PayReq req = genPayReq("sdfsd");//result.get("prepay_id")
//				sendPayReq(aty,req);
//				
//			}
//			
//			@Override
//			public void error(AppError error) {
//				l.error(error);
//				
//			}
//		});
//	}
	
	/**
	 生成签名
	 */

	private static String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);
		

		String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion",packageSign);
		return packageSign;
	}
	private static String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

//        this.sb.append("sign str\n"+sb.toString()+"\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion",appSign);
		return appSign;
	}
	private static String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<"+params.get(i).getName()+">");


			sb.append(params.get(i).getValue());
			sb.append("</"+params.get(i).getName()+">");
		}
		sb.append("</xml>");

		Log.e("orion",sb.toString());
		return sb.toString();
	}

//	private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String,String>> {
//
//		private Activity aty;
//		private ApiBase.Success l;
//		public GetPrepayIdTask(Activity aty,ApiBase.Success l) {
//			this.aty = aty;
//			this.l = l;
//		}
//		@Override
//		protected void onPreExecute() {
//		}
//
//		@Override
//		protected void onPostExecute(Map<String,String> result) {
////			sb.append("prepay_id\n"+result.get("prepay_id")+"\n\n");
////			show.setText(sb.toString());
//
////			resultunifiedorder=result;
//			PayReq req = genPayReq(result.get("prepay_id"));
//			sendPayReq(aty,req);
////			Log.e("onpost prepay_id",result.get("prepay_id"));
//		}
//
//		@Override
//		protected void onCancelled() {
//			super.onCancelled();
//		}
//
//		@Override
//		protected Map<String,String>  doInBackground(Void... params) {
//
//			String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
//			String entity = genProductArgs();
//
//			Log.e("orion",entity);
//
//			byte[] buf = Util.httpPost(url, entity);
//
//			String content = new String(buf);
//			Log.e("orion", content);
//			Map<String,String> xml=decodeXml(content);
//
//			return xml;
//		}
//	}



	public static Map<String,String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName=parser.getName();
				switch (event) {
					case XmlPullParser.START_DOCUMENT:

						break;
					case XmlPullParser.START_TAG:

						if("xml".equals(nodeName)==false){
							//实例化student对象
							xml.put(nodeName,parser.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			Log.e("orion",e.toString());
		}
		return null;

	}


	private static String genNonceStr() {
		Random random = new Random();
		
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	
	private static long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}
	


	private static String genOutTradNo() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	

   //
//	private static String genProductArgs(long totalFee) {
//		StringBuffer xml = new StringBuffer();
//
//		try {
//			String	nonceStr = genNonceStr();
//
//
//			xml.append("</xml>");
//            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
//			packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
//			packageParams.add(new BasicNameValuePair("body", "weixin"));
//			packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
//			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
//			packageParams.add(new BasicNameValuePair("notify_url", AppConfig.TONGLIAN_NOTIFY_URL));//TODO 需要修改
//			packageParams.add(new BasicNameValuePair("out_trade_no",genOutTradNo()));
//			packageParams.add(new BasicNameValuePair("spbill_create_ip",TDevice.getIpAddress()));
//			packageParams.add(new BasicNameValuePair("total_fee", "" + totalFee));
//			packageParams.add(new BasicNameValuePair("trade_type", "APP"));
//
//
//			String sign = genPackageSign(packageParams);
//			packageParams.add(new BasicNameValuePair("sign", sign));
//
//
//		   String xmlstring =toXml(packageParams);
//
//			return xmlstring;
//
//		} catch (Exception e) {
//			Log.e("Wechat", "genProductArgs fail, ex = " + e.getMessage());
//			return null;
//		}
//		
//
//	}
	private static PayReq genPayReq(String prepay_id) {
		PayReq req = new PayReq();
		req.appId = Constants.APP_ID;
		req.partnerId = Constants.MCH_ID;
		req.prepayId = prepay_id;
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());


		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new NameValuePair("appid", req.appId));
		signParams.add(new NameValuePair("noncestr", req.nonceStr));
		signParams.add(new NameValuePair("package", req.packageValue));
		signParams.add(new NameValuePair("partnerid", req.partnerId));
		signParams.add(new NameValuePair("prepayid", req.prepayId));
		signParams.add(new NameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		return req;
	}
	private static void sendPayReq(Activity activity, PayReq req) {
		final IWXAPI msgApi = WXAPIFactory.createWXAPI(activity, null);
		msgApi.registerApp(Constants.APP_ID);
		msgApi.sendReq(req);
	}

	static class NameValuePair {
		String name;
		String value;
		public NameValuePair(String name, String value) {
			this.name = name;
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		
		
	}
	
	


}
