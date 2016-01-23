package com.cloudsolutionltd.cslMobileAccounts.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudsolutionltd.cslMobileAccounts.Adapter.AdapterForIncomeExpenseStatement;
import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionCRUD;
import com.cloudsolutionltd.cslMobileAccounts.FormatDate;
import com.cloudsolutionltd.cslMobileAccounts.R;
import com.cloudsolutionltd.cslMobileAccounts.Utility;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BalanceSheet extends AppCompatActivity {

    final int FROM_DIALOG_ID = 1, TO_DIALOG_ID = 2;

    //ActivityContent
    TextView txtFromDate, txtToDate, txtHeading, txtTotalAsset, txtTotalLiabilities, txtProfitOrLoss, txtAssetListHeading, txtLiabilityListHeading;
    Button btnSearch;
    TextView txtProfitOrLossHeading;
    ListView listAsset, listLiability;

    //Variable for activity work
    int year, month, day;
    Double totalIncome = 0.0, totalExpense = 0.0, profitOrLoss = 0.0, totalAsset = 0.0, totalLiabilities = 0.0;
    String date, selectedMonth, selectedDay, accountType = null;
    NumberFormat numberFormat;
    LinearLayout totalAssetLine, totalLiabilityLine, totalProfitOrLossLine;
    TextView userHint, txtSummaryHeading;

    ArrayList<String[]> statement = null;
    AdapterForIncomeExpenseStatement adapterForAsset, adapterForLiability;
    LedgerTransactionCRUD ledgerTransactionCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_sheet);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#078f50")));

        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(2);

        ledgerTransactionCRUD = new LedgerTransactionCRUD(this);
        initialize();

        Calendar calendar = Calendar.getInstance();
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

        txtFromDate.setText(FormatDate.getDateToShow(String.valueOf(year) + "/" + selectedMonth + "/" + "01"));
        txtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(FROM_DIALOG_ID);
            }
        });


        txtToDate.setText(FormatDate.getDateToShow(String.valueOf(year) + "/" + selectedMonth + "/" + selectedDay));
        txtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TO_DIALOG_ID);
            }
        });



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userHint.setVisibility(View.GONE);

                try {
                    statement = ledgerTransactionCRUD.getAccountStatement(FormatDate.getDateToSave(txtFromDate.getText().toString()),
                            FormatDate.getDateToSave(txtToDate.getText().toString()), "Asset");
                    if (statement != null) {

                        txtAssetListHeading.setVisibility(View.VISIBLE);
                        listAsset.setVisibility(View.VISIBLE);
                        totalAssetLine.setVisibility(View.VISIBLE);
                        totalLiabilityLine.setVisibility(View.VISIBLE);
                        totalProfitOrLossLine.setVisibility(View.VISIBLE);
                        txtSummaryHeading.setVisibility(View.VISIBLE);

                        for (int i = 0; i < statement.size(); i++)
                            totalAsset += Double.valueOf(statement.get(i)[1]);

                        txtTotalAsset.setText(numberFormat.format(totalAsset));


                        adapterForAsset = new AdapterForIncomeExpenseStatement(BalanceSheet.this, statement);
                        listAsset.setAdapter(adapterForAsset);
                        Utility.setListViewHeightBasedOnChildren(listAsset);
                        //txtTotalAmount.setText("Total amount: " + String.valueOf(totalAmount));
                    }
                    else
                        Toast.makeText(getApplicationContext(),"No such data found", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("ListView Error: ", e.toString());
                }

                try {
                    statement = ledgerTransactionCRUD.getAccountStatement(FormatDate.getDateToSave(txtFromDate.getText().toString()),
                            FormatDate.getDateToSave(txtToDate.getText().toString()), "Liability");
                    if (statement != null) {

                        txtLiabilityListHeading.setVisibility(View.VISIBLE);
                        listLiability.setVisibility(View.VISIBLE);
                        for (int i = 0; i < statement.size(); i++)
                            totalLiabilities += Double.valueOf(statement.get(i)[1]);

                        txtTotalLiabilities.setText(numberFormat.format(totalLiabilities));


                        adapterForLiability = new AdapterForIncomeExpenseStatement(BalanceSheet.this, statement);
                        listLiability.setAdapter(adapterForLiability);
                        Utility.setListViewHeightBasedOnChildren(listLiability);
                        //txtTotalAmount.setText("Total amount: " + String.valueOf(totalAmount));
                    }
                    else
                        Toast.makeText(getApplicationContext(),"No such data found", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("ListView Error: ", e.toString());
                }



                try {
                    statement = ledgerTransactionCRUD.getAccountStatement(FormatDate.getDateToSave(txtFromDate.getText().toString()),
                            FormatDate.getDateToSave(txtToDate.getText().toString()), "Expense");
                    if (statement != null) {
                        for (int i = 0; i < statement.size(); i++)
                            totalExpense += Double.valueOf(statement.get(i)[1]);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "No such data found", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("ListView Error: ", e.toString());
                }


                try {
                    statement = ledgerTransactionCRUD.getAccountStatement(FormatDate.getDateToSave(txtFromDate.getText().toString()),
                            FormatDate.getDateToSave(txtToDate.getText().toString()), "Income");
                    if (statement != null) {
                        for (int i = 0; i < statement.size(); i++)
                            totalIncome += Double.valueOf(statement.get(i)[1]);

                        totalIncome *= -1;
                    }
                    else
                        Toast.makeText(getApplicationContext(),"No such data found", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("ListView Error: ", e.toString());
                }

                profitOrLoss = totalIncome - totalExpense;

                if(profitOrLoss < 0){
                    txtProfitOrLossHeading.setText("Loss: ");
                    txtProfitOrLoss.setText(numberFormat.format(profitOrLoss * -1));

                }
                else{
                    txtProfitOrLossHeading.setText("Profit: ");
                    txtProfitOrLoss.setText(numberFormat.format(profitOrLoss));

                }

                profitOrLoss = 0.0;
                totalExpense = 0.0;
                totalIncome = 0.0;
                totalAsset = 0.0;
                totalLiabilities = 0.0;

            }
        });
    }


    private void initialize(){
        txtFromDate = (TextView) findViewById(R.id.txtFromDate);
        txtToDate = (TextView) findViewById(R.id.txtToDate);
        txtTotalAsset = (TextView) findViewById(R.id.txtTotalAsset);
        txtTotalLiabilities = (TextView) findViewById(R.id.txtTotalLiabilities);
        txtProfitOrLoss = (TextView) findViewById(R.id.txtProfitOrLoss);
        txtAssetListHeading = (TextView) findViewById(R.id.txtAssetListHeading);
        txtLiabilityListHeading = (TextView) findViewById(R.id.txtLiabilityListHeading);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        listAsset = (ListView) findViewById(R.id.listAsset);
        listLiability = (ListView) findViewById(R.id.listLiability);
        txtProfitOrLossHeading = (TextView) findViewById(R.id.txtProfitOrLossHeading);
        totalAssetLine = (LinearLayout) findViewById(R.id.totalAssetLine);
        totalLiabilityLine = (LinearLayout) findViewById(R.id.totalLiabilityLine);
        totalProfitOrLossLine = (LinearLayout) findViewById(R.id.profitOrLossLine);
        userHint = (TextView) findViewById(R.id.txtUserHint);
        txtSummaryHeading = (TextView) findViewById(R.id.txtSummaryHeading);
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

                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {

        if (id == FROM_DIALOG_ID)
            return new DatePickerDialog(this, dpListenerForFromDate, year, month - 1, day);
        else if (id == TO_DIALOG_ID)
            return new DatePickerDialog(this, dpListenerForToDate, year, month, day);

        return null;
    }

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
