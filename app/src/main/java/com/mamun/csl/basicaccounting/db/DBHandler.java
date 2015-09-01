package com.mamun.csl.basicaccounting.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mamun on 8/12/15.
 */
public class DBHandler extends SQLiteOpenHelper {

    //Database info
    public static final String dbName = "csl-Accounting";
    public static final int dbVersion = 1;
    public static final String table1 = "chart_of_account";
    public static final String table2 = "ledger_transaction";


    public static final String table1Pid = "pid";
    public static final String table1Pid_parent = "pid_parent";
    public static final String table1AccountName = "account_name";
    public static final String table1AccountId = "account_id";
    public static final String table1AccountType = "account_type";
    public static final String table1AccountGroup = "account_group";
    public static final String table1Status = "status";


    public static final String table2Pid = "p_id";
    public static final String table2PidParent = "pid_parent";
    public static final String table2TransactionDate = "transaction_date";
    public static final String table2AccFrom = "acc_from";
    public static final String table2AccTo = "acc_to";
    public static final String table2AmountDr = "amount_dr";
    public static final String table2AmountCr = "amount_cr";
    public static final String table2RefBill = "ref_bill";
    public static final String table2Description = "description";
    public static final String table2BankCheque = "bank_cheque";
    public static final String table2Transaction_type = "transaction_type";
    public static final String table2Status = "status";


    private String createTable1 = "create table " + table1 + "(" +
            table1Pid + " INTEGER PRIMARY KEY," +
            table1Pid_parent + " INTEGER," +
            table1AccountName + " text," +
            table1AccountId  + " integer," +
            table1AccountType + " text," +
            table1AccountGroup + " text," +
            table1Status + " text" +
            ");";

    private String createTable2 = "create table " + table2 +"(" +
            table2Pid + " integer," +
            table2PidParent + " integer," +
            table2TransactionDate + " text," +
            table2AccFrom + "integer," +
            table2AccTo + " integer," +
            table2AmountDr + " integer," +
            table2AmountCr + " integer," +
            table2RefBill + " text," +
            table2Description + " text," +
            table2BankCheque + " text," +
            table2Transaction_type + " text," +
            table2Status + " text" +
            ");";

    public DBHandler(Context context) {
        super(context, dbName, null, dbVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creating table if not exist
        db.execSQL(createTable1);
        db.execSQL(createTable2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
