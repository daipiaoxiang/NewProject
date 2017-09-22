package com.newproject.http;


/**
 * 接口
 */
public interface ApiService {
//    @POST(AppConfig.BASE_URLS + "api/other/version")
//    Call<Update> updateVersion();
//
//    /**
//     * 登录
//     *
//     * @param mobile
//     * @param password
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("auth/login")
//    Call<User> login(@Field("mobile") String mobile, @Field("password") String password);
//
//    /**
//     * 注册
//     *
//     * @param phone
//     * @param checkcode
//     * @param password
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("auth/register")
//    Call<User> register(@Field("mobile") String phone, @Field("code") String checkcode, @Field("password") String password);
//
//    /**
//     * 发送验证码
//     *
//     * @param phone
//     * @param type
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("auth/sendVerifyCode")
//    Call<Void> code(@Field("mobile") String phone, @Field("type") String type);
//
//    /**
//     * 检验验证码
//     *
//     * @param phone
//     * @param checkcode
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("auth/VerifyCode")
//    Call<Void> checkCode(@Field("mobile") String phone, @Field("code") String checkcode);
//
//    /**
//     * 修改密码
//     *
//     * @param password
//     * @param confirmPassword
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("user/modifyPassword")
//    Call<Void> forgetPassword(@Field("password") String password, @Field("newPassword") String confirmPassword);
//
//    /**
//     * 忘记密码
//     *
//     * @param password
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("auth/reset")
//    Call<Void> resetPwd(@Field("password") String password, @Field("mobile") String mobile, @Field("code") String code);
//
//    /**
//     * 修改信息
//     *
//     * @param userName
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("user/update")
//    Call<User> modifyInfo(@Field("name") String userName);
//
//    @POST("auth/logout")
//    Call<User> logout();
//
//    /**
//     * 反馈
//     *
//     * @param msg
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("other/feedback")
//    Call<String> feedback(@Field("msg") String msg);
//
//    /**
//     * 定位
//     *
//     * @return
//     */
//    @POST("other/location")
//    Call<City> locationCity();
//
//    /**
//     * 支付宝签名
//     *
//     * @param ordersn
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("pay/aliPay")
//    Call<String> alisignSignature(@Field("ordersn") String ordersn);
//
//    /**
//     * 微信
//     *
//     * @param ordersn
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("pay/wxPay")
//    Call<WXOrder> wxOrder(@Field("ordersn") String ordersn);
//    /*******************************/
//    /********* 已重构分割线 *********/
//    /*******************************/
//    /**
//     * 通过房屋信息获取预算信息
//     */
//    @FormUrlEncoded
//    @POST(AppConfig.BASE_URLS + "graphql")
//    Call<GraphqlModel<CostList>> budgets(@Field("query") String query);
//
//    /**
//     * 通过单个房间获取预算信息
//     */
//    @FormUrlEncoded
//    @POST(AppConfig.BASE_URLS + "graphql")
//    Call<GraphqlModel<List<Project>>> budget(@Field("query") String query, @Field("variables") String variables);
//
//    /**
//     * 获取默认施工项目
//     */
//    @FormUrlEncoded
//    @POST(AppConfig.BASE_URLS + "graphql")
//    Call<GraphqlModel<List<Project>>> getConstructProject(@Field("query") String query);
//
//    /**
//     * 获取工程量清单列表
//     */
//    @FormUrlEncoded
//    @POST(AppConfig.BASE_URLS + "graphql")
//    Call<GraphqlModel<List<CostList>>> getBudgets(@Field("query") String query);
//
//    /**
//     * 保存工程量清单
//     */
//    @FormUrlEncoded
//    @POST(AppConfig.BASE_URLS + "graphql")
//    Call<GraphqlModel<CostList>> saveBudget(@Field("query") String mutation, @Field("variables") String variables);
//
//    /**
//     * 修改工程量清单
//     */
//    @FormUrlEncoded
//    @POST(AppConfig.BASE_URLS + "graphql")
//    Call<GraphqlModel<CostList>> updateBudget(@Field("query") String mutation, @Field("variables") String variables);
//
//    /**
//     * 获取工程量清单详情
//     */
//    @FormUrlEncoded
//    @POST(AppConfig.BASE_URLS + "graphql")
//    Call<GraphqlModel<CostList>> getBudgetDetail(@Field("query") String query);
//
//    /**
//     * 删除工程量清单
//     */
////    http://pwww.pcw365.com:3000/graphql
//    //http:192.168.1.39:3000/graphql"
//    @FormUrlEncoded
//    @POST(AppConfig.BASE_URLS + "graphql")
//    Call<GraphqlModel<Boolean>> deleteBudget(@Field("query") String mutation);
//
//    /**
//     * 计算施工项目工程量
//     */
//    @FormUrlEncoded
//    @POST(AppConfig.BASE_URLS + "graphql")
//    Call<GraphqlModel<Double>> calculationQuantities(@Field("query") String query, @Field("variables") String variables);
//
//    @GET(AppConfig.BASE_URLS + "share/budget/{id}/xml")
//    Call<ResponseBody> download(@Path("id") String id);
//
//    /**
//     * 创建预算订单
//     *
//     * @return
//     */
//    @POST("vip/orderCreateLite")
//    Call<VIPConfirm> vipConfirm();

}