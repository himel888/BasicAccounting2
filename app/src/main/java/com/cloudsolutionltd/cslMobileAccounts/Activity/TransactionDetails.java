package com.cloudsolutionltd.cslMobileAccounts.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionCRUD;
import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionTable;
import com.cloudsolutionltd.cslMobileAccounts.R;

public class TransactionDetails extends AppCompatActivity {


    EditText txtAccountFrom, txtAccountTo, txtAmount, txtDescription, txtBankCheque;
    TextView txtDate;
    Button btnEdit, btnUpdate, btnDelete;
    LedgerTransactionTable transactionDetails;
    int adapterPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8cce64")));

        initialize();
        final LedgerTransactionCRUD ledgerTransactionCRUD = new LedgerTransactionCRUD(this);
        transactionDetails = (LedgerTransactionTable) getIntent().getSerializableExtra("transactionDetails");
        adapterPosition = getIntent().getIntExtra("adapterPosition",0);

        txtDate.setText(transactionDetails.getTable2TransactionDate());
        txtAccountFrom.setText(ledgerTransactionCRUD.getAccountName(transactionDetails.getTable2AccFrom()));
        txtAccountTo.setText(ledgerTransactionCRUD.getAccountName(transactionDetails.getTable2AccTo()));
        txtAmount.setText(String.valueOf(transactionDetails.getTable2AmountCr()));
        txtDescription.setText(String.valueOf(transactionDetails.getTable2Description()));
        txtBankCheque.setText(String.valueOf(transactionDetails.getTable2BankCheque()));

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txtAccountFrom.setEnabled(true);
                //txtAccountTo.setEnabled(true);
                //txtAmount.setEnabled(true);
                txtDescription.setEnabled(true);
                txtBankCheque.setEnabled(true);

                btnEdit.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.VISIBLE);

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ledgerTransactionCRUD.deletePairedRow(transactionDetails.getTable2PidPair());
                AllTransaction.adapter.remove(AllTransaction.adapter.getItem(adapterPosition));
                AllTransaction.adapter.notifyDataSetChanged();
                //Intent intent = new Intent(getApplicationContext(),AllTransaction.class);
                //startActivity(intent);
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionDetails.setTable2Description(txtDescription.getText().toString());
                transactionDetails.setTable2BankCheque(txtBankCheque.getText().toString());

                ledgerTransactionCRUD.updatePairedRow(transactionDetails);
            }
        });


    }

    private void initialize(){
        txtAccountFrom = (EditText) findViewById(R.id.txtAccountFrom);
        txtAccountTo = (EditText) findViewById(R.id.txtAccountTo);
        txtAmount = (EditText) findViewById(R.id.txtAmount);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtBankCheque = (EditText) findViewById(R.id.txtBankCheque);
        txtDate = (TextView) findViewById(R.id.txtDate);

        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.home) {
//            onBackPressed();
//            return true;
//        }
//
//        return false;
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
