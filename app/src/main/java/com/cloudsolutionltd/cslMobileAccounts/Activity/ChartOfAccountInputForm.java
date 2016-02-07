package com.cloudsolutionltd.cslMobileAccounts.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cloudsolutionltd.cslMobileAccounts.db.ChartOfAccountCRUD;
import com.cloudsolutionltd.cslMobileAccounts.db.ChartOfAccountTable;
import com.cloudsolutionltd.cslMobileAccounts.R;

public class ChartOfAccountInputForm extends AppCompatActivity {


    //Data Source for Account Name, Account Type and Account Group Spinner
    private static String[] type = {"Asset", "Liability", "Income", "Expense"};
    //private static String[] group = {"Group", "Transactional"};
    public static String[][] accountNameId;
    //public static String[] accountName;
    //public String status;
    public static int pidParent;




    //View object for Account Name, Account Type and Account Group
    private Spinner spntype;
    private Spinner spnGroup,spnParent;


    //Adapter for Account type and Account Group
    private ArrayAdapter<String> accountTypeAdapter, accountGroupAdapter;
    private ArrayAdapter<String> parentAdapter;
    Button btnSave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_of_account_input_form);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4A97DF")));

        final ChartOfAccountCRUD crud = new ChartOfAccountCRUD(this);



        //final EditText txtPid = (EditText) findViewById(R.id.txtPid);
        final EditText txtAccountName = (EditText) findViewById(R.id.txtAccountName);
        final EditText txtAccountId = (EditText) findViewById(R.id.txtAccountId);
        btnSave = (Button) findViewById(R.id.btnSave);
       // spnParent = (Spinner) findViewById(R.id.spnParant);
        spntype = (Spinner) findViewById(R.id.spnType);
//        spnGroup = (Spinner) findViewById(R.id.spnGroup);
//        try {
//            accountNameId = crud.getAccountName();
//            accountName = new String[accountNameId.length];
//            for (int i =0; i < accountNameId.length; i++){
//                accountName[i] = accountNameId[i][1];
//            }
//            parentAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,accountName);
//            spnParent.setAdapter(parentAdapter);
//        }catch (Exception e){
//            System.out.println(e);
//        }




        accountTypeAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_row_add_account_head,type);
        //accountGroupAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,group);


        spntype.setAdapter(accountTypeAdapter);
        //spnGroup.setAdapter(accountGroupAdapter);

        spntype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String maxId = crud.getMaxAccountId(type[position]);
                if (maxId != null)
                    txtAccountId.setText(maxId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        //Listener for Save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartOfAccountTable abc = new ChartOfAccountTable();
                ChartOfAccountCRUD crud = new ChartOfAccountCRUD(getApplicationContext());

//
//                if (spnParent.getSelectedItem().toString().equals("None")) {
//                    pidParent = 0;
//                } else {
//                    pidParent = Integer.parseInt(accountNameId[spnParent.getSelectedItemPosition()][0]);
//                }



                try {
                    //abc.setTable1Pid(5);
                   // abc.setTable1Pid_parent(pidParent);
                    abc.setTable1AccountName(txtAccountName.getText().toString());
                    abc.setTable1AccountId(Integer.parseInt(txtAccountId.getText().toString()));
                    abc.setTable1AccountType(spntype.getSelectedItem().toString());
                    //abc.setTable1AccountGroup(spnGroup.getSelectedItem().toString());
                    //abc.setTable1Status(status);

                } catch (Exception e) {
                }


                if(abc.getTable1AccountName().length() == 0){
                    txtAccountName.setError("Write Account name");

                }else if (abc.getTable1AccountId() == 0){
                    txtAccountId.setError("Give Account Id");

                }else {
                    long result = crud.addInChartOfAccount(abc);
                    if (result > 0){

                        Toast.makeText(getApplicationContext(),"Account head created",Toast.LENGTH_SHORT).show();
                        finish();
                        //txtAccountName.setText("");
                        //txtAccountId.setText("");

                    }

                }


                // finish();
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
