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

import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionCRUD;
import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionTable;
import com.cloudsolutionltd.cslMobileAccounts.FormatDate;
import com.cloudsolutionltd.cslMobileAccounts.R;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by csl on 10/11/15.
 */
public class ArrayAdapterForAllTransaction extends ArrayAdapter<LedgerTransactionTable> {

    Activity con;
    ArrayList<LedgerTransactionTable> voucherEntryList;
    LedgerTransactionCRUD ledgerTransactionCRUD = new LedgerTransactionCRUD(this.getContext());
    NumberFormat numberFormat;

    public ArrayAdapterForAllTransaction(Context context, ArrayList<LedgerTransactionTable> voucherEntryList) {

        super(context, R.layout.all_transaction_list_item, voucherEntryList);

        con = (Activity) context;
        this.voucherEntryList = voucherEntryList;
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumFractionDigits(2);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {

            //Generate view
            LayoutInflater inflater = con.getLayoutInflater();
            v = inflater.inflate(R.layout.all_transaction_list_item, null);

        } else {
            v = convertView;
        }

        viewHolder.txtAccountToName = (TextView) v.findViewById(R.id.txtAccountToName);
        viewHolder.txtTransactionDate = (TextView) v.findViewById(R.id.txtTransactionDate);
        viewHolder.txtAmount = (TextView) v.findViewById(R.id.txtAmount);
        viewHolder.row = (LinearLayout) v.findViewById(R.id.row);

        LedgerTransactionTable c = voucherEntryList.get(position);
        try {
            if (position%2 == 0)
                viewHolder.row.setBackgroundColor(Color.parseColor("#FFEAF4FE"));
            else
                viewHolder.row.setBackgroundColor(Color.parseColor("#FFFFFF"));
            viewHolder.txtAccountToName.setText(ledgerTransactionCRUD.getAccountName(c.getTable2AccFrom())
                    + "\n\tTo\n" + ledgerTransactionCRUD.getAccountName(c.getTable2AccTo()));
            viewHolder.txtTransactionDate.setText(FormatDate.getDateToShow(c.getTable2TransactionDate()));
            if (c.getTable2AmountDr() > 0) {
                viewHolder.txtAmount.setText(String.valueOf(numberFormat.format(c.getTable2AmountDr())));
            } else
                viewHolder.txtAmount.setText(String.valueOf(numberFormat.format(c.getTable2AmountCr())));


        } catch (Exception e) {
            Log.e("Error in Adapter", e.toString());
        }
        return v;
    }

    private static class ViewHolder{
        TextView txtAccountToName;
        TextView txtTransactionDate;
        TextView txtAmount;
        LinearLayout row;

    }
}
