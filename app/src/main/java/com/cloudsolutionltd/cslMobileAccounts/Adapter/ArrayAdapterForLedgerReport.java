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

import com.cloudsolutionltd.cslMobileAccounts.Activity.LedgerReport;
import com.cloudsolutionltd.cslMobileAccounts.FormatDate;
import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionCRUD;
import com.cloudsolutionltd.cslMobileAccounts.db.LedgerTransactionTable;
import com.cloudsolutionltd.cslMobileAccounts.R;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by csl on 10/7/15.
 */
public class ArrayAdapterForLedgerReport extends ArrayAdapter<LedgerTransactionTable> {

    Activity con;
    ArrayList<LedgerTransactionTable> voucherEntryList;
    LedgerReport ledgerReport = new LedgerReport();
    LedgerTransactionCRUD ledgerTransactionCRUD = new LedgerTransactionCRUD(this.getContext());
    NumberFormat numberFormat;
    double[] currentBalance;

    public ArrayAdapterForLedgerReport(Context context, ArrayList<LedgerTransactionTable> voucherEntryList, double[] currentBalance) {

        super(context, R.layout.ledger_report_list_item , voucherEntryList);

        con = (Activity) context;
        this.voucherEntryList = voucherEntryList;
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumFractionDigits(2);
        this.currentBalance = currentBalance;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if(convertView == null){
            //Generate view
            LayoutInflater inflater = con.getLayoutInflater();
            convertView = inflater.inflate(R.layout.ledger_report_list_item,null);
            viewHolder.txtDateAndName = (TextView) convertView.findViewById(R.id.txtDateAndName);
            viewHolder.txtAmountDR = (TextView) convertView.findViewById(R.id.txtAmountDR);
            viewHolder.txtAmountCR = (TextView) convertView.findViewById(R.id.txtAmountCR);
            viewHolder.txtBalance = (TextView) convertView.findViewById(R.id.txtBalance);
            viewHolder.row = (LinearLayout) convertView.findViewById(R.id.row);

        }else{
            return convertView;
        }

        LedgerTransactionTable c = voucherEntryList.get(position);

        try {
            if (position%2 == 0){
                viewHolder.row.setBackgroundColor(Color.parseColor("#FFEAF4FE"));
            }else
                viewHolder.row.setBackgroundColor(Color.parseColor("#FFFFFF"));
            viewHolder.txtDateAndName.setText(FormatDate.getDateToShow(c.getTable2TransactionDate()) +"\n" + ledgerTransactionCRUD.getAccountName(c.getTable2AccTo()));
            viewHolder.txtAmountDR.setText(String.valueOf(numberFormat.format(c.getTable2AmountDr())));
            viewHolder.txtAmountCR.setText(String.valueOf(numberFormat.format(c.getTable2AmountCr())));
            viewHolder.txtBalance.setText(String.valueOf(numberFormat.format(currentBalance[position])));
                /*if (c.getTable2AmountDr() > 0){
                    txtAmountDR.setText(c.getTable2AmountDr() + "Dr");
                    previousBalance = previousBalance + c.getTable2AmountDr();
                    txtBalance.setText(String.valueOf(previousBalance));
                    ledgerReport.setPreviousBalance(previousBalance);
                }else{
                    txtAmountCR.setText(c.getTable2AmountCr() + "Cr");
                    previousBalance = previousBalance - c.getTable2AmountCr();
                    txtBalance.setText(String.valueOf(previousBalance));
                    ledgerReport.setPreviousBalance(previousBalance);

                }*/
        }catch (Exception e){
            Log.e("Error in Adapter",e.toString());
        }



        return convertView;
    }

    private class ViewHolder{
        TextView txtDateAndName;
        TextView txtAmountDR;
        TextView txtAmountCR;
        TextView txtBalance;
        LinearLayout row;
    }
}
