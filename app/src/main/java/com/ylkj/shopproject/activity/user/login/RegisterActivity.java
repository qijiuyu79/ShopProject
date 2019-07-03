package com.ylkj.shopproject.activity.user.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ylkj.shopproject.R;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.util.ToastUtil;

/**
 * 注册界面
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private EditText etMobile,etCode,etPwd;
    private TextView tvSend;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }


    /**
     * 初始化控件
     */
    private void initView(){
        etMobile=findViewById(R.id.et_mobile);
        etCode=findViewById(R.id.et_code);
        tvSend=findViewById(R.id.tv_send);
        etPwd=findViewById(R.id.et_pwd);
        tvSend.setOnClickListener(this);
        findViewById(R.id.lin_login).setOnClickListener(this);
        findViewById(R.id.tv_register).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String mobile,code,pwd;
        switch (v.getId()){
            //获取验证码
            case R.id.tv_send:
                 mobile=etMobile.getText().toString().trim();
                 if(TextUtils.isEmpty(mobile)){
                     ToastUtil.showLong("请输入手机号!");
                     return;
                 }
                 if(mobile.length()<11){
                     ToastUtil.showLong("请输入完整的手机号!");
                     return;
                 }
                 break;
            //注册
            case R.id.tv_register:
                 mobile=etMobile.getText().toString().trim();
                 code=etCode.getText().toString().trim();
                 pwd=etPwd.getText().toString().trim();
                 if(TextUtils.isEmpty(mobile)){
                     ToastUtil.showLong("请输入手机号!");
                     return;
                 }
                 if(mobile.length()<11){
                    ToastUtil.showLong("请输入完整的手机号!");
                    return;
                 }
                 if(TextUtils.isEmpty(code)){
                     ToastUtil.showLong("请输入短信验证码!");
                     return;
                 }
                 if(TextUtils.isEmpty(pwd)){
                    ToastUtil.showLong("请输入密码!");
                    return;
                 }
                 break;
           //跳到登录页面
            case R.id.lin_login:
                 setClass(LoginActivity.class);
                 break;
            case R.id.lin_back:
                 finish();
                 break;
        }
    }
}
