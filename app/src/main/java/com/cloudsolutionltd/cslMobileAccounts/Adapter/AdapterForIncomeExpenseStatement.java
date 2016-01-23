package com.cloudsolutionltd.cslMobileAccounts.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudsolutionltd.cslMobileAccounts.R;
import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionCRUD;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by csl on 10/17/15.
 */
public class AdapterForIncomeExpenseStatement extends ArrayAdapter<String[]> {
    Activity con;
    ArrayList<String[]> statementList;
    LedgerTransactionCRUD ledgerTransactionCRUD = new LedgerTransactionCRUD(this.getContext());
    NumberFormat numberFormat;

    public AdapterForIncomeExpenseStatement(Context context, ArrayList<String[]> statementList) {

        super(context, R.layout.income_expense_statement_item_view, statementList);

        con = (Activity) context;
        this.statementList = statementList;
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(2);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;

        if (convertView == null) {
            //Generate view


            LayoutInflater inflater = con.getLayoutInflater();
            v = inflater.inflate(R.layout.income_expense_statement_item_view, null);


        } else {
            v = convertView;
        }

        TextView txtAccountHead = (TextView) v.findViewById(R.id.txtAccountHead);
        TextView txtAmount = (TextView) v.findViewById(R.id.txtAmount);
        LinearLayout row = (LinearLayout) v.findViewById(R.id.row);


        try {
            String statementRow[] = statementList.get(position);
            if (position % 2 == 0)
                row.setBackgroundColor(Color.parseColor("#FFEAF4FE"));
            else
                row.setBackgroundColor(Color.parseColor("#FFFFFF"));
            txtAccountHead.setText(statementRow[0]);
//            if (statement.getDouble(statement.getColumnIndex("balance")) < 0)
//                txtAmount.setText(String.valueOf(numberFormat.format(statement.getDouble(statement.getColumnIndex("balance")) * -1)));
//            else
            txtAmount.setText(numberFormat.format(Double.valueOf(statementRow[1])));


        } catch (Exception e) {
            Log.e("Error in Adapter", e.toString());
        }


        return v;
    }
}
