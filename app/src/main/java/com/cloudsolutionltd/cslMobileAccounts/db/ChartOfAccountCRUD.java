package com.cloudsolutionltd.cslMobileAccounts.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Mamun on 8/12/15.
 */
public class ChartOfAccountCRUD extends DBHandler {
    SQLiteDatabase db;


    public ChartOfAccountCRUD(Context context) {
        super(context);
        //db = this.getWritableDatabase();
    }

    public long addInChartOfAccount(ChartOfAccountTable chartOfAccountTable) {

        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        //values.put(DBHandler.table1Pid,chartOfAccountTable.getTable1Pid());
        values.put(table1AccountName, chartOfAccountTable.getTable1AccountName());
        values.put(table1AccountId, chartOfAccountTable.getTable1AccountId());
        values.put(table1AccountType, chartOfAccountTable.getTable1AccountType());
        long result = db.insert(table1, null, values);

        db.close();

        return result;
    }

    public ArrayList<ChartOfAccountTable> getAll_ChartOfAccount() {
        ArrayList<ChartOfAccountTable> chartOfAccountList = new ArrayList<ChartOfAccountTable>();

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM chart_of_account ORDER BY account_id ASC", null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int pid = cursor.getInt(cursor.getColumnIndex(table1Pid));
                //int pidParent = cursor.getInt(cursor.getColumnIndex(table1Pid_parent));
                String accountName = cursor.getString(cursor.getColumnIndex(table1AccountName));
                int accountId = cursor.getInt(cursor.getColumnIndex(table1AccountId));
                String accountType = cursor.getString(cursor.getColumnIndex(table1AccountType));
                //String accountGroup = cursor.getString(cursor.getColumnIndex(table1AccountGroup));
                //String status = cursor.getString(cursor.getColumnIndex(table1Status));

                ChartOfAccountTable chartOfAccountTable = new ChartOfAccountTable(pid, accountName, accountId, accountType);
                chartOfAccountList.add(chartOfAccountTable);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return chartOfAccountList;
    }


    public String[][] getAccountName() {


        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select account_name,account_id from chart_of_account where account_group = ? order by account_name asc", new String[]{"Group"});


        String[][] accountName;


        if (cursor != null && cursor.getCount() > 0) {

            //int j = cursor.getCount();
            accountName = new String[cursor.getCount() + 1][2];
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                accountName[i][0] = cursor.getString(cursor.getColumnIndex("account_id"));
                accountName[i][1] = cursor.getString(cursor.getColumnIndex("account_name")) + " - " + cursor.getString(cursor.getColumnIndex("account_id"));
                cursor.moveToNext();
            }

            accountName[cursor.getCount()][1] = "None";
            accountName[cursor.getCount()][0] = "0";

        } else {
            accountName = new String[1][2];
            accountName[0][1] = "None";
            accountName[0][0] = "0";
        }


        cursor.close();
        db.close();
        return accountName;
    }


    public String[][] getTransactionalAccountName() {


        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select account_name,account_id from chart_of_account" +
                " order by account_id asc", null);


        String[][] accountName;


        if (cursor != null && cursor.getCount() > 0) {

            //int j = cursor.getCount();
            accountName = new String[cursor.getCount() + 1][2];
            accountName[0][1] = "";
            accountName[0][0] = "1";
            cursor.moveToFirst();
            for (int i = 1; i <= cursor.getCount(); i++) {
                accountName[i][0] = cursor.getString(cursor.getColumnIndex("account_id"));
                accountName[i][1] = cursor.getString(cursor.getColumnIndex("account_name"));
                cursor.moveToNext();
            }

        } else {
            accountName = new String[1][2];
            accountName[0][1] = "";
            accountName[0][0] = "1";
        }


        cursor.close();
        db.close();
        return accountName;
    }


    public int deleteRow(int pid, int account_id) {
        db = this.getWritableDatabase();
        int result = 0;

        Cursor cursor = db.rawQuery("select acc_from, acc_to from ledger_transaction where acc_from = ? or acc_to = ?",
                new String[]{String.valueOf(account_id), String.valueOf(account_id)});

        if (cursor.getCount() <= 0) {
            db.delete(table1, table1Pid + " = ?", new String[]{String.valueOf(pid)});
            result = 1;
            db.close();
            cursor.close();
            return result;
        } else {

            cursor.close();
            db.close();
            return result;
        }
    }


    public void updateRow(ChartOfAccountTable chartOfAccountTable, int previousAccountId) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int pid = chartOfAccountTable.getTable1Pid();
        //values.put(DBHandler.table1Pid,chartOfAccountTable.getTable1Pid());
        values.put(table1AccountName, chartOfAccountTable.getTable1AccountName());
        values.put(table1AccountId, chartOfAccountTable.getTable1AccountId());
        values.put(table1AccountType, chartOfAccountTable.getTable1AccountType());

        ContentValues values1 = new ContentValues();
        values1.put(table2AccFrom, chartOfAccountTable.getTable1AccountId());

        ContentValues values2 = new ContentValues();
        values2.put(table2AccTo, chartOfAccountTable.getTable1AccountId());
        try {
            int a = db.update(table1, values, table1Pid + " = ?", new String[]{String.valueOf(chartOfAccountTable.getTable1Pid())});
            int b = db.update(table2, values1, table2AccFrom + " = ?", new String[]{String.valueOf(previousAccountId)});
            int c = db.update(table2, values2, table2AccTo + " = ?", new String[]{String.valueOf(previousAccountId)});
            Log.i("Update Chart of Account", String.valueOf(a));
        } catch (Exception e) {
            Log.e("Update problem:", e.toString());
        }
        //Log.d("Mamun", "Affected row is " + String.valueOf(affectedRow));
        db.close();
    }


    public String[][] getAccountNameExcept(int pid) {


        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select account_name,account_id from chart_of_account" +
                " where account_group = ? and " + table1Pid + " <> ?", new String[]{"Group", String.valueOf(pid)});


        String[][] accountName;


        if (cursor != null && cursor.getCount() > 0) {

            //int j = cursor.getCount();
            accountName = new String[cursor.getCount() + 1][2];
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                accountName[i][0] = cursor.getString(cursor.getColumnIndex("account_id"));
                accountName[i][1] = cursor.getString(cursor.getColumnIndex("account_name")) + " - " + cursor.getString(cursor.getColumnIndex("account_id"));
                cursor.moveToNext();
            }

            accountName[cursor.getCount()][1] = "None";
            accountName[cursor.getCount()][0] = "0";

        } else {
            accountName = new String[1][1];
            accountName[0][1] = "None";
            accountName[0][0] = "0";
        }


        cursor.close();
        db.close();
        return accountName;
    }

    public String getMaxAccountId(String accountType) {
        SQLiteDatabase db = getReadableDatabase();
        String maxId = new String();
        Cursor cursor = db.rawQuery("select max(account_id)+1 as max_id " +
                "from chart_of_account " +
                "where account_type = ?", new String[]{accountType});

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            maxId = cursor.getString(0);
            return maxId;
        }

        return maxId;
    }

}
