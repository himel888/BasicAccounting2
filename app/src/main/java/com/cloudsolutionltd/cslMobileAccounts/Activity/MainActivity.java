package com.cloudsolutionltd.cslMobileAccounts.Activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudsolutionltd.cslMobileAccounts.PinProtection;
import com.cloudsolutionltd.cslMobileAccounts.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Locale;


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
    private Locale myLocale;
    private boolean isApplicationPinProtected;
    private String applicationPin;
    private String pinHint;
    private PinProtection pinProtection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF107EC2")));



//        item = getResources().getStringArray(R.array.drawer_list);
//        // mTitle  = mDrawerTitle = getTitle();
//
//        navigationMenu = (DrawerLayout) findViewById(R.id.navigationMenu);
//        drawerList = (ListView) findViewById(R.id.drawarList);
//
//        // Set the drawer toggle as the DrawerListener
//        navigationMenu.setDrawerListener(mDrawerToggle);
//
//        menuListAdapter = new ArrayAdapter(this,
//                android.R.layout.simple_list_item_1, item);
//
//
//        mDrawerToggle = new ActionBarDrawerToggle(this, navigationMenu, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
//
//            /** Called when drawer is closed */
//            public void onDrawerClosed(View view) {
//                //highlightSelectedCountry();
//                getSupportActionBar().setTitle(R.string.app_name);
//                supportInvalidateOptionsMenu();
//            }
//
//            /** Called when a drawer is opened */
//            public void onDrawerOpened(View drawerView) {
//                getSupportActionBar().setTitle(getResources().getString(R.string.title_select_menu));
//
//                getSupportActionBar().setDisplayShowHomeEnabled(true);
//                supportInvalidateOptionsMenu();
//            }
//        };
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        // Setting event listener for the drawer
//        navigationMenu.setDrawerListener(mDrawerToggle);
//
//        drawerList.setAdapter(menuListAdapter);
//        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//                    Intent intent = new Intent(getApplicationContext(), BasicAccounting.class);
//                    startActivity(intent);
//                    navigationMenu.closeDrawers();
//                } else if (position == 1) {
//                    Intent intent = new Intent(getApplicationContext(), VoucherEntry.class);
//                    startActivity(intent);
//                    navigationMenu.closeDrawers();
//                } else if (position == 2) {
//                    Intent intent = new Intent(getApplicationContext(), AllTransaction.class);
//                    startActivity(intent);
//                    navigationMenu.closeDrawers();
//                } else if (position == 3) {
//                    Intent intent = new Intent(getApplicationContext(), LedgerReport.class);
//                    startActivity(intent);
//                    navigationMenu.closeDrawers();
//                } else if (position == 4) {
//                    Intent intent = new Intent(getApplicationContext(), IncomeExpenseStatement.class);
//                    startActivity(intent);
//                    navigationMenu.closeDrawers();
//                } else if (position == 5) {
//                    Intent intent = new Intent(getApplicationContext(), ProfitLoss.class);
//                    startActivity(intent);
//                    navigationMenu.closeDrawers();
//                } else if (position == 6) {
//                    Intent intent = new Intent(getApplicationContext(), BalanceSheet.class);
//                    startActivity(intent);
//                    navigationMenu.closeDrawers();
//                } else if (position == 7) {
//                    Intent intent = new Intent(getApplicationContext(), ChartOfAccount.class);
//                    startActivity(intent);
//                    navigationMenu.closeDrawers();
//                } else if (position == 8) {
//                    navigationMenu.closeDrawers();
//                    finish();
//                    Intent intent = new Intent(Intent.ACTION_MAIN);
//                    intent.addCategory(Intent.CATEGORY_HOME);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
////                    navigationMenu.closeDrawers();
////                    finish();
////                    System.exit(0);
//                }
//            }
//        });


        cslWebContent = (WebView) findViewById(R.id.CSLWebContent);

    }

    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
//        Intent refresh = new Intent(this, MainActivity.class);
//        startActivity(refresh);
        //finish();
    }

    public void btnEnterOnClick(View view) {


        if (!isApplicationPinProtected){
            Intent intent = new Intent(getApplicationContext(), BasicAccounting.class);
            startActivity(intent);
        }else
            createDialog();

    }

    @Override
    protected void onResume() {
        super.onResume();

        pinProtection = new PinProtection(getApplicationContext());
        isApplicationPinProtected = pinProtection.isApplicationProtected();
        applicationPin = pinProtection.getApplicationPin();
        pinHint = pinProtection.getPinHint();

        cslWebContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

        if (isInternetOn()) {

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
            String url = "http://www.cloudsolutionltd.com/mob/home_text.html";

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

                    Log.d("HTTP response: ", error.toString());
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


    private void createDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);


        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.custom_security_dialog, null);

        //builder.setTitle("C");

        builder.setView(view);
        builder.setPositiveButton("ok", null);

        builder.setNegativeButton("cancel", null);

        TextView txtForgotPin = (TextView) view.findViewById(R.id.txt_forgot_pin);
        txtForgotPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Woopy: ", "It worked");
                forgotPinDialog();
            }
        });

        final AlertDialog myDialog = builder.create();

        myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button okButton = myDialog.getButton(DialogInterface.BUTTON_POSITIVE);


                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText eTxtUserPin = (EditText) view.findViewById(R.id.eTxtUserPin);
                        TextView txtErrorMsg = (TextView) view.findViewById(R.id.txtErrorMsg);
                        if (!eTxtUserPin.getText().toString().equals(applicationPin)) {

                            eTxtUserPin.setText("");
                            txtErrorMsg.setText("Incorrect pin!");
                            txtErrorMsg.setVisibility(View.VISIBLE);
                        }else {

                            myDialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(),BasicAccounting.class);
                            startActivity(intent);


                        }

                        Log.d("Test Input Dialog:", eTxtUserPin.getText().toString());
                    }
                });

            }
        });
        myDialog.show();
    }

    public void forgotPinDialog(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Forgot pin.");

        LinearLayout dialogLayout = new LinearLayout(this);
        dialogLayout.setOrientation(LinearLayout.VERTICAL);
        TextView txtPinHint = new TextView(this);
        txtPinHint.setText("Who is your first childhood friend?");
        txtPinHint.setPadding(10, 5, 5, 5);
        final EditText eTxtHint = new EditText(this);
        eTxtHint.setHint("my friend");
        dialogLayout.addView(txtPinHint);
        dialogLayout.addView(eTxtHint);
        builder.setView(dialogLayout);
        builder.setPositiveButton("Ok", null);
        builder.setNegativeButton("Cancel", null);


        final AlertDialog myDialog = builder.create();
        myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = myDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (eTxtHint.getText().toString().equals(pinHint)){
                            myDialog.dismiss();
                            showPin();
                        }else
                            eTxtHint.setError("Wrong answer.");
                    }
                });
            }
        });
        myDialog.show();
    }

    private void showPin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Application pin.");
        TextView txtPin = new TextView(this);
        txtPin.setPadding(5, 5, 5, 5);
        txtPin.setText(applicationPin);
        builder.setView(txtPin);
        builder.setPositiveButton("Ok", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public boolean isInternetOn() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else
            return false;
    }

//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        mDrawerToggle.syncState();
//
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        } else if (id == R.id.settings) {
//            Intent intent = new Intent(getApplicationContext(), Settings.class);
//            startActivity(intent);
//            return true;
//        } else if (id == R.id.help) {
//            Intent intent = new Intent(getApplicationContext(), Help.class);
//            startActivity(intent);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


}
