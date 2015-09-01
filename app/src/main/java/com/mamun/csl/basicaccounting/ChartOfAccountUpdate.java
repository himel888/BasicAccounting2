package com.mamun.csl.basicaccounting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by csl on 8/31/15.
 */
public class ChartOfAccountUpdate extends AppCompatActivity {



    //Declare activity component
    Spinner spnParent,spnAccountType,spnAccountGroup;
    EditText txtAccountName,txtAccountId;
    CheckBox chkStatus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_of_account_update);


        //Register activity component
        spnParent = (Spinner) findViewById(R.id.spnParent);
        spnAccountType = (Spinner) findViewById(R.id.spnType);
        spnAccountGroup = (Spinner) findViewById(R.id.spnGroup);
        txtAccountName = (EditText) findViewById(R.id.txtAccountName);
        txtAccountId = (EditText) findViewById(R.id.txtAccountId);
        chkStatus = (CheckBox) findViewById(R.id.chkStatus);

        txtAccountName.setText("Mamun");
    }
}
