package com.mamun.csl.basicaccounting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mamun.csl.basicaccounting.db.ChartOfAccountCRUD;
import com.mamun.csl.basicaccounting.db.ChartOfAccountTable;

/**
 * Created by csl on 8/31/15.
 */
public class ChartOfAccountUpdate extends AppCompatActivity {


    //Declare activity component
    Spinner spnParent, spnAccountType, spnAccountGroup;
    EditText txtAccountName, txtAccountId;
    CheckBox chkStatus;
    Button btnUpdate, btnDelete;

    //Data Source for Account Name, Account Type and Account Group Spinner
    private static String[] type = {"Asset", "Expense", "Income", "Liability"};
    private static String[] group = {"Group", "Transactional"};
    String[][] accountNameId;
    String[] accountName;

    //Declare adapter
    private ArrayAdapter<String> accountTypeAdapter, accountGroupAdapter;
    ArrayAdapter<String> parentAdapter;

    //Declare variable
    int defaultSpnParentName;
    private int table1Pid;
    private int table1Pid_parent;
    private String table1AccountName;
    private int table1AccountId;
    private String table1AccountType;
    private String table1AccountGroup;
    private String table1Status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_of_account_update);


        final ChartOfAccountCRUD crud = new ChartOfAccountCRUD(this);


        //Register activity component
        spnParent = (Spinner) findViewById(R.id.spnParent);
        spnAccountType = (Spinner) findViewById(R.id.spnType);
        spnAccountGroup = (Spinner) findViewById(R.id.spnGroup);
        txtAccountName = (EditText) findViewById(R.id.txtAccountName);
        txtAccountId = (EditText) findViewById(R.id.txtAccountId);
        chkStatus = (CheckBox) findViewById(R.id.chkStatus);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);


        final ChartOfAccountTable table = (ChartOfAccountTable) getIntent().getSerializableExtra("table");
        table1Pid = table.getTable1Pid();
        table1Pid_parent = table.getTable1Pid_parent();
        table1AccountName = table.getTable1AccountName();
        table1AccountId = table.getTable1AccountId();
        table1AccountType = table.getTable1AccountType();
        table1AccountGroup = table.getTable1AccountGroup();
        table1Status = table.getTable1Status();

        //Toast.makeText(getApplicationContext(),table1AccountName,Toast.LENGTH_LONG).show();

        try {
            txtAccountName.setText(table1AccountName);
            txtAccountId.setText(String.valueOf(table1AccountId));
        } catch (Exception e) {
            System.out.println(e);
        }

        if (table1Status.equals("Active")) {
            chkStatus.setChecked(true);
        } else {
            chkStatus.setChecked(false);
        }


        try {
            accountNameId = crud.getAccountNameExcept(table1Pid);
            accountName = new String[accountNameId.length];
            for (int i = 0; i < accountNameId.length; i++) {
                accountName[i] = accountNameId[i][1];
                if (table1Pid_parent == Integer.parseInt(accountNameId[i][0]))
                    defaultSpnParentName = i;
            }
            parentAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, accountName);

            try {
                spnParent.setAdapter(parentAdapter);
                spnParent.setSelection(defaultSpnParentName);
            } catch (Exception e) {
                System.out.print(e);
            }


        } catch (Exception e) {
            System.out.println(e);
        }

        accountTypeAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, type);
        spnAccountType.setAdapter(accountTypeAdapter);

        if (table1AccountType.equals("Asset")) {
            spnAccountType.setSelection(0);
        } else if (table1AccountType.equals("Expense")) {
            spnAccountType.setSelection(1);
        } else if (table1AccountType.equals("Income")) {
            spnAccountType.setSelection(2);
        } else
            spnAccountType.setSelection(3);


        accountGroupAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, group);
        spnAccountGroup.setAdapter(accountGroupAdapter);

        if (table1AccountGroup.equals("Group")) {
            spnAccountGroup.setSelection(0);
        } else
            spnAccountGroup.setSelection(1);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    crud.deleteRow(table1Pid);
                }catch(Exception e){
                    System.out.print(e);
                }


                Intent intent = new Intent(getApplicationContext(),ChartOfAccount.class);
                startActivity(intent);

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ChartOfAccountTable chartOfAccountTable = new ChartOfAccountTable();

                try {

                    if (chkStatus.isChecked()) {
                        table1Status = "Active";
                    } else
                        table1Status = "Inactive";

                    table.setTable1Pid(table1Pid);
                    table.setTable1Pid_parent(Integer.parseInt(accountNameId[spnParent.getSelectedItemPosition()][0]));
                    table.setTable1AccountName(txtAccountName.getText().toString());
                    table.setTable1AccountId(Integer.parseInt(txtAccountId.getText().toString()));
                    table.setTable1AccountType(spnAccountType.getSelectedItem().toString());
                    table.setTable1AccountGroup(spnAccountGroup.getSelectedItem().toString());
                    table.setTable1Status(table1Status);
                    //Toast.makeText(getApplicationContext(),table.toString(),Toast.LENGTH_LONG).show();
                    crud.updateRow(table);
                } catch (Exception e) {
                    System.out.print(e);
                }




                Intent intent = new Intent(getApplicationContext(), ChartOfAccount.class);
                startActivity(intent);

            }
        });



    }
}