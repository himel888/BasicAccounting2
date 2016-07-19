package com.cloudsolutionltd.cslMobileAccounts.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudsolutionltd.cslMobileAccounts.FormatDate;
import com.cloudsolutionltd.cslMobileAccounts.R;
import com.cloudsolutionltd.cslMobileAccounts.db.ChartOfAccountCRUD;
import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionCRUD;
import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionTable;

import java.util.ArrayList;
import java.util.Calendar;

public class VoucherEntry extends AppCompatActivity {


    int year, month, day;
    final int DIALOG_ID = 1;

    //Declare activity content
    EditText txtAmount, txtDescription, txtBankCheque;
    TextView txtDate;
    Spinner spnAccountFrom, spnAccountTo;
    Button btnSave;

    //Declare variable and adapter for spinner
    String[][] accountNameId;
    //String[] accountFromTo;
    ArrayList<String> accountFromTo;
    int pidPair, accFromId, accToId;
    ArrayAdapter<String> accountFromAdapter, accountToAdapter;
    String date, selectedMonth, selectedDay;


    ListView drawerList;
    DrawerLayout navigationMenu;
    ArrayAdapter menuListAdapter;
    ActionBarDrawerToggle mDrawerToggle;
    String[] item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_entry);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF2373C7")));

        item = getResources().getStringArray(R.array.drawer_list);

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
                getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_voucher_entry));
                supportInvalidateOptionsMenu();
            }

            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(getResources().getString(R.string.title_select_menu));
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


        date = String.valueOf(year) + "/" + selectedMonth + "/" + selectedDay;

        //Register Activity component
        initialize();


        //Object for voucher_entry entry
        final LedgerTransactionTable table = new LedgerTransactionTable();
        ChartOfAccountCRUD coaCrud = new ChartOfAccountCRUD(this);
        final LedgerTransactionCRUD crud = new LedgerTransactionCRUD(this);

        accountNameId = coaCrud.getTransactionalAccountName();
        accountFromTo = new ArrayList<>();
        //accountFromTo[0] = "";

        for (int i = 1; i < accountNameId.length; i++) {
            accountFromTo.add(accountNameId[i][1] + " - " + accountNameId[i][0]);
        }

        accountFromAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_row_account_from, accountFromTo);
        accountToAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_row_account_to, accountFromTo);

        spnAccountFrom.setAdapter(accountFromAdapter);
        spnAccountTo.setAdapter(accountToAdapter);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {


                    if (spnAccountFrom.getSelectedItem().toString().equals("") && spnAccountTo.getSelectedItem().toString().equals("")) {

                        ((TextView) spnAccountFrom.getSelectedView()).setError("Choose Account");
                        ((TextView) spnAccountTo.getSelectedView()).setError("Choose Account");
                    } else if (spnAccountFrom.getSelectedItem().toString().equals(""))
                        ((TextView) spnAccountFrom.getSelectedView()).setError("Choose Account");
                    else if (spnAccountTo.getSelectedItem().toString().equals(""))
                        ((TextView) spnAccountTo.getSelectedView()).setError("Choose Account");
                    else if (spnAccountFrom.getSelectedItem().toString().equals(spnAccountTo.getSelectedItem().toString())) {

                        Toast.makeText(getApplicationContext(), "From and To account couldn't be same", Toast.LENGTH_LONG).show();
                        ((TextView) spnAccountFrom.getSelectedView()).setTextColor(Color.YELLOW);
                        ((TextView) spnAccountTo.getSelectedView()).setTextColor(Color.YELLOW);

                    } else if (txtAmount.getText().toString().equals("")) {

                        txtAmount.setError("Fill amount");

                    } else {

                        table.setTable2PidPair(pidPair = crud.getMaxPidPair());
                        table.setTable2TransactionDate(date);
                        table.setTable2AccFrom(Integer.parseInt(accountNameId[spnAccountFrom.getSelectedItemPosition() + 1][0]));
                        table.setTable2AccTo(Integer.parseInt(accountNameId[spnAccountTo.getSelectedItemPosition() + 1][0]));
                        table.setTable2AmountDr(Double.parseDouble(txtAmount.getText().toString()));
                        table.setTable2AmountCr(Double.parseDouble(txtAmount.getText().toString()));
                        table.setTable2Description(txtDescription.getText().toString());
                        table.setTable2BankCheque(txtBankCheque.getText().toString());

                        long result = crud.insert(table);
                        if (result >= 0) {
                            Toast.makeText(getApplicationContext(), "Insertion successful", Toast.LENGTH_SHORT).show();
//                            txtAmount.setText(null);
//                            txtBankCheque.setText(null);
//                            txtDescription.setText(null);
//                            txtRefBill.setText(null);
//                            txtTransactionType.setText(null);
                            Intent intent = new Intent(getApplicationContext(), AllTransaction.class);
                            startActivity(intent);
                            finish();
                        } else
                            Toast.makeText(getApplicationContext(), "Insertion failed", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    System.out.print(e);
                }


            }
        });
    }

    public void initialize() {
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtDate.setText(FormatDate.getDateToShow(date));
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
        txtAmount = (EditText) findViewById(R.id.txtAmount);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtBankCheque = (EditText) findViewById(R.id.txtBankChecque);
        spnAccountFrom = (Spinner) findViewById(R.id.spnAccountFrom);
        spnAccountTo = (Spinner) findViewById(R.id.spnAccountTo);
        btnSave = (Button) findViewById(R.id.btnSave);
    }


    @Override
    protected Dialog onCreateDialog(int id) {

        if (id == DIALOG_ID)
            return new DatePickerDialog(this, dpListener, year, month, day);

        return null;
    }

    private DatePickerDialog.OnDateSetListener dpListener =
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
                    txtDate.setText(FormatDate.getDateToShow(date));
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


}