package com.newproject.pay.alipay;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.newproject.AppConfig;
import com.newproject.MyApp;
import com.newproject.utils.NumberFormat;
import com.newproject.widget.TLog;
import com.newproject.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Alipay {

    private final static int SDK_PAY_FLAG = 1;
    //商户PID
    private static final String PARTNER = "2088911549097841";
    //商户收款账号
    private static final String SELLER = "hbz@pcw268.com";
    //商户私钥，pkcs8格式
    private static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMZuCU8/nagkrAio4NRRnB1QSKprxh4gcXbVBLoT0IolgGVFsYwQwMEhWehhxUX8qcA+vtObT6EvZRSBpNSu5TH7DJIsMpIEAG3aOm2jyMTZ9Ycsu4fKwqsTNfsbSEh7q2341WvlHEOJZMMIjsvOVo5ZihIQStsIvZY1c40eBzEjAgMBAAECgYAfoYTMZDiuiAIAc2M7lTLMnXKn7/wp60QLDFPvkZ8JlobMHfr6d+UyJC3f1E4NJQSuR5JFyxk7eYGkbjInUSXptIriFf4dlJa2XFBJDhGM6P1wtDsAK0QeFK+7JXxlRcMMVaIcim3lu09iKu6OZ6KMLdsIepHuvfi5l1JVoUWIyQJBAPf/czo1Q/TdsthSuFSd9t/0zZ6kRiX4QAm9/ai0x7xrqRugGkeY4gvh8h2wxIb50fVJ129N8tCgzQ/RRJPxqVcCQQDM1SMKZCMJRZoSk7KBza/+gMsoTki4EZ6/IFEHD5/2TZycW6oQVMsmMrkj5Xg3SdKoDM9huzodtaHEU+svtPsVAkEAhNsHusYwSqy+E990ungJen7l7LfqkIrGm7F5g3idjBZM+OyzR2nipyT7FObO0dRhoztUu18R1qxeTrv4Nh1/bwJBAKgxY4KG01YrtuYGLY/0i81ZiHhiP8gVf9rE+IPpypn/BNhHVI/wcJe/EtNs+5e6oKWpTyibCo+Ws2x41CfqXjkCQQCt9RAbKNkTpX8G4Pz2mPKTFMHU7VYhhZNoJ10rX8AHI6P/tI6D+C/qSfR6UwOxgTZeFVTp9LoQQaAZct+svOM3";

    public static abstract class PayListener {
        public abstract void result(String code, String msg);

        public int getTime() {
            return 30;
        }
    }

    public static void pay(final Activity activity, double money, String sn, String productName,
                           final PayListener listener) {
        if (TextUtils.isEmpty(sn)) {
            Toast.show("订单号不存在");
            return;
        }
//		if(AppConfig.DEBUG){
//			money = 0.01;
//		}
        String orderInfo = getOrderInfo(money, sn, productName, listener.getTime());

        // 对订单做RSA 签名
        String sign = SignUtils.sign(orderInfo, RSA_PRIVATE);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + "sign_type=\"RSA\"";
        final Handler tHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        PayResult payResult = new PayResult((String) msg.obj);
                        TLog.e("tag", payResult);
                        // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                        String resultInfo = payResult.getResult();

                        String resultStatus = payResult.getResultStatus();

                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "9000")) {
                            listener.result("9000", "支付成功");
                        } else {
                            // 判断resultStatus 为非“9000”则代表可能支付失败
                            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                                listener.result("8000", "支付结果确认中");

                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                listener.result(resultStatus, "支付失败");

                            }
                        }
                        break;
                    }
                    default:
                        break;
                }
            }

            ;
        };

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                tHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    private static String getOrderInfo(double money, String sn, String productName, int it_b_pay) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + sn + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + productName + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + MyApp.getApp().getUser().getUserId() + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + NumberFormat.formatPrice(money) + "\"";

        // 服务器异步通知页面路径
        // orderInfo += "&notify_url=" + "\"" +
        // "http://notify.msp.hk/notify.htm"
        // + "\"";
        orderInfo += "&notify_url=" + "\"" + AppConfig.ALIAPY_NOTIFY_URL + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"" + it_b_pay + "m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";
