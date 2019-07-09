package com.ylkj.shopproject.activity.user.company;

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
 * 完善企业信息
 */
public class EditCompanyActivity extends BaseActivity {

    private EditText etCompany,etAddress,etUser,etMobile;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company);
        initView();
    }


    /**
     * 初始化
     */
    private void initView(){
        etCompany=findViewById(R.id.et_company);
        etAddress=findViewById(R.id.et_address);
        etUser=findViewById(R.id.et_user);
        etMobile=findViewById(R.id.et_mobile);

        //确定
        findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String company=etCompany.getText().toString().trim();
                final String address=etAddress.getText().toString().trim();
                final String user=etUser.getText().toString().trim();
                final String mobile=etMobile.getText().toString().trim();
                if(TextUtils.isEmpty(company)){
                    ToastUtil.showLong("请输入企业名称");
                    return;
                }
                if(TextUtils.isEmpty(address)){
                    ToastUtil.showLong("请输入企业地址");
                    return;
                }
                if(TextUtils.isEmpty(user)){
                    ToastUtil.showLong("请输入联系人名称");
                    return;
                }
                if(TextUtils.isEmpty(mobile)){
                    ToastUtil.showLong("请输入联系方式");
                    return;
                }
            }
        });
    }
}
