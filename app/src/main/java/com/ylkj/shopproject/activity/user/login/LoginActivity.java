package com.ylkj.shopproject.activity.user.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ylkj.shopproject.R;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.util.ToastUtil;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private EditText etMobile,etPwd;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView(){
        etMobile=findViewById(R.id.et_mobile);
        etPwd=findViewById(R.id.et_pwd);
        findViewById(R.id.tv_set_pwd).setOnClickListener(this);
        findViewById(R.id.lin_register).setOnClickListener(this);
        findViewById(R.id.tv_login).setOnClickListener(this);
        findViewById(R.id.lin_back).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String mobile,pwd;
        switch (v.getId()){
            //登录
            case R.id.tv_login:
                mobile=etMobile.getText().toString().trim();
                pwd=etPwd.getText().toString().trim();
                if(TextUtils.isEmpty(mobile)){
                    ToastUtil.showLong("请输入手机号!");
                    return;
                }
                if(mobile.length()<11){
                    ToastUtil.showLong("请输入完整的手机号!");
                    return;
                }
                if(TextUtils.isEmpty(pwd)){
                    ToastUtil.showLong("请输入密码!");
                    return;
                }
                 break;
            //忘记密码
            case R.id.tv_set_pwd:
                 setClass(ForgetPwdActivity.class);
                 break;
            case R.id.lin_register:
                 setClass(RegisterActivity.class);
                 break;
            case R.id.lin_back:
                  finish();
                 break;
        }
    }
}