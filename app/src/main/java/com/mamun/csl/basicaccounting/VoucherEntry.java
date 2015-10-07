package com.mamun.csl.basicaccounting;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.mamun.csl.basicaccounting.db.ChartOfAccountCRUD;
import com.mamun.csl.basicaccounting.db.LedgerTransactionCRUD;
import com.mamun.csl.basicaccounting.db.LedgerTransactionTable;

import java.util.Calendar;

public class VoucherEntry extends AppCompatActivity {




    int year,month,day;
    final int DIALOG_ID = 1;

    //Declare activity content
    EditText txtDate,txtAmmount,txtRefBill,txtDescription,txtBankCheque,txtTransactionType;
    Spinner spnAccountFrom,spnAccountTo;
    CheckBox chkStatus;
    Button btnSave;

    //Declare variable and adapter for spinner
    String[][] accountNameId;
    String[] accountFromTo;
    int pidPair;
    ArrayAdapter<String> accountFromAdapter, accountToAdapter;
    String date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_entry);


        Calendar calander = Calendar.getInstance();
        year = calander.get(Calendar.YEAR);
        month = calander.get(Calendar.MONTH)+1;
        day = calander.get(Calendar.DAY_OF_MONTH);
        date = String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year);

        //Register Activity component
        txtDate = (EditText) findViewById(R.id.txtDate);
        txtDate.setText(day+"/"+month+"/"+year);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
        txtAmmount = (EditText) findViewById(R.id.txtAmmount);
        txtRefBill = (EditText) findViewById(R.id.txtRefBill);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtBankCheque = (EditText) findViewById(R.id.txtBankChecque);
        txtTransactionType = (EditText) findViewById(R.id.txtTransactionType);
        spnAccountFrom = (Spinner) findViewById(R.id.spnAccountFrom);
        spnAccountTo = (Spinner) findViewById(R.id.spnAccountTo);
        chkStatus = (CheckBox) findViewById(R.id.chkStatus);
        btnSave = (Button) findViewById(R.id.btnSave);


        //Object for voucher entry
        final LedgerTransactionTable table = new LedgerTransactionTable();
        ChartOfAccountCRUD coaCrud = new ChartOfAccountCRUD(this);
        final LedgerTransactionCRUD crud = new LedgerTransactionCRUD(this);

        accountNameId = coaCrud.getTransactionalAccountName();
        accountFromTo = new String[accountNameId.length];

        for (int i = 0; i < accountNameId.length; i++){
            accountFromTo[i] = accountNameId[i][1];
        }

        accountFromAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, accountFromTo);
        accountToAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,accountFromTo);
        spnAccountFrom.setAdapter(accountFromAdapter);
        spnAccountTo.setAdapter(accountToAdapter);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    table.setTable2PidPair(pidPair=crud.getMaxPidPair());
                    table.setTable2TransactionDate(date);
                    table.setTable2AccFrom(Integer.parseInt(accountNameId[(spnAccountFrom.getSelectedItemPosition())][0]));
                    table.setTable2AccTo(Integer.parseInt(accountNameId[spnAccountTo.getSelectedItemPosition()][0]));
                    table.setTable2AmountDr(Integer.parseInt(txtAmmount.getText().toString()));
                    table.setTable2AmountCr(Integer.parseInt(txtAmmount.getText().toString()));
                    table.setTable2RefBill(txtRefBill.getText().toString());
                    table.setTable2Description(txtDescription.getText().toString());
                    table.setTable2BankCheque(txtBankCheque.getText().toString());
                    table.setTable2Transaction_type(txtTransactionType.getText().toString());

                    if (chkStatus.isChecked()){
                        table.setTable2Status("Y");
                    }else
                        table.setTable2Status("N");


                    crud.insert(table);
                }catch (Exception e){
                    System.out.print(e);
                }


            }
        });

    }


    @Override
    protected Dialog onCreateDialog(int id){

        if(id == DIALOG_ID)
            return new DatePickerDialog(this, dpListener,year,month,day);

        return null;
    }

    private DatePickerDialog.OnDateSetListener dpListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year_x, int monthOfYear, int dayOfMonth) {
                    year = year_x;
                    month = monthOfYear+1;
                    day = dayOfMonth;


                    date = String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year);
                    txtDate.setText(date);
                }
            };


}