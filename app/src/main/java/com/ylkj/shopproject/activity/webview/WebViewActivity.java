package com.ylkj.shopproject.activity.webview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.ylkj.shopproject.R;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.http.HttpConstant;
import com.zxdc.utils.library.util.LogUtils;

public class WebViewActivity extends BaseActivity {

    private WebView webshow;
    private String url;
    private int type;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        initView();
    }

    @SuppressLint("JavascriptInterface")
    private void initView(){
        type=getIntent().getIntExtra("type",0);
        url=getIntent().getStringExtra("url");
        webshow =findViewById(R.id.webview);
        webshow.getSettings().setJavaScriptEnabled(true);
        webshow.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webshow.getSettings().setSupportMultipleWindows(true);
        webshow.getSettings().setBuiltInZoomControls(true);
        webshow.setWebViewClient(new WebViewClient());
        webshow.setWebChromeClient(new WebChromeClient());

        switch (type){
            case 1:
                 webshow.loadUrl(url);
                 break;
           //平台消息详情
            case 2:
                  webshow.loadUrl(HttpConstant.IP+"api/app/html/messageinfo?id="+url);
                  break;
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webshow.canGoBack()) {
            webshow.goBack();
        }
        return super.onKeyDown(keyCode, event);
    }
}
