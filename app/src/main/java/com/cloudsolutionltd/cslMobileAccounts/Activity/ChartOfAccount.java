package com.cloudsolutionltd.cslMobileAccounts.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_of_account);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4A97DF")));

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
}
