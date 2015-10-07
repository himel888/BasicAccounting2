package com.mamun.csl.basicaccounting;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.mamun.csl.basicaccounting.db.ChartOfAccountCRUD;
import com.mamun.csl.basicaccounting.db.LedgerTransactionCRUD;
import com.mamun.csl.basicaccounting.db.LedgerTransactionTable;

import java.util.ArrayList;
import java.util.Calendar;

public class LedgerReport extends AppCompatActivity {




    final int FROM_DIALOG_ID = 1, TO_DIALOG_ID = 2;


    //Variable for Activity content
    String[][] accountNameId;
    String[] accountName;
    int year,month,day;
    String date;
    TextView txtFromDate;
    TextView txtToDate;

    //Adapter for Spinner
    ArrayAdapter<String> adapter;
    ArrayList<LedgerTransactionTable> listOfLedgerTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledger_report);


        ChartOfAccountCRUD crud = new ChartOfAccountCRUD(this);
        final LedgerTransactionCRUD ledgerTransactionCRUD = new LedgerTransactionCRUD(this);

        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        date = String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year);

        final Spinner spnAccountName = (Spinner) findViewById(R.id.spnAccountName);
        txtFromDate = (TextView) findViewById(R.id.txtFromDate);
        txtFromDate.setText(date);
        txtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(FROM_DIALOG_ID);
            }
        });


        txtToDate = (TextView) findViewById(R.id.txtToDate);
        txtToDate.setText(date);
        txtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TO_DIALOG_ID);
            }
        });


        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        final TextView txtAccountInfo = (TextView) findViewById(R.id.txtAccountInfo);
        final TextView txtPreviousBalance = (TextView) findViewById(R.id.txtPreviousBalance);
        txtPreviousBalance.setVisibility(View.INVISIBLE);
        txtAccountInfo.setVisibility(View.INVISIBLE);

        try {
            accountNameId = crud.getTransactionalAccountName();
            accountName = new String[accountNameId.length];
            for (int i =0; i < accountNameId.length; i++){
                accountName[i] = accountNameId[i][1] + " - " + accountNameId[i][0];
            }
            adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,accountName);
            spnAccountName.setAdapter(adapter);
        }catch (Exception e){
            System.out.println(e);
        }



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int previousBalance = ledgerTransactionCRUD.getPreviousBalance(String.valueOf(txtFromDate.getText()),
                        Integer.parseInt(accountNameId[spnAccountName.getSelectedItemPosition()][0]));

                txtAccountInfo.setText("Ledger of "+accountNameId[spnAccountName.getSelectedItemPosition()][1]+
                        "\nFrom"+txtFromDate.getText()+
                        " To "+txtToDate.getText());
                txtAccountInfo.setVisibility(View.VISIBLE);
                txtPreviousBalance.setText("Previous Balance = " + previousBalance);
                txtPreviousBalance.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    protected Dialog onCreateDialog(int id){

        if(id == FROM_DIALOG_ID)
            return new DatePickerDialog(this, dpListenerForFromDate,year,month-1,day);
        else if (id == TO_DIALOG_ID)
            return new DatePickerDialog(this, dpListenerForToDate,year,month,day);

        return null;
    }

    private DatePickerDialog.OnDateSetListener dpListenerForFromDate =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year_x, int monthOfYear, int dayOfMonth) {
                    year = year_x;
                    month = monthOfYear+1;
                    day = dayOfMonth;

                    date = String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year);
                    txtFromDate.setText(date);

                }
            };

    private DatePickerDialog.OnDateSetListener dpListenerForToDate =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year_x, int monthOfYear, int dayOfMonth) {
                    year = year_x;
                    month = monthOfYear+1;
                    day = dayOfMonth;

                    date = String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year);
                    txtToDate.setText(date);

                }
            };

}
