package com.example.x5webviewapplication;

import android.annotation.SuppressLint;

import android.content.Context;

import android.graphics.Canvas;

import android.graphics.Paint;

import android.util.AttributeSet;

import android.view.View;

import com.tencent.smtt.sdk.WebSettings;

import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;

import com.tencent.smtt.sdk.WebView;

import com.tencent.smtt.sdk.WebViewClient;

public class X5WebView extends WebView{
    public X5WebView(Context context, boolean b) {
        super(context, b);
    }


//    private WebViewClientclient =new WebViewClient() {
//        /**
//         * 防止加载网页时调起系统浏览器
//         */
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//         }
//    };

}
