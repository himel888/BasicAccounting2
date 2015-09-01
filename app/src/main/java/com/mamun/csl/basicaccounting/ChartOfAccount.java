package com.mamun.csl.basicaccounting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mamun.csl.basicaccounting.db.ChartOfAccountCRUD;
import com.mamun.csl.basicaccounting.db.ChartOfAccountTable;
import com.mamun.csl.basicaccounting.db.DBHandler;

import java.util.ArrayList;

public class ChartOfAccount extends AppCompatActivity {

    //Declare Listview
    ListView chartOfAccountListView;
    //Data Source
    ArrayList<ChartOfAccountTable> chartofAccountList;
    //Adapter
    CustomizedArrayAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_of_account);


        ChartOfAccountCRUD crud = new ChartOfAccountCRUD(this);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        chartOfAccountListView = (ListView) findViewById(R.id.chartOfAccountListView);
        chartofAccountList = crud.getAll_ChartOfAccount();
        if (chartofAccountList != null && chartofAccountList.size()>0){
            adapter = new CustomizedArrayAdapter(this,chartofAccountList);
            chartOfAccountListView.setAdapter(adapter);
        }

        chartOfAccountListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //ChartOfAccountInputForm form = new ChartOfAccountInputForm();
                try{
                    //form.btnSave.setVisibility(View.INVISIBLE);
                }catch(Exception e){
                    System.out.println(e);
                }


                Intent intent = new Intent(getApplicationContext(),ChartOfAccountUpdate.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),ChartOfAccountInputForm.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chart_of_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
