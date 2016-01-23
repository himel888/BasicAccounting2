package com.cloudsolutionltd.cslMobileAccounts.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudsolutionltd.cslMobileAccounts.Adapter.ArrayAdapterForAllTransaction;
import com.cloudsolutionltd.cslMobileAccounts.FormatDate;
import com.cloudsolutionltd.cslMobileAccounts.R;
import com.cloudsolutionltd.cslMobileAccounts.Utility;
import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionCRUD;
import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AllTransaction extends AppCompatActivity {


    final int FROM_DIALOG_ID = 1, TO_DIALOG_ID = 2;

    int year, month, day;
    String date, selectedMonth, selectedDay;
    TextView txtFromDate, txtToDate;
    Button btnSearch;
    Button btnVoucherEntry;
    public static ListView listOfAccountName;
    ArrayList<LedgerTransactionTable> voucherEntryList;
    LedgerTransactionCRUD ledgerTransactionCRUD;
    public static ArrayAdapterForAllTransaction adapter;
    LinearLayout headingLayout;

    SimpleDateFormat simpleDateFormat;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transaction);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8cce64")));

        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        calendar = Calendar.getInstance();

        ledgerTransactionCRUD = new LedgerTransactionCRUD(this);


        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        if (month < 10) {
            selectedMonth = "0" + String.valueOf(month);
        } else
            selectedMonth = String.valueOf(month);


        day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            selectedDay = "0" + String.valueOf(day);
        } else
            selectedDay = String.valueOf(day);
        date = String.valueOf(year) + "/" + selectedMonth + "/" + selectedDay;


        headingLayout = (LinearLayout) findViewById(R.id.headingLayout);
        txtFromDate = (TextView) findViewById(R.id.txtFromDate);
        txtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(FROM_DIALOG_ID);
            }
        });


        txtToDate = (TextView) findViewById(R.id.txtToDate);
        txtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TO_DIALOG_ID);
            }
        });


        listOfAccountName = (ListView) findViewById(R.id.listOfAccountName);


        btnVoucherEntry = (Button) findViewById(R.id.btnVoucherEntry);
        btnVoucherEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VoucherEntry.class);
                startActivity(intent);
                    finish();
            }
        });


        //Show default report from first date to current date of this month
        try {
            voucherEntryList = ledgerTransactionCRUD.getAllVoucherEntry(simpleDateFormat.format(calendar.getTime()).substring(0, 8) + "/01",
                    simpleDateFormat.format(calendar.getTime()));
            txtFromDate.setText(FormatDate.getDateToShow(String.valueOf(year) + "/" + selectedMonth + "/" + "01"));
            txtToDate.setText(FormatDate.getDateToShow(date));

            if (voucherEntryList.size() == 0) {
                Toast.makeText(getApplicationContext(), "No available transaction\n From " + txtFromDate.getText().toString() + " To " + txtToDate.getText().toString(), Toast.LENGTH_LONG).show();

            } else {

                adapter = new ArrayAdapterForAllTransaction(AllTransaction.this, voucherEntryList);
                listOfAccountName.setAdapter(adapter);
                Utility.setListViewHeightBasedOnChildren(listOfAccountName);
            }
        } catch (Exception e) {
            Log.e("Problem in listView", String.valueOf(e));
        }


        btnSearch = (Button) findViewById(R.id.btnSearch);
        try {
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (FormatDate.getDateToSave(txtFromDate.getText().toString()).compareTo(FormatDate.getDateToSave(txtToDate.getText().toString())) > 0) {
                        Toast.makeText(getApplicationContext(), "From date have to smaller then to date", Toast.LENGTH_SHORT).show();

                    } else
                        try {

                            voucherEntryList.clear();
                            voucherEntryList.addAll(ledgerTransactionCRUD.getAllVoucherEntry(FormatDate.getDateToSave(String.valueOf(txtFromDate.getText())),
                                    FormatDate.getDateToSave(String.valueOf(txtToDate.getText()))));
                            //listOfAccountName.setAdapter(null);


                            if (voucherEntryList.size() < 1) {
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "No available transaction\n From " + txtFromDate.getText().toString() + " To " + txtToDate.getText().toString(), Toast.LENGTH_LONG).show();

                            } else {
                                adapter.notifyDataSetChanged();
                                //adapter = new ArrayAdapterForAllTransaction(AllTransaction.this, voucherEntryList);
                                //listOfAccountName.setAdapter(adapter);
                                Utility.setListViewHeightBasedOnChildren(listOfAccountName);
                                Toast.makeText(getApplicationContext(), "Transaction report from " + String.valueOf(txtFromDate.getText()) + " To " +
                                        String.valueOf(txtToDate.getText()), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.e("Problem in listView", String.valueOf(e));
                        }

                }
            });
        } catch (Exception e) {
            Log.e("Problem in button", e.toString());

        }

        listOfAccountName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    LedgerTransactionTable transactionDetails = (LedgerTransactionTable) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), TransactionDetails.class);
                    intent.putExtra("transactionDetails", transactionDetails);
                    intent.putExtra("adapterPosition", position);
                    startActivity(intent);
                    //finish();
                } catch (Exception e) {
                    Log.e("ListView listener:", e.toString());
                }

            }
        });


    }


    @Override
    protected Dialog onCreateDialog(int id) {

        if (id == FROM_DIALOG_ID)
            return new DatePickerDialog(this, dpListenerForFromDate, year, month - 1, day);
        else if (id == TO_DIALOG_ID)
            return new DatePickerDialog(this, dpListenerForToDate, year, month, day);

        return null;
    }

    private DatePickerDialog.OnDateSetListener dpListenerForFromDate =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year_x, int monthOfYear, int dayOfMonth) {
                    year = year_x;
                    month = monthOfYear + 1;
                    day = dayOfMonth;


                    if (month < 10) {
                        selectedMonth = "0" + String.valueOf(
                                month);
                    } else
                        selectedMonth = String.valueOf(month);


                    if (day < 10) {
                        selectedDay = "0" + String.valueOf(day);
                    } else
                        selectedDay = String.valueOf(day);

                    date = String.valueOf(year) + "/" + selectedMonth + "/" + selectedDay;
                    txtFromDate.setText(FormatDate.getDateToShow(date));
                    if (FormatDate.getDateToSave(txtFromDate.getText().toString()).compareTo(FormatDate.getDateToSave(txtToDate.getText().toString())) > 0){

                        txtFromDate.setTextColor(Color.RED);
                        txtToDate.setTextColor(Color.RED);


                    }else{
                        txtFromDate.setTextColor(Color.WHITE);
                        txtToDate.setTextColor(Color.WHITE);

                    }

                }
            };

    private DatePickerDialog.OnDateSetListener dpListenerForToDate =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year_x, int monthOfYear, int dayOfMonth) {
                    year = year_x;
                    month = monthOfYear + 1;
                    day = dayOfMonth;

                    if (month < 10) {
                        selectedMonth = "0" + String.valueOf(month);
                    } else
                        selectedMonth = String.valueOf(month);


                    if (day < 10) {
                        selectedDay = "0" + String.valueOf(day);
                    } else
                        selectedDay = String.valueOf(day);

                    date = String.valueOf(year) + "/" + selectedMonth + "/" + selectedDay;
                    txtToDate.setText(FormatDate.getDateToShow(date));
                    if (FormatDate.getDateToSave(txtFromDate.getText().toString()).compareTo(FormatDate.getDateToSave(txtToDate.getText().toString())) > 0){

                        txtFromDate.setTextColor(Color.RED);
                        txtToDate.setTextColor(Color.WHITE);


                    }else{
                        txtFromDate.setTextColor(Color.WHITE);
                        txtToDate.setTextColor(Color.WHITE);

                    }

                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), ChartOfAccount.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_help){
            Intent intent = new Intent(getApplicationContext(), Help.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
