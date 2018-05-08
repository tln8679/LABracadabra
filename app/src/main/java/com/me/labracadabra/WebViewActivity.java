package com.me.labracadabra;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author tln86
 * Created by Taylor Noble on 4/20/2018.
 * Filename: WebViewActivity.java
 * Purpose: This program file opens our website that has credits to the images and our github page
 * Revised: 4/6/2018 - made code cleaner
 * Data Structures: Nothing special
 * Reason for existence: Activity contains the webpage
 * Input: None
 * Extensions/Revisions: Possibly open in browser instead of application? But not preferred.
 */
public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    /**
     * @author tln86
     * Created by Taylor Noble on 4/20/2018.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent i = getIntent();
        String url= i.getStringExtra("url");
        webView = (WebView) findViewById(R.id.webview);
        webView.setPadding(0, 0, 0, 0);
        webView.setInitialScale(getScale());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    /**
     * @author tln86
     * Created by Taylor Noble on 4/20/2018.
     * @return the correct scaling for the web page on my phone
     */
    private int getScale(){
        int PIC_WIDTH = 1024;   // Width of my web page
        Display display = ((WindowManager)
                getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        Double val = new Double(width)/new Double(PIC_WIDTH);
        val = val * 100d;
        return val.intValue();
    }
}