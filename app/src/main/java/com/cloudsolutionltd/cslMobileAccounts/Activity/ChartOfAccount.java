package com.cloudsolutionltd.cslMobileAccounts.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cloudsolutionltd.cslMobileAccounts.Adapter.CustomizedArrayAdapter;
import com.cloudsolutionltd.cslMobileAccounts.db.ChartOfAccountCRUD;
import com.cloudsolutionltd.cslMobileAccounts.db.ChartOfAccountTable;
import com.cloudsolutionltd.cslMobileAccounts.R;

import java.util.ArrayList;

public class ChartOfAccount extends AppCompatActivity {

    //Declare ListView
    ListView chartOfAccountListView;
    ChartOfAccountCRUD crud;

    //Data Source
    ArrayList<ChartOfAccountTable> chartofAccountList;

    //Adapter
    CustomizedArrayAdapter adapter;

    ListView drawerList;
    DrawerLayout navigationMenu;
    ArrayAdapter menuListAdapter;
    ActionBarDrawerToggle mDrawerToggle;
    String[] item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_of_account);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4A97DF")));

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
                getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_chart_of_account));
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
                if (position == 0) {
                    Intent intent = new Intent(getApplicationContext(), VoucherEntry.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                    finish();
                } else if (position == 1) {
                    Intent intent = new Intent(getApplicationContext(), AllTransaction.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                    finish();
                } else if (position == 2) {
                    Intent intent = new Intent(getApplicationContext(), LedgerReport.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                    finish();
                } else if (position == 3) {
                    Intent intent = new Intent(getApplicationContext(), IncomeExpenseStatement.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                    finish();
                } else if (position == 4) {
                    Intent intent = new Intent(getApplicationContext(), ProfitLoss.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                    finish();
                } else if (position == 5) {
                    Intent intent = new Intent(getApplicationContext(), BalanceSheet.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                    finish();
                } else if (position == 6) {
                    Intent intent = new Intent(getApplicationContext(), ChartOfAccount.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                    finish();
                } else if (position == 7) {
                    Intent intent = new Intent(getApplicationContext(), Help.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                    finish();
                }
            }
        });

        crud = new ChartOfAccountCRUD(getApplicationContext());
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        //btnAdd.setBackgroundColor(Color);

        try {

            chartOfAccountListView = (ListView) findViewById(R.id.chartOfAccountListView);
            chartofAccountList = crud.getAll_ChartOfAccount();
            if (chartofAccountList != null && chartofAccountList.size() > 0) {
                adapter = new CustomizedArrayAdapter(this, chartofAccountList);
                chartOfAccountListView.setAdapter(adapter);
            }

//            chartOfAccountListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    try {
//                        ChartOfAccountTable table = (ChartOfAccountTable) parent.getItemAtPosition(position);
//
//                        Intent intent = new Intent(getApplicationContext(), ChartOfAccountUpdate.class);
//                        intent.putExtra("table", table);
//                        startActivity(intent);
//                    } catch (Exception e) {
//
//                        System.out.println(e);
//                    }
//                }
//            });
        } catch (Exception e) {
            Log.e("asdas", e.toString());
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(getApplicationContext(), ChartOfAccountInputForm.class);
               startActivity(intent);

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        crud = new ChartOfAccountCRUD(getApplicationContext());
        chartofAccountList = crud.getAll_ChartOfAccount();
        if (chartofAccountList != null && chartofAccountList.size() > 0) {
            adapter = new CustomizedArrayAdapter(this, chartofAccountList);
            chartOfAccountListView.setAdapter(adapter);
        }

        chartOfAccountListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    ChartOfAccountTable table = (ChartOfAccountTable) parent.getItemAtPosition(position);

                    Intent intent = new Intent(ChartOfAccount.this, ChartOfAccountUpdate.class);
                    intent.putExtra("table", table);
                    startActivity(intent);
                } catch (Exception e) {

                    System.out.println(e);
                }


            }
        });

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
