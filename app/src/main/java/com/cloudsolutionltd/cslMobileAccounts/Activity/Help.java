package com.cloudsolutionltd.cslMobileAccounts.Activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cloudsolutionltd.cslMobileAccounts.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class Help extends AppCompatActivity {

    //WebView webHelp;
    private int httpStatusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


//        webHelp = (WebView) findViewById(R.id.helpWeb);
//
//        webHelp.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return false;
//            }
//        });
//
//        if (isInternetConnected()){
//            try {
//                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                StrictMode.setThreadPolicy(policy);
//
//                HttpGet httpGet = new HttpGet("http://cloud-aps.com/mob/help_text.html");
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpResponse response = httpclient.execute(httpGet);
//                httpStatusCode = response.getStatusLine().getStatusCode();
//                Log.d("HTTP response: ", String.valueOf(response.getStatusLine().getStatusCode()));
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            if (httpStatusCode == 200){
//                webHelp.setVisibility(View.VISIBLE);
//                webHelp.loadUrl("http://cloud-aps.com/mob/help_text.html");
//            }
//        }
    }

    public boolean isInternetConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
            return true;
        }else
            return false;
    }

}
