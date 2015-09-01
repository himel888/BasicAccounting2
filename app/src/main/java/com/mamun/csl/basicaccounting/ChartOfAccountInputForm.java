package com.mamun.csl.basicaccounting;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mamun.csl.basicaccounting.db.ChartOfAccountCRUD;
import com.mamun.csl.basicaccounting.db.ChartOfAccountTable;

public class ChartOfAccountInputForm extends AppCompatActivity {


    //Data Source for Account Name, Account Type and Account Group Spinner
    private static String[] type = {"Asset", "Expense", "Income", "Liability"};
    private static String[] group = {"Group", "Transactional"};
    public static String[][] accountNameId;
    public static String[] accountName;
    public String status;
    public static int pidParent;




    //View object for Account Name, Account Type and Account Group
    private Spinner spntype,spnGroup,spnParent;


    //Adapter for Account type and Account Group
    private ArrayAdapter<String> accountTypeAdapter, accountGroupAdapter;
    private ArrayAdapter<String> parentAdapter;
    Button btnSave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_of_account_input_form);


        ChartOfAccountCRUD crud = new ChartOfAccountCRUD(this);



        //final EditText txtPid = (EditText) findViewById(R.id.txtPid);
        final EditText txtAccountName = (EditText) findViewById(R.id.txtAccountName);
        final EditText txtAccountId = (EditText) findViewById(R.id.txtAccountId);
        final CheckBox chkStatus = (CheckBox) findViewById(R.id.chkStatus);
        btnSave = (Button) findViewById(R.id.btnSave);
        spnParent = (Spinner) findViewById(R.id.spnParant);
        spntype = (Spinner) findViewById(R.id.spnType);
        spnGroup = (Spinner) findViewById(R.id.spnGroup);
        try {
            accountNameId = crud.getAccountName();
            accountName = new String[accountNameId.length];
            for (int i =0; i < accountNameId.length; i++){
                accountName[i] = accountNameId[i][1];
            }
            parentAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,accountName);
            spnParent.setAdapter(parentAdapter);
        }catch (Exception e){
            System.out.println(e);
        }




        accountTypeAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,type);
        accountGroupAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,group);


        spntype.setAdapter(accountTypeAdapter);
        spnGroup.setAdapter(accountGroupAdapter);







        //Listener for Save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartOfAccountTable abc = new ChartOfAccountTable();
                ChartOfAccountCRUD crud = new ChartOfAccountCRUD(getApplicationContext());

                if (chkStatus.isChecked()) {
                    status = "A";
                } else
                    status = "I";


                if (spnParent.getSelectedItem().toString().equals("None")) {
                    pidParent = 0;
                } else {
                    pidParent = Integer.parseInt(accountNameId[spnParent.getSelectedItemPosition()][0]);
                }



                try {
                    //abc.setTable1Pid(5);
                    abc.setTable1Pid_parent(pidParent);
                    abc.setTable1AccountName(txtAccountName.getText().toString());
                    abc.setTable1AccountId(Integer.parseInt(txtAccountId.getText().toString()));
                    abc.setTable1AccountType(spntype.getSelectedItem().toString());
                    abc.setTable1AccountGroup(spnGroup.getSelectedItem().toString());
                    abc.setTable1Status(status);
                    Toast.makeText(getApplicationContext(), abc.toString(), Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                }


                crud.addInChartOfAccount(abc);

                Intent intent = new Intent(getApplicationContext(), ChartOfAccount.class);
                startActivity(intent);


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chart_of_account_input_form, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
