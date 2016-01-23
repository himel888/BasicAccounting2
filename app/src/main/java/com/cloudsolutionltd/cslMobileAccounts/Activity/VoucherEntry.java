package com.cloudsolutionltd.cslMobileAccounts.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudsolutionltd.cslMobileAccounts.FormatDate;
import com.cloudsolutionltd.cslMobileAccounts.db.ChartOfAccountCRUD;
import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionCRUD;
import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionTable;
import com.cloudsolutionltd.cslMobileAccounts.R;

import java.util.ArrayList;
import java.util.Calendar;

public class VoucherEntry extends AppCompatActivity {


    int year, month, day;
    final int DIALOG_ID = 1;

    //Declare activity content
    EditText txtAmount, txtRefBill, txtDescription, txtBankCheque, txtTransactionType;
    TextView txtDate;
    Spinner spnAccountFrom, spnAccountTo;
    Button btnSave;

    //Declare variable and adapter for spinner
    String[][] accountNameId;
    //String[] accountFromTo;
    ArrayList<String> accountFromTo;
    int pidPair,accFromId, accToId;
    ArrayAdapter<String> accountFromAdapter, accountToAdapter;
    String date, selectedMonth, selectedDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_entry);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF2373C7")));

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
                        ((TextView)spnAccountFrom.getSelectedView()).setTextColor(Color.RED);
                        ((TextView)spnAccountTo.getSelectedView()).setTextColor(Color.RED);

                    } else if (txtAmount.getText().toString().equals("")) {

                        txtAmount.setError("Fill amount");

                    } else {

                        table.setTable2PidPair(pidPair = crud.getMaxPidPair());
                        table.setTable2TransactionDate(date);
                        table.setTable2AccFrom(Integer.parseInt(accountNameId[spnAccountFrom.getSelectedItemPosition()+1][0]));
                        table.setTable2AccTo(Integer.parseInt(accountNameId[spnAccountTo.getSelectedItemPosition()+1][0]));
                        table.setTable2AmountDr(Double.parseDouble(txtAmount.getText().toString()));
                        table.setTable2AmountCr(Double.parseDouble(txtAmount.getText().toString()));
                        table.setTable2RefBill(txtRefBill.getText().toString());
                        table.setTable2Description(txtDescription.getText().toString());
                        table.setTable2BankCheque(txtBankCheque.getText().toString());
                        table.setTable2Transaction_type(txtTransactionType.getText().toString());

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

    public void initialize(){
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtDate.setText(FormatDate.getDateToShow(date));
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
        txtAmount = (EditText) findViewById(R.id.txtAmount);
        txtRefBill = (EditText) findViewById(R.id.txtRefBill);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtBankCheque = (EditText) findViewById(R.id.txtBankChecque);
        txtTransactionType = (EditText) findViewById(R.id.txtTransactionType);
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


}