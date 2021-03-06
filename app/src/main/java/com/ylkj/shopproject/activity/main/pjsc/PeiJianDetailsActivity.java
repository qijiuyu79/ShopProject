package com.ylkj.shopproject.activity.main.pjsc;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.ylkj.shopproject.R;
import com.ylkj.shopproject.activity.main.persenter.PeiJianDetailsPersenter;
import com.ylkj.shopproject.adapter.main.PeiJianDetailsTypeAdapter;
import com.ylkj.shopproject.eventbus.EventBusType;
import com.ylkj.shopproject.eventbus.EventStatus;
import com.youth.banner.Banner;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.Coupon;
import com.zxdc.utils.library.bean.PJGoodDetails;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.util.Util;
import com.zxdc.utils.library.view.MeasureListView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.HashMap;
import java.util.Map;
/**
 * 配件商品详情
 */
public class PeiJianDetailsActivity extends BaseActivity implements View.OnClickListener{

    private Banner banner;
    private TextView tvName,tvDes,tvNewMoney,tvOldMoney,tvYfMoney,tvNum,btn_good,btn_details,tvDay,tvHour,tvMinutes,tvSecounds;
    private MeasureListView listView;
    private PeiJianDetailsTypeAdapter peiJianDetailsTypeAdapter;
    //MVP实例
    private PeiJianDetailsPersenter peiJianDetailsPersenter;
    //商品详情对象
    private PJGoodDetails pjGoodDetails;
    //商品数量
    private int num=1;
    //商品id
    private int spuid;
    //选中优惠券的对象
    private Coupon.DataBean coupon;
    //选中的sku规格值
    private Map<String,String> selectSku=new HashMap<>();
    //选中的规格对象
    private PJGoodDetails.skuBean skuBean;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peijian_details);
        //注册eventbus
        EventBus.getDefault().register(this);
        //实例化MVP
        peiJianDetailsPersenter=new PeiJianDetailsPersenter(this);
        initView();
        peiJianDetailsPersenter.getPJDetails(spuid);
    }

    /**
     * 初始化
     */
    private void initView(){
        spuid=getIntent().getIntExtra("spuid",0);
        banner=findViewById(R.id.banner);
        tvName=findViewById(R.id.tv_name);
        tvDes=findViewById(R.id.tv_des);
        tvDay=findViewById(R.id.tv_day);
        tvHour=findViewById(R.id.tv_hour);
        tvMinutes=findViewById(R.id.tv_minutes);
        tvSecounds=findViewById(R.id.tv_secounds);
        tvNewMoney=findViewById(R.id.tv_new_money);
        tvOldMoney=findViewById(R.id.tv_old_money);
        tvYfMoney=findViewById(R.id.tv_yf_money);
        listView=findViewById(R.id.list_type);
        tvNum=findViewById(R.id.tv_num);
        btn_good=findViewById(R.id.btn_good);
        btn_details=findViewById(R.id.btn_details);
        btn_good.setOnClickListener(this);
        btn_details.setOnClickListener(this);
        findViewById(R.id.img_remove).setOnClickListener(this);
        findViewById(R.id.img_add).setOnClickListener(this);
        findViewById(R.id.img_kf).setOnClickListener(this);
        findViewById(R.id.img_sc).setOnClickListener(this);
        findViewById(R.id.img_shopping).setOnClickListener(this);
        findViewById(R.id.tv_buy).setOnClickListener(this);
        findViewById(R.id.rel_yhq).setOnClickListener(this);
        findViewById(R.id.tv_add_shopping).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //领取优惠券
            case R.id.rel_yhq:
                 if(!saveSelectSkuid()){
                     ToastUtil.showLong("请选择完整的商品类型！");
                 }
                peiJianDetailsPersenter.couponJson(pjGoodDetails.getData(),skuBean.getStock(),skuBean.getPrice());
                 break;
            //减数量
            case R.id.img_remove:
                 if(num==1){
                     return;
                 }
                 --num;
                 tvNum.setText(String.valueOf(num));
                 pjGoodDetails.getData().setCount(num);
                 break;
            //加数量
            case R.id.img_add:
                 if(!saveSelectSkuid()){
                     ToastUtil.showLong("请选择完整的商品类型！");
                     return;
                 }
                 if(num==skuBean.getStock()){
                     return;
                 }
                 num++;
                 tvNum.setText(String.valueOf(num));
                 pjGoodDetails.getData().setCount(num);
                 break;
            //客服
            case R.id.img_kf:
                 break;
            //收藏
            case R.id.img_sc:
                 peiJianDetailsPersenter.isCollec(pjGoodDetails);
                 break;
            //加入购物车
            case R.id.tv_add_shopping:
            case R.id.img_shopping:
                 if(!saveSelectSkuid()){
                     ToastUtil.showLong("请选择完整的商品类型！");
                 }
                 peiJianDetailsPersenter.addCar(String.valueOf(skuBean.getId()),num);
                 break;
            //立即购买
            case R.id.tv_buy:
                 if(!saveSelectSkuid()){
                     ToastUtil.showLong("请选择完整的商品类型！");
                    return;
                 }
                 Intent intent=new Intent(this,ConfirmXDActivity.class);
                 intent.putExtra("goodBean",pjGoodDetails.getData());
                 intent.putExtra("coupon",coupon);
                 intent.putExtra("type",0);
                 startActivity(intent);
                 break;
            case R.id.btn_good:
                  showDetails(1);
                  break;
            case R.id.btn_details:
                  showDetails(2);
                  break;
            case R.id.lin_back:
                  finish();
                  break;
        }
    }


    /**
     * 展示数据
     */
    private void showUIData(){
        final PJGoodDetails.goodBean goodBean=pjGoodDetails.getData();
        if(null==goodBean){
            return;
        }
        tvName.setText(goodBean.getName());
        tvDes.setText(Html.fromHtml(goodBean.getDiscription()));
        tvNewMoney.setText("平台售价："+ Util.setDouble(goodBean.getPrice(),2));
        tvOldMoney.setText(Util.setDouble(goodBean.getOldprice(),2));
        tvOldMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvYfMoney.setText("¥"+goodBean.getFreigth());

        //展示banner轮播图
        peiJianDetailsPersenter.setBanner(banner,goodBean.getSpuImgList());

        //展示商品类型
        peiJianDetailsTypeAdapter=new PeiJianDetailsTypeAdapter(this,goodBean.getProSpecsList());
        listView.setAdapter(peiJianDetailsTypeAdapter);

        //判断是否是团购
        judgeTG();
    }


    /**
     * 刷新规格
     */
    public void updateType(String skuId){
        PJGoodDetails.goodBean goodBean=pjGoodDetails.getData();
        for (int i=0;i<goodBean.getProSpecsList().size();i++){
            for (int j=0;j<goodBean.getProSpecsList().get(i).getProSpecsVals().size();j++){
                if(goodBean.getProSpecsList().get(i).getProSpecsVals().get(j).getIscheck()==1){
                    boolean isRepeat=Util.isRepeat(skuId, goodBean.getProSpecsList().get(i).getProSpecsVals().get(j).getSkuid());
                    if(!isRepeat){
                        goodBean.getProSpecsList().get(i).getProSpecsVals().get(j).setIscheck(0);
                    }
                }
            }
        }
        peiJianDetailsTypeAdapter.notifyDataSetChanged();
    }


    /**
     * 保存已选中的规格
     */
    private boolean saveSelectSkuid(){
        selectSku.clear();
        String skuid=null;
        //存储已选中的规格值
        final PJGoodDetails.goodBean goodBean=pjGoodDetails.getData();
        for (int i=0;i<goodBean.getProSpecsList().size();i++){
                for (int j=0;j<goodBean.getProSpecsList().get(i).getProSpecsVals().size();j++){
                        if(goodBean.getProSpecsList().get(i).getProSpecsVals().get(j).getIscheck()==1){
                            skuid=goodBean.getProSpecsList().get(i).getProSpecsVals().get(j).getSkuid();
                            selectSku.put(skuid,skuid);
                            continue;
                        }
                }
        }
        if(selectSku.size()<pjGoodDetails.getData().getProSpecsList().size()){
            return false;
        }

        //遍历map获取最终规格的skuId
        for (Map.Entry<String, String> entry : selectSku.entrySet()) {
              if(entry.getValue().indexOf(",")==-1){
                  skuid=entry.getValue();
                  break;
              }
        }

        //获取选中的规格对象
        for (int i=0;i<goodBean.getSkuList().size();i++){
            if(Integer.parseInt(skuid)==goodBean.getSkuList().get(i).getId()){
                skuBean=goodBean.getSkuList().get(i);
                //给全局商品对象赋值上最终规格的id和价格
                pjGoodDetails.getData().setPrice(skuBean.getPrice());
                pjGoodDetails.getData().setSkuid(skuBean.getId());
                break;
            }
        }
        return true;
    }


    /**\
     * 判断是否是团购
     */
    private void judgeTG(){
        if(!saveSelectSkuid()){
            return;
        }
        boolean b=peiJianDetailsPersenter.isTG(skuBean);
        if(b){
            findViewById(R.id.lin_time).setVisibility(View.VISIBLE);
            peiJianDetailsPersenter.startCountdown(skuBean);
        }else{
            findViewById(R.id.lin_time).setVisibility(View.GONE);
        }
    }


    /**
     * 切换商品和详情
     */
    private void showDetails(int type){
        if(type==1){
            btn_good.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_rounded));
            btn_good.setTextColor(getResources().getColor(android.R.color.white));
            btn_details.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_rounded));
            btn_details.setTextColor(getResources().getColor(R.color.color_33333));
            findViewById(R.id.scro_details).setVisibility(View.GONE);
        }else{
            btn_good.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_rounded2));
            btn_good.setTextColor(getResources().getColor(R.color.color_33333));
            btn_details.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_rounded2));
            btn_details.setTextColor(getResources().getColor(android.R.color.white));
            findViewById(R.id.scro_details).setVisibility(View.VISIBLE);
        }
        if(null!=pjGoodDetails){
            final PJGoodDetails.goodBean goodBean=pjGoodDetails.getData();
            if(null==goodBean){
                return;
            }
            TextView tvDetails=findViewById(R.id.tv_details);
            tvDetails.setText(Html.fromHtml(goodBean.getDiscription()));
        }
    }


    /**
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType){
        switch (eventBusType.getStatus()){
            //获取配件商品详情
            case EventStatus.GET_PJ_DETAILS:
                  pjGoodDetails= (PJGoodDetails) eventBusType.getObject();
                  showUIData();
                  break;
           //商品收藏成功
            case EventStatus.COLLECTION_SUCCESS:
                  pjGoodDetails.getData().setIscollect(1);
                  break;
            //下单页选中的优惠券
            case EventStatus.SELECT_ORDER_COUPON:
                coupon= (Coupon.DataBean) eventBusType.getObject();
                if(null!=coupon){
                    TextView tvYhq=findViewById(R.id.tv_yhq);
                    tvYhq.setText("满"+coupon.getFullreductionvalue()+"减"+coupon.getFacevalue());
                }
                break;
            //选中的规格
            case EventStatus.SELECT_SKUID:
                 int[] postion= (int[]) eventBusType.getObject();
                 if(null!=postion && postion.length==2){
                     pjGoodDetails.getData().getProSpecsList().get(postion[0]).getProSpecsVals().get(postion[1]).setIscheck(1);
                     //刷新界面规格列表
                     updateType(pjGoodDetails.getData().getProSpecsList().get(postion[0]).getProSpecsVals().get(postion[1]).getSkuid());
                     //判断是否是团购
                     judgeTG();
                 }
                 break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
