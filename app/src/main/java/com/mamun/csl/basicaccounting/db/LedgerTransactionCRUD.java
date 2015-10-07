package com.mamun.csl.basicaccounting.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Mamun on 8/12/15.
 */
public class LedgerTransactionCRUD extends DBHandler {
    public LedgerTransactionCRUD(Context context) {
        super(context);
    }

    SQLiteDatabase db;



    //Get Maximum pid pair
    public int getMaxPidPair(){
        db = this.getReadableDatabase();
        int pidPair = 1;
        try {
            Cursor cursor = db.rawQuery("select max(pid_pair)+1 p from ledger_transaction",null);
            if (cursor != null && cursor.getCount()>0){
                cursor.moveToPosition(0);
                pidPair = cursor.getInt(0);
            }else{

                pidPair = 1;
            }
            db.close();
            cursor.close();
        }catch (Exception e){
            System.out.print(e);
        }
        return pidPair;
    }



    //Insert into Ledger transaction table
    public void insert(LedgerTransactionTable table){

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(DBHandler.table1Pid,chartOfAccountTable.getTable1Pid());
        values.put(table2PidPair, table.getTable2PidPair());
        values.put(table2TransactionDate, table.getTable2TransactionDate());
        values.put(table2AccFrom, table.getTable2AccFrom());
        values.put(table2AccTo, table.getTable2AccTo());
        values.put(table2AmountDr, 0);
        values.put(table2AmountCr, table.getTable2AmountCr());
        values.put(table2RefBill, table.getTable2RefBill());
        values.put(table2Description, table.getTable2Description());
        values.put(table2BankCheque, table.getTable2BankCheque());
        values.put(table2Transaction_type, table.getTable2Transaction_type());
        values.put(table2Status, table.getTable2Status());
        db.insert(table2, null, values);

        values.put(table2PidPair, table.getTable2PidPair());
        values.put(table2TransactionDate, table.getTable2TransactionDate());
        values.put(table2AccFrom, table.getTable2AccTo());
        values.put(table2AccTo, table.getTable2AccFrom());
        values.put(table2AmountDr,table.getTable2AmountDr() );
        values.put(table2AmountCr, 0);
        values.put(table2RefBill, table.getTable2RefBill());
        values.put(table2Description, table.getTable2Description());
        values.put(table2BankCheque, table.getTable2BankCheque());
        values.put(table2Transaction_type, table.getTable2Transaction_type());
        values.put(table2Status, table.getTable2Status());
        db.insert(table2, null, values);
        db.close();

    }




    //Get Previous Balance
    public int getPreviousBalance(String fromDate, int acc_from){


        int previousBalance = 0;
        db = this.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery("select sum(amount_dr) - sum(amount_cr) as previousBalance "+
                    "from ledger_transaction "+
                    "where transaction_date < ? and acc_from = ?",new String[]{fromDate,String.valueOf(acc_from)});

            if (cursor != null && cursor.getCount() > 0){
                cursor.moveToPosition(0);
                previousBalance = cursor.getInt(0);
            }
        }catch (Exception e){
            System.out.print(e);
        }


        return previousBalance;
    }


}
