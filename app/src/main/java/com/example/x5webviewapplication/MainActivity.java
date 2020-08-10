package com.example.x5webviewapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private com.tencent.smtt.sdk.WebView txwebView;

    private ProgressBar progressBar;
    private TextView tvError;
    private TextView tvLoading;

    //http://ywpjjgptsecond.natapp1.cc/frontend/mobile/#/
    private String UrlWeb ="https://www.bilibili.com/";

    private Integer Position =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvError = findViewById(R.id.tvError);
        progressBar = findViewById(R.id.progress);
        tvLoading=findViewById(R.id.tv_notification);

        txwebView = findViewById(R.id.full_web_webview);
        txwebView.onResume();

        initsetting();


        txwebView.loadUrl(UrlWeb);

        txwebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return super.shouldOverrideUrlLoading(webView, s);
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                //设定加载开始的操作
                tvLoading.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(webView, s, bitmap);
            }

            //加载结束后，取消loading
            @Override
            public void onPageFinished(WebView webView, String s) {
                //设定加载结束的操作
                progressBar.setVisibility(View.GONE);
                tvLoading.setVisibility(View.GONE);
                tvError.setVisibility(View.INVISIBLE);

                Position++;
                super.onPageFinished(webView, s);
            }

            //网页报错的解决办法
            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                tvError.setVisibility(View.VISIBLE);
                webView.loadUrl(UrlWeb);
                webView.onResume();

                super.onReceivedError(webView, i, s, s1);
            }



        });

    }

    @Override
    public void onBackPressed() {
        if (Position.equals(0)){
            finish();
            System.exit(0);
        }else {
            txwebView.goBack();
            Position--;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        txwebView.reload();
        txwebView.onPause();
        try {
            txwebView.getClass().getMethod("onPause").invoke(txwebView, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        txwebView.pauseTimers();


    }

    void initsetting(){
        WebSettings webSetting =txwebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //支持通过js打开新窗口
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSetting.setDefaultTextEncodingName("utf-8");//设置编码格式
    }


}