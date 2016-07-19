package com.cloudsolutionltd.cslMobileAccounts.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudsolutionltd.cslMobileAccounts.Adapter.AdapterForIncomeExpenseStatement;
import com.cloudsolutionltd.cslMobileAccounts.FormatDate;
import com.cloudsolutionltd.cslMobileAccounts.R;
import com.cloudsolutionltd.cslMobileAccounts.Utility;
import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionCRUD;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ProfitLoss extends AppCompatActivity {

    final int FROM_DIALOG_ID = 1, TO_DIALOG_ID = 2;

    //Activity component
    TextView txtFromDate, txtToDate, txtTotalAmount, txtIncomelistHeading, txtExpenseListHeading;
    Button btnSearch;
    ListView listIncome, listExpense;
    Utility utility;
    NumberFormat numberFormat;
    LinearLayout reportBody;
    TextView txtReportHint;


    //Variable for activity work
    int year, month, day;
    Double totalIncome = 0.0, totalExpense = 0.0, profitOrLoss = 0.0;
    String date, selectedMonth, selectedDay, accountType = null;

    ArrayList<String[]> statement = null;
    AdapterForIncomeExpenseStatement adapterForIncome, adapterForExpense;
    LedgerTransactionCRUD ledgerTransactionCRUD;

    ListView drawerList;
    DrawerLayout navigationMenu;
    ArrayAdapter menuListAdapter;
    ActionBarDrawerToggle mDrawerToggle;
    String[] item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_loss);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#91563d")));

        item = getResources().getStringArray(R.array.drawer_list);
        // mTitle  = mDrawerTitle = getTitle();

        navigationMenu = (DrawerLayout) findViewById(R.id.navigationMenu);
        drawerList = (ListView) findViewById(R.id.drawarList);

        // Set the drawer toggle as the DrawerListener
        navigationMenu.setDrawerListener(mDrawerToggle);

        menuListAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, item);

        mDrawerToggle = new ActionBarDrawerToggle(this, navigationMenu, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when drawer is closed */
            public void onDrawerClosed(View view) {
                //highlightSelectedCountry();
                getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_profit_loss));
                supportInvalidateOptionsMenu();
            }

            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(getResources().getString(R.string.title_select_menu));
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
                    Intent intent = new Intent(getApplicationContext(), BasicAccounting.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                    finish();
                } else if (position == 1) {
                    Intent intent = new Intent(getApplicationContext(), VoucherEntry.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                    finish();
                } else if (position == 2) {
                    Intent intent = new Intent(getApplicationContext(), AllTransaction.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                    finish();
                } else if (position == 3) {
                    Intent intent = new Intent(getApplicationContext(), LedgerReport.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                    finish();
                } else if (position == 4) {
                    Intent intent = new Intent(getApplicationContext(), IncomeExpenseStatement.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                    finish();
                } else if (position == 5) {
                    Intent intent = new Intent(getApplicationContext(), ProfitLoss.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                    finish();
                } else if (position == 6) {
                    Intent intent = new Intent(getApplicationContext(), BalanceSheet.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                    finish();
                } else if (position == 7) {
                    Intent intent = new Intent(getApplicationContext(), ChartOfAccount.class);
                    startActivity(intent);
                    navigationMenu.closeDrawers();
                    finish();
                } else if (position == 8) {
                    navigationMenu.closeDrawers();
                    finish();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(2);

        ledgerTransactionCRUD = new LedgerTransactionCRUD(this);
        utility = new Utility();
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
                try {
                    statement = ledgerTransactionCRUD.getAccountStatement(FormatDate.getDateToSave(txtFromDate.getText().toString()),
                            FormatDate.getDateToSave(txtToDate.getText().toString()), "Expense");
                    if (!statement.isEmpty()) {

                        for (int i = 0; i < statement.size(); i++)
                            totalExpense += Double.valueOf(statement.get(i)[1]);


                        adapterForExpense = new AdapterForIncomeExpenseStatement(ProfitLoss.this, statement);
                        listExpense.setAdapter(adapterForExpense);

                        utility.setListViewHeightBasedOnChildren(listExpense);
                        //txtTotalAmount.setText("Total amount: " + String.valueOf(totalAmount));
                    } else
                        Toast.makeText(getApplicationContext(), "No such data found", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("ListView Error: ", e.toString());
                }


                try {
                    statement = ledgerTransactionCRUD.getAccountStatement(FormatDate.getDateToSave(txtFromDate.getText().toString()),
                            FormatDate.getDateToSave(txtToDate.getText().toString()), "Income");
                    if (!statement.isEmpty()) {

                        txtIncomelistHeading.setVisibility(View.VISIBLE);
                        for (int i = 0; i < statement.size(); i++)
                            totalIncome += Double.valueOf(statement.get(i)[1]);

                        totalIncome *= -1;

                        adapterForIncome = new AdapterForIncomeExpenseStatement(ProfitLoss.this, statement);
                        listIncome.setAdapter(adapterForIncome);
                        utility.setListViewHeightBasedOnChildren(listIncome);
                        //txtTotalAmount.setText("Total amount: " + String.valueOf(totalAmount));
                    } else
                        Toast.makeText(getApplicationContext(), "No such data found", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("ListView Error: ", e.toString());
                }

                profitOrLoss = totalIncome - totalExpense;

                txtReportHint.setVisibility(View.GONE);
                reportBody.setVisibility(View.VISIBLE);

                if (profitOrLoss < 0)
                    txtTotalAmount.setText("Loss: " + numberFormat.format(profitOrLoss * -1));
                else
                    txtTotalAmount.setText("Profit: " + numberFormat.format(profitOrLoss));

                profitOrLoss = 0.0;
                totalExpense = 0.0;
                totalIncome = 0.0;


            }
        });


    }

    private void initialize() {

        txtFromDate = (TextView) findViewById(R.id.txtFromDate);
        txtToDate = (TextView) findViewById(R.id.txtToDate);
        txtTotalAmount = (TextView) findViewById(R.id.txtTotalAmount);
        txtIncomelistHeading = (TextView) findViewById(R.id.txtIncomeListHeading);
        txtExpenseListHeading = (TextView) findViewById(R.id.txtExpenseListHeading);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        listIncome = (ListView) findViewById(R.id.listIncome);
        listExpense = (ListView) findViewById(R.id.listExpense);
        reportBody = (LinearLayout) findViewById(R.id.reportBody);
        txtReportHint = (TextView) findViewById(R.id.txtReportHInt);
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all_transaction, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (id == R.id.settings) {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.help) {
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
