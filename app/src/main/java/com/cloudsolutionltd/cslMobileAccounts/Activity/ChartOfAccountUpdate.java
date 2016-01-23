package com.cloudsolutionltd.cslMobileAccounts.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cloudsolutionltd.cslMobileAccounts.db.ChartOfAccountCRUD;
import com.cloudsolutionltd.cslMobileAccounts.db.ChartOfAccountTable;
import com.cloudsolutionltd.cslMobileAccounts.R;

/**
 * Created by Kazi Abdullah Al Mamun on 8/31/15.
 */
public class ChartOfAccountUpdate extends AppCompatActivity {


    //Declare activity component
    Spinner spnAccountType;
    EditText txtAccountName, txtAccountId;
    Button btnUpdate, btnDelete;

    //Data Source for Account Name, Account Type and Account Group Spinner
    private static String[] type = {"Asset", "Liability", "Income","Expense" };
    //private static String[] group = {"Group", "Transactional"};
    //String[][] accountNameId;
    //String[] accountName;

    //Declare adapter
    private ArrayAdapter<String> accountTypeAdapter, accountGroupAdapter;
    ArrayAdapter<String> parentAdapter;

    //Declare variable
    int defaultSpnParentName;
    private int table1Pid;
    //private int table1Pid_parent;
    private String table1AccountName;
    private int table1AccountId;
    private String table1AccountType;
    //private String table1AccountGroup;
    //private String table1Status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_of_account_update);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4A97DF")));


        final ChartOfAccountCRUD crud = new ChartOfAccountCRUD(this);



        //Register activity component

        spnAccountType = (Spinner) findViewById(R.id.spnType);
        txtAccountName = (EditText) findViewById(R.id.txtAccountName);
        txtAccountId = (EditText) findViewById(R.id.txtAccountId);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);


        final ChartOfAccountTable table = (ChartOfAccountTable) getIntent().getSerializableExtra("table");
        table1Pid = table.getTable1Pid();
        //table1Pid_parent = table.getTable1Pid_parent();
        table1AccountName = table.getTable1AccountName();
        table1AccountId = table.getTable1AccountId();
        table1AccountType = table.getTable1AccountType();
        //table1AccountGroup = table.getTable1AccountGroup();
        //table1Status = table.getTable1Status();

        //Toast.makeText(getApplicationContext(),table1AccountName,Toast.LENGTH_LONG).show();


        try {
            txtAccountName.setText(table1AccountName);
            txtAccountId.setText(String.valueOf(table1AccountId));
        } catch (Exception e) {
            System.out.println(e);
        }


        accountTypeAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_row_add_account_head, type);
        spnAccountType.setAdapter(accountTypeAdapter);

        if (table1AccountType.equals("Asset")) {
            spnAccountType.setSelection(0);
        } else if (table1AccountType.equals("Liability")) {
            spnAccountType.setSelection(1);
        } else if (table1AccountType.equals("Income")) {
            spnAccountType.setSelection(2);
        } else
            spnAccountType.setSelection(3);


//        accountGroupAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, group);
//        spnAccountGroup.setAdapter(accountGroupAdapter);
//
//        if (table1AccountGroup.equals("Group")) {
//            spnAccountGroup.setSelection(0);
//        } else
//            spnAccountGroup.setSelection(1);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int result;
                try{
                    result = crud.deleteRow(table1Pid,table1AccountId);
                    if (result == 1){
                        Toast.makeText(ChartOfAccountUpdate.this, "Account delete successFul", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
//                        new AlertDialog.Builder(getApplicationContext())
//                                .setTitle("Delete entry")
//                                .setMessage("You can't delete this Account head\n" +
//                                        "cause you have several transaction under this Account head")
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // continue with delete
//                        }
//                    })
//                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // do nothing
//                        }
//                    })
//                                .show();

                        AlertDialog alertDialog = new AlertDialog.Builder(
                                ChartOfAccountUpdate.this).create();

                        // Setting Dialog Title
                        alertDialog.setTitle("Delete caution");

                        // Setting Dialog Message
                        alertDialog.setMessage("You can't delete this Account head\n" +
                                       "cause you have several transaction under this Account head");

                        // Setting Icon to Dialog
                        //alertDialog.setIcon(R.drawable.tick);

                        // Setting OK Button
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog closed
                                finish();
                            }
                        });

                        // Showing Alert Message
                        alertDialog.show();
                    }
                }catch(Exception e){
                    System.out.print(e);
                }

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ChartOfAccountTable chartOfAccountTable = new ChartOfAccountTable();

                try {

                    //table.setTable1Pid(table1Pid);
                    //table.setTable1Pid_parent(Integer.parseInt(accountNameId[spnParent.getSelectedItemPosition()][0]));
                    table.setTable1AccountName(txtAccountName.getText().toString());
                    table.setTable1AccountId(Integer.parseInt(txtAccountId.getText().toString()));
                    table.setTable1AccountType(spnAccountType.getSelectedItem().toString());
                    //table.setTable1AccountGroup(spnAccountGroup.getSelectedItem().toString());
                    //table.setTable1Status(table1Status);
                    //Toast.makeText(getApplicationContext(),table.toString(),Toast.LENGTH_LONG).show();
                    crud.updateRow(table,table1AccountId);
                } catch (Exception e) {
                    System.out.print(e);
                }




                finish();

            }
        });



    }
}