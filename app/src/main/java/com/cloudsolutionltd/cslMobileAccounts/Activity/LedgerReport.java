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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudsolutionltd.cslMobileAccounts.Adapter.ArrayAdapterForLedgerReport;
import com.cloudsolutionltd.cslMobileAccounts.FormatDate;
import com.cloudsolutionltd.cslMobileAccounts.R;
import com.cloudsolutionltd.cslMobileAccounts.Utility;
import com.cloudsolutionltd.cslMobileAccounts.db.ChartOfAccountCRUD;
import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionCRUD;
import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionTable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LedgerReport extends AppCompatActivity {


    final int FROM_DIALOG_ID = 1, TO_DIALOG_ID = 2;


    //Variable for Activity content
    String[][] accountNameId;
    ArrayList<String> accountName;
    int year, month, day, accountId;
    double[] currentBalance;
    String date, selectedMonth, selectedDay;
    TextView txtFromDate;
    TextView txtToDate;
    TextView txtClosingBalance;
    LinearLayout reportBody;
    TextView txtReportHint;


    //Adapter for Spinner
    ArrayAdapter<String> adapter;
    public ArrayList<LedgerTransactionTable> voucherEntryList;
    public ArrayAdapterForLedgerReport adapterForLedgerReport;
    public ListView listLedger;
    NumberFormat numberFormat;

    ListView drawerList;
    DrawerLayout navigationMenu;
    ArrayAdapter menuListAdapter;
    ActionBarDrawerToggle mDrawerToggle;
    String[] item;

    private static Double openingBalance;

    public Double getOpeningBalance() {
        return openingBalance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledger_report);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0aa4c7")));

        item = getResources().getStringArray(R.array.drawer_list);
        // mTitle  = mDrawerTitle = getTitle();

        navigationMenu = (DrawerLayout) findViewById(R.id.navigationMenu);
        drawerList = (ListView) findViewById(R.id.drawarList);

        // Set the drawer toggle as the DrawerListener
        navigationMenu.setDrawerListener(mDrawerToggle);

        txtClosingBalance = (TextView) findViewById(R.id.txtClosingBalance);

        menuListAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, item);

        mDrawerToggle = new ActionBarDrawerToggle(this, navigationMenu, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when drawer is closed */
            public void onDrawerClosed(View view) {
                //highlightSelectedCountry();
                getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_ledger_report));
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

        ChartOfAccountCRUD crud = new ChartOfAccountCRUD(this);
        final LedgerTransactionCRUD ledgerTransactionCRUD = new LedgerTransactionCRUD(this);
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumFractionDigits(2);


        reportBody = (LinearLayout) findViewById(R.id.reportBody);
        txtReportHint = (TextView) findViewById(R.id.txtReportHInt);

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

        final Spinner spnAccountName = (Spinner) findViewById(R.id.spnAccountName);
        txtFromDate = (TextView) findViewById(R.id.txtFromDate);
        txtFromDate.setText(FormatDate.getDateToShow(String.valueOf(year) + "/" + selectedMonth + "/" + "01"));
        txtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(FROM_DIALOG_ID);
            }
        });


        txtToDate = (TextView) findViewById(R.id.txtToDate);
        txtToDate.setText(FormatDate.getDateToShow(date));
        txtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TO_DIALOG_ID);
            }
        });


        final Button btnSearch = (Button) findViewById(R.id.btnSearch);
        final TextView txtOpeningBalance = (TextView) findViewById(R.id.txtOpeningBalance);
        //txtopeningBalance.setVisibility(View.INVISIBLE);
        //txtAccountInfo.setVisibility(View.INVISIBLE);

        try {
            accountNameId = crud.getTransactionalAccountName();
            accountName = new ArrayList<>();
            for (int i = 1; i < accountNameId.length; i++) {
                accountName.add(accountNameId[i][1] + " - " + accountNameId[i][0]);
            }
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_row_ledger_report, accountName);
            spnAccountName.setAdapter(adapter);
        } catch (Exception e) {
            System.out.println(e);
        }


        listLedger = (ListView) findViewById(R.id.listLedger);
        /*voucherEntryList = ledgerTransactionCRUD.getAllVoucherEntry(2,"1/3/2015","8/10/2015");
        adapterForLedgerReport = new ArrayAdapterForLedgerReport(this,voucherEntryList);
        listLedger.setAdapter(adapterForLedgerReport);*/


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spnAccountName.getSelectedItem().toString().equals("")) {
                    ((TextView) spnAccountName.getSelectedView()).setError("");


                } else if (FormatDate.getDateToSave(txtFromDate.getText().toString()).compareTo(FormatDate.getDateToSave(txtToDate.getText().toString())) > 0) {
                    Toast.makeText(getApplicationContext(), "From date should less then To date", Toast.LENGTH_SHORT).show();
                } else {
                    listLedger.setAdapter(null);
                    openingBalance = ledgerTransactionCRUD.getPreviousBalance(FormatDate.getDateToSave(txtFromDate.getText().toString()),
                            Integer.parseInt(accountNameId[spnAccountName.getSelectedItemPosition() + 1][0]));

                    //txtAccountInfo.setText("Ledger of " + accountNameId[spnAccountName.getSelectedItemPosition()][1] +
                    //"\nFrom " + txtFromDate.getText() +
                    //" To " + txtToDate.getText());
                    //txtAccountInfo.setVisibility(View.VISIBLE);

                    txtReportHint.setVisibility(View.GONE);
                    reportBody.setVisibility(View.VISIBLE);
                    txtOpeningBalance.setText(String.valueOf(numberFormat.format(openingBalance)));
                    //txtopeningBalance.setVisibility(View.VISIBLE);

                    voucherEntryList = ledgerTransactionCRUD.getVoucherEntryForSpecificAccount(Integer.parseInt(accountNameId[spnAccountName.getSelectedItemPosition() + 1][0]),
                            FormatDate.getDateToSave(String.valueOf(txtFromDate.getText())), FormatDate.getDateToSave(String.valueOf(txtToDate.getText())));
                    if (voucherEntryList == null || voucherEntryList.size() < 1) {
                        Toast.makeText(getApplicationContext(), "! No record found", Toast.LENGTH_LONG).show();
                    } else {
                        try {


                            txtFromDate.setTextColor(Color.WHITE);
                            Toast.makeText(getApplicationContext(), "! Ledger report From " + txtFromDate.getText() + " To " + txtToDate.getText(), Toast.LENGTH_LONG).show();
                            currentBalance = new double[voucherEntryList.size()];
                            for (int i = 0; i < voucherEntryList.size(); i++) {
                                if (i == 0)
                                    currentBalance[i] = openingBalance + (voucherEntryList.get(i).getTable2AmountDr() - voucherEntryList.get(i).getTable2AmountCr());
                                else
                                    currentBalance[i] = currentBalance[i - 1] + (voucherEntryList.get(i).getTable2AmountDr() - voucherEntryList.get(i).getTable2AmountCr());
                            }

                            adapterForLedgerReport = new ArrayAdapterForLedgerReport(LedgerReport.this, voucherEntryList, currentBalance);
                            listLedger.setAdapter(adapterForLedgerReport);
                            txtClosingBalance.setText(String.valueOf(currentBalance[voucherEntryList.size() - 1]));

                            Utility.setListViewHeightBasedOnChildren(listLedger);
                            Log.e("OK Mamun", "Adapter working well");
                        } catch (Exception e) {
                            Log.e("Button problem", String.valueOf(e));
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }

                        //Toast.makeText(getApplicationContext(),"list is not empty", Toast.LENGTH_SHORT).show();
                    }


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
                        selectedMonth = "0" + String.valueOf(month);
                    } else
                        selectedMonth = String.valueOf(month);


                    if (day < 10) {
                        selectedDay = "0" + String.valueOf(day);
                    } else
                        selectedDay = String.valueOf(day);

                    date = String.valueOf(year) + "/" + selectedMonth + "/" + selectedDay;
                    txtFromDate.setText(FormatDate.getDateToShow(date));
                    if (FormatDate.getDateToSave(txtFromDate.getText().toString()).compareTo(FormatDate.getDateToSave(txtToDate.getText().toString())) > 0) {

                        txtFromDate.setTextColor(Color.RED);
                        txtToDate.setTextColor(Color.WHITE);


                    } else {
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

                    if (FormatDate.getDateToSave(txtFromDate.getText().toString()).compareTo(FormatDate.getDateToSave(txtToDate.getText().toString())) > 0) {

                        txtFromDate.setTextColor(Color.RED);
                        txtToDate.setTextColor(Color.WHITE);


                    } else {
                        txtFromDate.setTextColor(Color.WHITE);
                        txtToDate.setTextColor(Color.WHITE);

                    }


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
