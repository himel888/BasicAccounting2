package com.cloudsolutionltd.cslMobileAccounts.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudsolutionltd.cslMobileAccounts.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {


    WebView cslWebContent;
    private boolean webviewSuccess;
    protected int httpStatusCode;
    ListView drawerList;
    DrawerLayout navigationMenu;
    ArrayAdapter menuListAdapter;
    ActionBarDrawerToggle mDrawerToggle;
    String[] item;
    AdView adView;
    AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF107EC2")));

        item = getResources().getStringArray(R.array.drawer_list);
       // mTitle  = mDrawerTitle = getTitle();

        navigationMenu = (DrawerLayout) findViewById(R.id.navigationMenu);
        drawerList = (ListView) findViewById(R.id.drawarList);

        // Set the drawer toggle as the DrawerListener
        navigationMenu.setDrawerListener(mDrawerToggle);

        menuListAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,item);

        mDrawerToggle = new ActionBarDrawerToggle(this, navigationMenu, R.drawable.ic_drawer , R.string.drawer_open,R.string.drawer_close){

            /** Called when drawer is closed */
            public void onDrawerClosed(View view) {
                //highlightSelectedCountry();
                getSupportActionBar().setTitle(R.string.app_name);
                supportInvalidateOptionsMenu();
            }

            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Select Menu");

                getSupportActionBar().setDisplayShowHomeEnabled(true);
                supportInvalidateOptionsMenu();
            }
        };
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Setting event listener for the drawer
        navigationMenu.setDrawerListener(mDrawerToggle);

        drawerList.setAdapter(menuListAdapter);
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    Intent intent = new Intent(getApplicationContext(), BasicAccounting.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                } else if (position == 1) {
                    Intent intent = new Intent(getApplicationContext(), VoucherEntry.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                } else if (position == 2) {
                    Intent intent = new Intent(getApplicationContext(), AllTransaction.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                } else if (position == 3) {
                    Intent intent = new Intent(getApplicationContext(), LedgerReport.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                } else if (position == 4) {
                    Intent intent = new Intent(getApplicationContext(), IncomeExpenseStatement.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                } else if (position == 5) {
                    Intent intent = new Intent(getApplicationContext(), ProfitLoss.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                } else if (position == 6) {
                    Intent intent = new Intent(getApplicationContext(), BalanceSheet.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                } else if (position == 7) {
                    Intent intent = new Intent(getApplicationContext(), ChartOfAccount.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                } else if (position == 8) {
                    Intent intent = new Intent(getApplicationContext(), Help.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                }else if (position == 9) {
                    navigationMenu.closeDrawers();
                    finish();
                    System.exit(0);
                }
            }
        });


        cslWebContent = (WebView) findViewById(R.id.CSLWebContent);

    }

    public void btnEnterOnClick(View view){
        Intent intent = new Intent(getApplicationContext(), BasicAccounting.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();


        cslWebContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

        if (isInternetOn()){

//                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                StrictMode.setThreadPolicy(policy);
//
//                URL url = new URL("http://cloud-aps.com/mob/home_text.html");
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");


            adView = (AdView) findViewById(R.id.adView);
            adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            adView.setVisibility(View.VISIBLE);

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://www.cloudsolutionltd.com/mob/home_text.html";

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            cslWebContent.setVisibility(View.VISIBLE);
                            cslWebContent.loadUrl("http://www.cloudsolutionltd.com/mob/home_text.html");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("HTTP response: ",error.toString());
                }
            });
// Add the request to the RequestQueue.
            queue.add(stringRequest);


//                HttpGet httpGet = new HttpGet("http://cloud-aps.com/mob/home_text.html");
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpResponse response = httpclient.execute(httpGet);
//                httpStatusCode = response.getStatusLine().getStatusCode();
            //httpStatusCode = conn.getResponseCode();
//                Log.d("HTTP response: ", String.valueOf(response.getStatusLine().getStatusCode()));
            //Log.d("HTTP response: ",String.valueOf(httpStatusCode));

            //            if (httpStatusCode == 200){
//                cslWebContent.setVisibility(View.VISIBLE);
//                cslWebContent.loadUrl("http://cloud-aps.com/mob/home_text.html");
//            }
        }

    }




    public boolean isInternetOn(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else
            return false;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
