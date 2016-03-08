package com.cloudsolutionltd.cslMobileAccounts.db;

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
    public long insert(LedgerTransactionTable table){

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
        //values.put(table2Status, table.getTable2Status());
        long result1 = db.insert(table2, null, values);


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
        //values.put(table2Status, table.getTable2Status());
        long result2 = db.insert(table2, null, values);

        db.close();

        if (result1 >=0 && result2 >= 0 )
            return 1;
        else
            return -1;
    }




    //Get Previous Balance
    public Double getPreviousBalance(String fromDate, int acc_from){


        Double previousBalance = 0.0;
        db = this.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery("select sum(amount_dr) - sum(amount_cr) as previousBalance "+
                    "from ledger_transaction "+
                    "where transaction_date < ? and acc_from = ?",new String[]{fromDate,String.valueOf(acc_from)});

            if (cursor != null && cursor.getCount() > 0){
                cursor.moveToPosition(0);
                previousBalance = cursor.getDouble(0);
            }
        }catch (Exception e){
            System.out.print(e);
        }


        return previousBalance;
    }


    public ArrayList<LedgerTransactionTable> getVoucherEntryForSpecificAccount(int accountFrom, String fromDate, String toDate){
        ArrayList<LedgerTransactionTable> voucherEntryList = new ArrayList<LedgerTransactionTable>();
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ledger_transaction where acc_from = ? and transaction_date between ? and ?" +
                " ORDER BY transaction_date ASC", new String[]{String.valueOf(accountFrom),fromDate,toDate});

        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i = 0; i<cursor.getCount(); i++){

                 int pid = cursor.getInt(cursor.getColumnIndex(table2Pid));
                 int pidPair = cursor.getInt(cursor.getColumnIndex(table2PidPair));
                 String transactionDate = cursor.getString(cursor.getColumnIndex(table2TransactionDate));
                 int accFrom = cursor.getInt(cursor.getColumnIndex(table2AccFrom));
                 int accTo = cursor.getInt(cursor.getColumnIndex(table2AccTo));
                 Double amountDr = cursor.getDouble(cursor.getColumnIndex(table2AmountDr));
                 Double amountCr = cursor.getDouble(cursor.getColumnIndex(table2AmountCr));
                 String refBill = cursor.getString(cursor.getColumnIndex(table2RefBill));
                 String description = cursor.getString(cursor.getColumnIndex(table2Description));
                 String bankCheque = cursor.getString(cursor.getColumnIndex(table2BankCheque));
                 String transaction_type = cursor.getString(cursor.getColumnIndex(table2Transaction_type));
                 //String status = cursor.getString(cursor.getColumnIndex(table2Status));

                LedgerTransactionTable ledgerTransactionTable = new LedgerTransactionTable(pid, pidPair, transactionDate,
                        accFrom, accTo,
                        amountDr, amountCr, refBill, description, bankCheque, transaction_type);

                voucherEntryList.add(ledgerTransactionTable);
                cursor.moveToNext();
            }
        }else
           System.out.print("Cursor is empty");

        cursor.close();
        db.close();
        return voucherEntryList;
    }

    public ArrayList<LedgerTransactionTable> getAllVoucherEntry(String fromDate, String toDate){
        ArrayList<LedgerTransactionTable> voucherEntryList = new ArrayList<LedgerTransactionTable>();
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ledger_transaction where transaction_date between ?" +
                        " and ? and amount_dr = ? order by transaction_date desc,p_id desc",
                new String[]{fromDate,toDate, String.valueOf(0)});

        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i = 0; i<cursor.getCount(); i++){



                int pid = cursor.getInt(cursor.getColumnIndex(table2Pid));
                int pidPair = cursor.getInt(cursor.getColumnIndex(table2PidPair));
                String transactionDate = cursor.getString(cursor.getColumnIndex(table2TransactionDate));
                int accFrom = cursor.getInt(cursor.getColumnIndex(table2AccFrom));
                int accTo = cursor.getInt(cursor.getColumnIndex(table2AccTo));
                Double amountDr = cursor.getDouble(cursor.getColumnIndex(table2AmountDr));
                Double amountCr = cursor.getDouble(cursor.getColumnIndex(table2AmountCr));
                String refBill = cursor.getString(cursor.getColumnIndex(table2RefBill));
                String description = cursor.getString(cursor.getColumnIndex(table2Description));
                String bankCheque = cursor.getString(cursor.getColumnIndex(table2BankCheque));
                String transaction_type = cursor.getString(cursor.getColumnIndex(table2Transaction_type));
                //String status = cursor.getString(cursor.getColumnIndex(table2Status));

                LedgerTransactionTable ledgerTransactionTable = new LedgerTransactionTable(pid, pidPair, transactionDate,
                        accFrom, accTo,
                        amountDr, amountCr, refBill, description, bankCheque, transaction_type);

                voucherEntryList.add(ledgerTransactionTable);
                cursor.moveToNext();
            }
        }else

            System.out.print("Cursor is empty");

        cursor.close();
        db.close();
        return voucherEntryList;
    }

    public String getAccountName(int accountId){
        db = this.getReadableDatabase();
        String accountName;
        Cursor cursor = db.rawQuery("select account_name from chart_of_account where account_id = ?",
                new String[]{String.valueOf(accountId)});
        if (cursor != null && cursor.getCount()>0){
            cursor.moveToPosition(0);
            accountName = cursor.getString(0);
            cursor.close();
            db.close();
            return accountName;
        }

        cursor.close();
        db.close();
        return null;
    }

    public int deletePairedRow(int pidPair){
        db = this.getWritableDatabase();

        int a = db.delete(table2, table2PidPair + " = ?", new String[]{String.valueOf(pidPair)});
        db.close();
        return a;

    }


    public int updatePairedRow(LedgerTransactionTable table){

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
        //values.put(table2Status, table.getTable2Status());
        int a = db.update(table2, values, table2PidPair + " = ? and " + table2Pid +
        " = ?",new String[]{String.valueOf(table.getTable2PidPair()), String.valueOf(table.getTable2Pid())});

        values.put(table2PidPair, table.getTable2PidPair());
        values.put(table2TransactionDate, table.getTable2TransactionDate());
        values.put(table2AccFrom, table.getTable2AccTo());
        values.put(table2AccTo, table.getTable2AccFrom());
        values.put(table2AmountDr,table.getTable2AmountCr() );
        values.put(table2AmountCr, 0);
        values.put(table2RefBill, table.getTable2RefBill());
        values.put(table2Description, table.getTable2Description());
        values.put(table2BankCheque, table.getTable2BankCheque());
        values.put(table2Transaction_type, table.getTable2Transaction_type());
        //values.put(table2Status, table.getTable2Status());
        int b = db.update(table2, values, table2PidPair + " = ? and " + table2Pid +
                " = ?",new String[]{String.valueOf(table.getTable2PidPair()), String.valueOf(table.getTable2Pid()+1)});
        db.close();

        return a+b;
    }


    public ArrayList getAccountStatement(String fromDate, String toDate, String accountType){

        db = this.getReadableDatabase();
        ArrayList<String[]> report = new ArrayList<>();

//        Cursor cursor = db.rawQuery("select a.account_name,sum(b.amount_dr) - sum(b.amount_cr) as balance " +
//                "from chart_of_account a, ledger_transaction b " +
//                "where b.transaction_date between ? and ?  and a.account_type = ? " +
//                "and a.account_id = b.acc_from " +
//                "group by a.account_name", new String[]{fromDate, toDate, accountType});


        Cursor cursor = db.rawQuery("select account_name , balance from (select * from chart_of_account where account_type = ?) " +
                "left  join (" +
                "select a.account_name,sum(b.amount_dr) - sum(b.amount_cr) as balance " +
                "from chart_of_account a, ledger_transaction b " +
                "where b.transaction_date between ? and ?  and a.account_type = ? " +
                "and a.account_id = b.acc_from " +
                "group by a.account_name) " +
                "using(account_name) " +
                "order by balance desc", new String[]{accountType, fromDate, toDate, accountType});

        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();


            do {
                String statementRow[] = new String[2];
                statementRow[0] = cursor.getString(cursor.getColumnIndex("account_name"));
                statementRow[1] = String.valueOf(cursor.getDouble(cursor.getColumnIndex("balance")));
                report.add(statementRow);
            }
            while (cursor.moveToNext());
            cursor.close();
            db.close();
            return report;
        }

        cursor.close();
        db.close();
        return report;

    }

    public ArrayList getAccountStatement(String toDate, String accountType){

        db = this.getReadableDatabase();
        ArrayList<String[]> report = new ArrayList<>();

//        Cursor cursor = db.rawQuery("select a.account_name,sum(b.amount_dr) - sum(b.amount_cr) as balance " +
//                "from chart_of_account a, ledger_transaction b " +
//                "where b.transaction_date between ? and ?  and a.account_type = ? " +
//                "and a.account_id = b.acc_from " +
//                "group by a.account_name", new String[]{fromDate, toDate, accountType});


        Cursor cursor = db.rawQuery("select account_name , balance from (select * from chart_of_account where account_type = ?) " +
                "left  join (" +
                "select a.account_name,sum(b.amount_dr) - sum(b.amount_cr) as balance " +
                "from chart_of_account a, ledger_transaction b " +
                "where b.transaction_date <= ?  and a.account_type = ? " +
                "and a.account_id = b.acc_from " +
                "group by a.account_name) " +
                "using(account_name) " +
                "order by balance desc", new String[]{accountType, toDate, accountType});

        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();


            do {
                String statementRow[] = new String[2];
                statementRow[0] = cursor.getString(cursor.getColumnIndex("account_name"));
                statementRow[1] = String.valueOf(cursor.getDouble(cursor.getColumnIndex("balance")));
                report.add(statementRow);
            }
            while (cursor.moveToNext());
            cursor.close();
            db.close();
            return report;
        }

        cursor.close();
        db.close();
        return report;

    }


    /*select acc_from, sum(amount_dr) - sum(amount_cr) as balance
from ledger_transaction
where transaction_date between '2014/06/12' and '2015/06/13'
and acc_from in (2,5)*/

}
