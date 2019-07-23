package com.ylkj.shopproject.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ylkj.shopproject.R;
import com.ylkj.shopproject.activity.type.JCDetailsActivity;
import com.ylkj.shopproject.activity.type.TypeListActivity;
import com.ylkj.shopproject.activity.webview.WebViewActivity;
import com.ylkj.shopproject.adapter.main.MoChuangAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.Abvert;
import com.zxdc.utils.library.bean.MoChuang;
import com.zxdc.utils.library.bean.Type;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MeasureListView;
import java.util.ArrayList;
import java.util.List;
/**
 * 首页磨床
 */
public class MoChuangActivity extends BaseActivity {

    private Banner banner;
    private MeasureListView listView;
    private MoChuangAdapter moChuangAdapter;
    //数据集合
    private List<MoChuang.DataBean> list=new ArrayList<>();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_details);
        initView();
        //查询广告轮播图
        getAbvert();
        //查询首页磨床
        MoChuang();
    }


    /**
     * 初始化
     */
    private void initView(){
        TextView tvTitle=findViewById(R.id.tv_title);
        tvTitle.setText("磨床");
        banner=findViewById(R.id.banner);
        listView=findViewById(R.id.listView);
        //返回
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MoChuangActivity.this.finish();
            }
        });
    }



    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                //获取顶部轮播图
                case HandlerConstant.GET_ABVERT_SUCCESS:
                    final Abvert abvert= (Abvert) msg.obj;
                    if(null==abvert){
                        break;
                    }
                    if(abvert.isSussess()){
                        //设置顶部的banner轮播图
                        setBanner(abvert.getData());
                    }else{
                        ToastUtil.showLong(abvert.getDesc());
                    }
                    break;
                 //获取磨床数据
                case  HandlerConstant.GET_MOCHUANG_SUCCESS:
                      final MoChuang moChuang= (MoChuang) msg.obj;
                      if(null==moChuang){
                          break;
                      }
                      if(moChuang.isSussess()){
                          list.addAll(moChuang.getData());
                          moChuangAdapter=new MoChuangAdapter(MoChuangActivity.this,list);
                          listView.setAdapter(moChuangAdapter);
                          listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                  MoChuang.DataBean dataBean=list.get(position);
                                  Type.TypeBean typeBean=new Type.TypeBean(dataBean.getId(),dataBean.getImg(),dataBean.getName());
                                  Intent intent=new Intent(MoChuangActivity.this,TypeListActivity.class);
                                  intent.putExtra("typeBean",typeBean);
                                  startActivity(intent);
                              }
                          });
                      }else{
                          ToastUtil.showLong(moChuang.getDesc());
                      }
                      break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(activity.getString(R.string.net_error));
                    break;
            }
            return false;
        }
    });



    private void setBanner(List<Abvert.DataBean> abList){
        //设置样式，里面有很多种样式可以自己都看看效果
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
        banner.setBannerAnimation(Transformer.ZoomOutSlide);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new ABImageLoader());
        //设置图片集合
        banner.setImages(abList);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是true
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    public class ABImageLoader extends ImageLoader {
        public void displayImage(Context context, Object path, ImageView imageView) {
            Abvert.DataBean dataBean= (Abvert.DataBean) path;
            Glide.with(context).load(dataBean.getImgurl()).into(imageView);
            //图片点击跳转
            imageView.setTag(dataBean);
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Abvert.DataBean dataBean= (Abvert.DataBean) v.getTag();
                    Intent intent=new Intent();
                    switch (dataBean.getJumptype()){
                        //跳转外链
                        case 1:
                            intent.setClass(activity, WebViewActivity.class);
                            intent.putExtra("url",dataBean.getUrl());
                            intent.putExtra("type",1);
                            break;
                        //跳转机床商品
                        case 2:
                            intent.setClass(activity,JCDetailsActivity.class);
                            intent.putExtra("spuid",dataBean.getSpuid());
                            break;
                    }
                    activity.startActivity(intent);
                }
            });
        }
    }


    /**
     * 查询广告轮播图
     */
    public void getAbvert(){
        HttpMethod.getAbvert("2",handler);
    }


    /**
     * 查询首页磨床
     */
    private void MoChuang(){
        DialogUtil.showProgress(this,"数据加载中");
        HttpMethod.MoChuang(handler);
    }

}
