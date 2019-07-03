package com.ylkj.shopproject.activity.main.fault;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ylkj.shopproject.R;
import com.ylkj.shopproject.activity.main.persenter.AddFaultPersenter;
import com.ylkj.shopproject.activity.selectphoto.bean.Bimp;
import com.ylkj.shopproject.activity.selectphoto.bean.ImageItem;
import com.ylkj.shopproject.adapter.selectphoto.GridImageAdapter;
import com.ylkj.shopproject.util.PicturesUtil;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.view.MyGridView;
import com.zxdc.utils.library.view.OvalImage2Views;
import java.io.File;
/**
 * 在线报修
 */
public class AddFaultActivity extends BaseActivity implements View.OnClickListener{

    private TextView tvUserName,tvMobile,tvAddress,tvJxName,tvTime;
    private EditText etContent;
    private OvalImage2Views imgFault1,imgFault2;
    private MyGridView gridView;
    private GridImageAdapter adapter;
    private AddFaultPersenter addFaultPersenter;
    //选择的图片类型 0：品牌  1：机场照片
    private int imgType;
    /**
     * mpCropPath：名牌剪裁的图片
     * jcCropPath：机床剪裁的图片
     */
    private String mpCropPath,jcCropPath;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fault);
        addFaultPersenter=new AddFaultPersenter(this);
        initView();
    }


    /**
     * 初始化
     */
    private void initView(){
        tvUserName=findViewById(R.id.tv_userName);
        tvMobile=findViewById(R.id.tv_mobile);
        tvAddress=findViewById(R.id.tv_address);
        tvJxName=findViewById(R.id.tv_jx_name);
        tvTime=findViewById(R.id.tv_time);
        etContent=findViewById(R.id.et_content);
        imgFault1=findViewById(R.id.img_fault);
        imgFault2=findViewById(R.id.img_fault2);
        gridView=findViewById(R.id.gv_other);
        imgFault1.setOnClickListener(this);
        imgFault2.setOnClickListener(this);
        tvJxName.setOnClickListener(this);
        findViewById(R.id.tv_add_jx).setOnClickListener(this);
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);

        //清空图片集合
        Bimp.selectBitmap.clear();
        adapter = new GridImageAdapter(getApplicationContext(), Bimp.selectBitmap);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == Bimp.selectBitmap.size()) {
                    PicturesUtil.selectPhoto(AddFaultActivity.this);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){
            //选择机型名称
            case R.id.tv_jx_name:
                 intent.setClass(this,SelectJCActivity.class);
                 startActivityForResult(intent,100);
                 break;
            //添加机型名称
            case R.id.tv_add_jx:
                 intent.setClass(this,EditJCActivity.class);
                 startActivityForResult(intent,200);
                 break;
             //选择名牌照片
            case R.id.img_fault:
                 imgType=0;
                 addFaultPersenter.selectPhoto();
                 break;
            //选择机床照片
            case R.id.img_fault2:
                  imgType=1;
                  addFaultPersenter.selectPhoto();
                 break;
            //提交
            case R.id.tv_confirm:
                 break;
            case R.id.lin_back:
                 finish();
                 break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //返回拍照图片
            case AddFaultPersenter.CODE_CAMERA_REQUEST:
                if (resultCode == RESULT_OK) {
                    File tempFile = new File(AddFaultPersenter.pai);
                    if (tempFile.isFile()) {
                        addFaultPersenter.cropRawPhoto(Uri.fromFile(tempFile));
                    }
                }
                break;
            //返回相册选择图片
            case AddFaultPersenter.CODE_GALLERY_REQUEST:
                if (data != null) {
                    addFaultPersenter.cropRawPhoto(data.getData());
                }
                break;
            //返回裁剪的图片
            case AddFaultPersenter.CODE_RESULT_REQUEST:
                 final File f = new File(AddFaultPersenter.crop);
                 if(imgType==0){
                     mpCropPath=AddFaultPersenter.crop;
                     Glide.with(AddFaultActivity.this).load(Uri.fromFile(f)).into(imgFault1);
                 }else{
                     jcCropPath=AddFaultPersenter.crop;
                     Glide.with(AddFaultActivity.this).load(Uri.fromFile(f)).into(imgFault2);
                 }
                break;
            //返回拍照图片
            case PicturesUtil.CODE_CAMERA_REQUEST:
                 if (resultCode == RESULT_OK) {
                      File file = new File(PicturesUtil.pai);
                      if(file.isFile()){
                        ImageItem takePhoto = new ImageItem();
                        takePhoto.setImagePath(file.getPath());
                        Bimp.selectBitmap.add(takePhoto);
                        Bimp.imgList.add(takePhoto);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
            //返回相册选择图片
            case PicturesUtil.CODE_GALLERY_REQUEST:
                 adapter.notifyDataSetChanged();
                 break;
            default:
                break;

        }
    }

}