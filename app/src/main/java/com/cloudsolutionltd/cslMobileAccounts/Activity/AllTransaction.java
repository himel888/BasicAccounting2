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
    public static ArrayList<LedgerTransactionTable> voucherEntryList;
    LedgerTransactionCRUD ledgerTransactionCRUD;
    public static ArrayAdapterForAllTransaction adapter;
    LinearLayout headingLayout;

    SimpleDateFormat simpleDateFormat;
    Calendar calendar;

    ListView drawerList;
    DrawerLayout navigationMenu;
    ArrayAdapter menuListAdapter;
    ActionBarDrawerToggle mDrawerToggle;
    String[] item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transaction);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8cce64")));

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
                getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_all_transaction));
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
        txtFromDate.setText(FormatDate.getDateToShow(String.valueOf(year) + "/" + selectedMonth + "/" + "01"));
        txtToDate.setText(FormatDate.getDateToShow(date));

        //Show default report from first date to current date of this month
        try {
            voucherEntryList = ledgerTransactionCRUD.getAllVoucherEntry(FormatDate.getDateToSave(txtFromDate.getText().toString()),
                    FormatDate.getDateToSave(txtToDate.getText().toString()));

//            Toast.makeText(getApplicationContext(),txtFromDate.getText().toString()+" "+
//                    txtToDate.getText().toString(),Toast.LENGTH_LONG).show();

            if (voucherEntryList.size() == 0) {
                Toast.makeText(getApplicationContext(), "No available transaction\n From " + txtFromDate.getText().toString() + " To " + txtToDate.getText().toString(), Toast.LENGTH_LONG).show();
                adapter = new ArrayAdapterForAllTransaction(AllTransaction.this, voucherEntryList);
                listOfAccountName.setAdapter(adapter);
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
                            voucherEntryList.addAll(ledgerTransactionCRUD.getAllVoucherEntry(
                                    FormatDate.getDateToSave(String.valueOf(txtFromDate.getText())),
                                    FormatDate.getDateToSave(String.valueOf(txtToDate.getText()))));
//                            Toast.makeText(getApplicationContext(), FormatDate.getDateToSave(String.valueOf(txtFromDate.getText()))+"  "+
//                                    FormatDate.getDateToSave(String.valueOf(txtToDate.getText())),Toast.LENGTH_LONG).show();
                            //listOfAccountName.setAdapter(null);


                            if (voucherEntryList.size() < 1) {
                                Toast.makeText(getApplicationContext(),
                                        "No available transaction\n From " + txtFromDate.getText().toString() +
                                                " To " + txtToDate.getText().toString(), Toast.LENGTH_LONG).show();
                                adapter.notifyDataSetChanged();

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
                    if (FormatDate.getDateToSave(txtFromDate.getText().toString()).compareTo(FormatDate.getDateToSave(txtToDate.getText().toString())) > 0) {

                        txtFromDate.setTextColor(Color.RED);
                        txtToDate.setTextColor(Color.RED);


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