//		orderInfo += "&payuser_id=\""+MyApp.getApp().getUser().getUserId()+"\"";
        return orderInfo;
    }



















    /*private final static int SDK_PAY_FLAG = 1;
//    //商户PID
//    private static final String PARTNER = "2088911549097841";
//    //商户收款账号
//    private static final String SELLER = "hbz@pcw268.com";
//    //商户私钥，pkcs8格式
//    private static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMZuCU8/nagkrAio4NRRnB1QSKprxh4gcXbVBLoT0IolgGVFsYwQwMEhWehhxUX8qcA+vtObT6EvZRSBpNSu5TH7DJIsMpIEAG3aOm2jyMTZ9Ycsu4fKwqsTNfsbSEh7q2341WvlHEOJZMMIjsvOVo5ZihIQStsIvZY1c40eBzEjAgMBAAECgYAfoYTMZDiuiAIAc2M7lTLMnXKn7/wp60QLDFPvkZ8JlobMHfr6d+UyJC3f1E4NJQSuR5JFyxk7eYGkbjInUSXptIriFf4dlJa2XFBJDhGM6P1wtDsAK0QeFK+7JXxlRcMMVaIcim3lu09iKu6OZ6KMLdsIepHuvfi5l1JVoUWIyQJBAPf/czo1Q/TdsthSuFSd9t/0zZ6kRiX4QAm9/ai0x7xrqRugGkeY4gvh8h2wxIb50fVJ129N8tCgzQ/RRJPxqVcCQQDM1SMKZCMJRZoSk7KBza/+gMsoTki4EZ6/IFEHD5/2TZycW6oQVMsmMrkj5Xg3SdKoDM9huzodtaHEU+svtPsVAkEAhNsHusYwSqy+E990ungJen7l7LfqkIrGm7F5g3idjBZM+OyzR2nipyT7FObO0dRhoztUu18R1qxeTrv4Nh1/bwJBAKgxY4KG01YrtuYGLY/0i81ZiHhiP8gVf9rE+IPpypn/BNhHVI/wcJe/EtNs+5e6oKWpTyibCo+Ws2x41CfqXjkCQQCt9RAbKNkTpX8G4Pz2mPKTFMHU7VYhhZNoJ10rX8AHI6P/tI6D+C/qSfR6UwOxgTZeFVTp9LoQQaAZct+svOM3";

    public static abstract class PayListener {
        public abstract void result(String code, String msg);

        public int getTime() {
            return 30;
        }
    }

    public static void pay(final Activity activity, Purchase purchase,
                           final PayListener listener) {
        if (TextUtils.isEmpty(purchase.getNo())) {
            Toast.show("订单号不存在");
            return;
        }
//		if(AppConfig.DEBUG){
//			money = 0.01;
//		}
//        String orderInfo = getOrderInfo(money, sn, productName, listener.getTime());

//        // 对订单做RSA 签名
//        String sign = SignUtils.sign(orderInfo, RSA_PRIVATE);
//        try {
//            // 仅需对sign 做URL编码
//            sign = URLEncoder.encode(sign, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        final LoadingDialog dialog = new LoadingDialog(activity);
        dialog.show();
        Api.getAliSign(purchase.getNo(), new ApiBase.Data<String>() {
            @Override
            public void onData(String sign) {
                dialog.dismiss();//支付宝支付支付宝支付
                final String payInfo = "myKey=\"aec188\"&" + sign;
                final Handler tHandler = new Handler() {
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case SDK_PAY_FLAG: {
                                PayResult payResult = new PayResult((String) msg.obj);
                                TLog.e("tag", payResult);
//                                // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
//                                String resultInfo = payResult.getResult();

                                String resultStatus = payResult.getResultStatus();

                                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                                if (TextUtils.equals(resultStatus, "9000")) {
                                    listener.result("9000", "支付成功");
                                } else {
                                    // 判断resultStatus 为非“9000”则代表可能支付失败
                                    // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                                    if (TextUtils.equals(resultStatus, "8000")) {
                                        listener.result("8000", "支付结果确认中");

                                    } else {
                                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                        listener.result(resultStatus, "支付失败");

                                    }
                                }
                                break;
                            }
                            default:
                                break;
                        }
                    }

                    ;
                };

                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(activity);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(payInfo, true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        tHandler.sendMessage(msg);
                    }
                };

                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }

            @Override
            public void error(AppError error) {
                dialog.dismiss();
                listener.result("1000", error.toString());
            }

            @Override
            public Object getTag() {
                return activity;
            }
        });

    }

    *//*private static String getOrderInfo(double money, String sn, String productName, int it_b_pay) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + sn + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + productName + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + MyApp.getApp().getUser().getUserId() + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + NumberFormat.formatPrice(money) + "\"";

        // 服务器异步通知页面路径
        // orderInfo += "&notify_url=" + "\"" +
        // "http://notify.msp.hk/notify.htm"
        // + "\"";
        orderInfo += "&notify_url=" + "\"" + AppConfig.ALIAPY_NOTIFY_URL + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"" + it_b_pay + "m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";
//		orderInfo += "&payuser_id=\""+MyApp.getApp().getUser().getUserId()+"\"";
        return orderInfo;
    }*/
}
