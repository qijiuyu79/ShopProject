package com.ylkj.shopproject.eventbus;

public class EventStatus {
    //配件商城分类下的列表，选择默认等
    public static final int PJSC_SELECT_TYPE=101;

    //微信支付成功
    public static final int WX_RECHARGE=102;

    //选择城市
    public static final int GET_CITY=103;

    //收货地址设置为默认
    public static final int SET_ADDR_DEFAULT=104;

    //删除收货地址
    public static final int DEL_ADDR=105;

    //修改地址
    public static final int UPD_ADDR=106;

    //获取配件商品列表
    public static final int GET_PJ_LIST1=107;
    public static final int GET_PJ_LIST2=108;

    //取消商品收藏
    public static final int CANCLE_COLLECTION=109;

    //查询购物车数据
    public static final int GET_CAR_LIST=110;

    //修改购物车数量
    public static final int CHANGE_CAR_COUNT=112;

    //获取配件商品详情
    public static final int GET_PJ_DETAILS=113;

    //商品收藏成功
    public static final int COLLECTION_SUCCESS=114;

    //获取机床商品详情
    public static final int GET_JC_DETAILS=115;

    //选择对应的颜色
    public static final int SELECT_JC_COLOR=116;

    //领取优惠券
    public static final int TAKE_COUPON=117;

    //购物车选中了几个
    public static final int SELECT_CAR_GOODS=118;

    //取消订单成功
    public static final int CANCLE_ORDER_SUCCESS=122;

    //删除订单成功
    public static final int DELETE_ORDER_SUCCESS=123;

    //确认收货成功
    public static final int CONFIRM_GOOD_SUCCESS=124;

    //确认下单中，查询收货地址
    public static final int GET_MY_ADDRESS=125;

    //首页获取到新品数据
    public static final int MAIN_XP_SUCCESS=126;

    //获取首页人气推荐
    public static final int MAIN_RQ_SUCCESS=127;

    //获取首页热门
    public static final int MAIN_HOT_SUCCESS=128;

    //生意圈分类查询完成
    public static final int BUSINESS_TYPE=129;

    //生意圈消息获取
    public static final int BUSINESS_NEW=130;

    //首页有未读的消息
    public static final int MAIN_NEWS_SHOW=131;

    //下单页选择的优惠券
    public static final int SELECT_ORDER_COUPON=132;

    //下单成功
    public static final int ADD_ORDER_SUCCESS=133;

    //下单时选择的发票类型
    public static final int SELECT_INVOICE_TYPE=134;

    //配件详情页选中规格
    public static final int SELECT_SKUID=135;

    //打开筛选界面
    public static final int OPEN_SCREENING=136;

    //筛选选中的字符串
    public static final int SCREENING_DATA=137;

    //组装机床总金额
    public static final int JC_TOTAL_MONEY=138;

    //我的拼团详情
    public static final int MY_TUAN_DETAILS=139;

    //计算倒计时
    public static final int TUAN_DETAILS_TIME=140;

}
