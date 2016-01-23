package com.cloudsolutionltd.cslMobileAccounts.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudsolutionltd.cslMobileAccounts.db.ChartOfAccountTable;
import com.cloudsolutionltd.cslMobileAccounts.R;

import java.util.ArrayList;

/**
 * Created by Mamun on 8/24/15.
 */
public class  CustomizedArrayAdapter extends ArrayAdapter<ChartOfAccountTable> {


    Activity con;
    ArrayList<ChartOfAccountTable> chartOfAccountList;
    public CustomizedArrayAdapter(Context context,  ArrayList<ChartOfAccountTable> chartOfAccountList) {
        super(context, R.layout.chart_of_account_list_item , chartOfAccountList);

        con = (Activity) context;
        this.chartOfAccountList =chartOfAccountList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;

        if(convertView == null){
            //Generate view

            LayoutInflater inflater = con.getLayoutInflater();
            v = inflater.inflate(R.layout.chart_of_account_list_item,null);


        }else{
            v = convertView;
        }
        if (position%2 == 0){
            LinearLayout row = (LinearLayout) v.findViewById(R.id.row);
            row.setBackgroundColor(Color.parseColor("#FFEAF4FE"));
        }else{
            LinearLayout row = (LinearLayout) v.findViewById(R.id.row);
            row.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        TextView txtName = (TextView) v.findViewById(R.id.txtName);
        TextView txtType = (TextView) v.findViewById(R.id.txtType);
        TextView txtStatus = (TextView) v.findViewById(R.id.txtId);

        ChartOfAccountTable c = chartOfAccountList.get(position);
        txtName.setText(c.getTable1AccountName());
        txtType.setText(c.getTable1AccountType());
        txtStatus.setText(String.valueOf(c.getTable1AccountId()));
        return v;
    }


}
