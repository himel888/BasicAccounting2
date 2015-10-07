package com.mamun.csl.basicaccounting.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.mamun.csl.basicaccounting.ChartOfAccountUpdate;

import java.util.ArrayList;

/**
 * Created by Mamun on 8/12/15.
 */
public class ChartOfAccountCRUD extends DBHandler {
    SQLiteDatabase db;


    public ChartOfAccountCRUD(Context context) {
        super(context);
    }

    public void addInChartOfAccount(ChartOfAccountTable chartOfAccountTable){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(DBHandler.table1Pid,chartOfAccountTable.getTable1Pid());
        values.put(DBHandler.table1Pid_parent,chartOfAccountTable.getTable1Pid_parent());
        values.put(DBHandler.table1AccountName,chartOfAccountTable.getTable1AccountName());
        values.put(DBHandler.table1AccountId,chartOfAccountTable.getTable1AccountId());
        values.put(DBHandler.table1AccountType,chartOfAccountTable.getTable1AccountType());
        values.put(DBHandler.table1AccountGroup,chartOfAccountTable.getTable1AccountGroup());
        values.put(DBHandler.table1Status,chartOfAccountTable.getTable1Status());
        db.insert(DBHandler.table1, null, values);

        db.close();
    }

    public ArrayList<ChartOfAccountTable> getAll_ChartOfAccount(){
        ArrayList<ChartOfAccountTable> chartOfAccountList = new ArrayList<ChartOfAccountTable>();
        db = this.getReadableDatabase();
        Cursor cursor = db.query(table1, null, null, null, null, null,
                null);

        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i = 0; i<cursor.getCount(); i++){
                int pid = cursor.getInt(cursor.getColumnIndex(table1Pid));
                int pidParent = cursor.getInt(cursor.getColumnIndex(table1Pid_parent));
                String accountName = cursor.getString(cursor.getColumnIndex(table1AccountName));
                int accountId = cursor.getInt(cursor.getColumnIndex(table1AccountId));
                String accountType = cursor.getString(cursor.getColumnIndex(table1AccountType));
                String accountGroup = cursor.getString(cursor.getColumnIndex(table1AccountGroup));
                String status = cursor.getString(cursor.getColumnIndex(table1Status));

                ChartOfAccountTable chartOfAccountTable = new ChartOfAccountTable(pid,pidParent,accountName,accountId,accountType,accountGroup,status);
                chartOfAccountList.add(chartOfAccountTable);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return chartOfAccountList;
    }





   public String[][] getAccountName(){


        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select account_name,account_id from chart_of_account where account_group = ?", new String[]{"Group"});


       String[][] accountName;


       if(cursor != null && cursor.getCount()>0){

           //int j = cursor.getCount();
           accountName = new String[cursor.getCount()+1][2];
           cursor.moveToFirst();
           for (int i =0; i< cursor.getCount(); i++ ){
               accountName[i][0] = cursor.getString(cursor.getColumnIndex("account_id"));
               accountName[i][1] = cursor.getString(cursor.getColumnIndex("account_name")) + " - " + cursor.getString(cursor.getColumnIndex("account_id"));
               cursor.moveToNext();
           }

           accountName[cursor.getCount()][1] = "None";
           accountName[cursor.getCount()][0] = "0";

       }else {
           accountName = new String[1][2];
           accountName[0][1] = "None";
           accountName[0][0] = "0";
       }


       cursor.close();
       db.close();
       return accountName;
    }


    public String[][] getTransactionalAccountName(){


        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select account_name,account_id from chart_of_account where account_group = ?", new String[]{"Transactional"});


        String[][] accountName;


        if(cursor != null && cursor.getCount()>0){

            //int j = cursor.getCount();
            accountName = new String[cursor.getCount()+1][2];
            cursor.moveToFirst();
            for (int i =0; i< cursor.getCount(); i++ ){
                accountName[i][0] = cursor.getString(cursor.getColumnIndex("account_id"));
                accountName[i][1] = cursor.getString(cursor.getColumnIndex("account_name"));
                cursor.moveToNext();
            }

            accountName[cursor.getCount()][1] = "None";
            accountName[cursor.getCount()][0] = "0";

        }else {
            accountName = new String[1][2];
            accountName[0][1] = "None";
            accountName[0][0] = "0";
        }


        cursor.close();
        db.close();
        return accountName;
    }




    public void deleteRow(int pid){
        db = this.getWritableDatabase();

        db.delete(table1, table1Pid + " = ?", new String[]{String.valueOf(pid)});
        db.close();

    }




    public void updateRow(ChartOfAccountTable chartOfAccountTable){

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //int pid = chartOfAccountTable.getTable1Pid();
        //values.put(DBHandler.table1Pid,chartOfAccountTable.getTable1Pid());
        values.put(DBHandler.table1Pid_parent,chartOfAccountTable.getTable1Pid_parent());
        values.put(DBHandler.table1AccountName,chartOfAccountTable.getTable1AccountName());
        values.put(DBHandler.table1AccountId,chartOfAccountTable.getTable1AccountId());
        values.put(DBHandler.table1AccountType,chartOfAccountTable.getTable1AccountType());
        values.put(DBHandler.table1AccountGroup, chartOfAccountTable.getTable1AccountGroup());
        values.put(DBHandler.table1Status, chartOfAccountTable.getTable1Status());
        try {
            int a = db.update(table1,values,table1Pid + " = ?",new String[]{String.valueOf(chartOfAccountTable.getTable1Pid())});
            System.out.print(a);
        }catch (Exception e){
            System.out.print(e);
        }
        //Log.d("Mamun", "Affected row is " + String.valueOf(affectedRow));
        db.close();
    }



    public String[][] getAccountNameExcept(int pid){


        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select account_name,account_id from chart_of_account" +
                " where account_group = ? and " + table1Pid + " <> ?", new String[]{"Group",String.valueOf(pid)});


        String[][] accountName;


        if(cursor != null && cursor.getCount()>0){

            //int j = cursor.getCount();
            accountName = new String[cursor.getCount()+1][2];
            cursor.moveToFirst();
            for (int i =0; i< cursor.getCount(); i++ ){
                accountName[i][0] = cursor.getString(cursor.getColumnIndex("account_id"));
                accountName[i][1] = cursor.getString(cursor.getColumnIndex("account_name")) + " - " + cursor.getString(cursor.getColumnIndex("account_id"));
                cursor.moveToNext();
            }

            accountName[cursor.getCount()][1] = "None";
            accountName[cursor.getCount()][0] = "0";

        }else {
            accountName = new String[1][1];
            accountName[0][1] = "None";
            accountName[0][0] = "0";
        }


        cursor.close();
        db.close();
        return accountName;
    }

}
