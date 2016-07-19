package com.cloudsolutionltd.cslMobileAccounts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    private Context context;
    private SQLiteDatabase dataBase;
    private static String DB_PATH;


    public static final String table1Pid_parent = "pid_parent";
    public static final String table1AccountName = "account_name";
    public static final String table1AccountId = "account_id";
    public static final String table1AccountType = "account_type";
    public static final String table1AccountGroup = "account_group";
    public static final String table1Status = "status";
    public static final String table2Pid = "p_id";
    public static final String table2PidPair = "pid_pair";
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


//    private String createTable1 = "create table " + table1 + "(" +
//            table1Pid + " INTEGER PRIMARY KEY," +
//            table1Pid_parent + " INTEGER," +
//            table1AccountName + " text," +
//            table1AccountId  + " integer," +
//            table1AccountType + " text," +
//            table1AccountGroup + " text," +
//            table1Status + " text" +
//            ");";
//
//    private String createTable2 = "create table " + table2 +"(" +
//            table2Pid + " integer primary key," +
//            table2PidPair + " integer," +
//            table2TransactionDate + " text," +
//            table2AccFrom + " integer," +
//            table2AccTo + " integer," +
//            table2AmountDr + " integer," +
//            table2AmountCr + " integer," +
//            table2RefBill + " text," +
//            table2Description + " text," +
//            table2BankCheque + " text," +
//            table2Transaction_type + " text," +
//            table2Status + " text" +
//            ");";

    public DBHandler(Context context) {
        super(context, dbName, null, dbVersion);


        this.context = context;

        // database path /data/data/pkg-name/databases/
        String packageName = context.getPackageName();
        DB_PATH = "/data/data/" + packageName + "/databases/";
        this.dataBase = openDataBase();

        /*try {
                DB_PATH = "/data/data/" + context.getApplicationContext() + "/databases/";
        }catch (Exception e){
            Log.e("GetPackageNAme: ", e.toString());
        }


        this.dataBase = openDataBase();*/

    }

    public SQLiteDatabase getDataBase() {
        return this.dataBase;
    }

    public SQLiteDatabase openDataBase() {
        String path = DB_PATH + dbName;
        if (dataBase == null) {
            createDatabase();
            dataBase = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        return dataBase;
    }

    private void createDatabase() {
        boolean dbExists = checkDB();
        if (!dbExists) {
            this.getReadableDatabase();
            Log.e(getClass().getName(),
                    "Database doesn't exist. Copying database from assets...");
            copyDatabase();
        } else {
            Log.e(getClass().getName(), "Database already exists");
        }
    }

    private void copyDatabase() {
        try {
            InputStream dbInputStream = context.getAssets().open(dbName);
            String path = DB_PATH + dbName;
            OutputStream dbOutputStream = new FileOutputStream(path);
            byte[] buffer = new byte[4096];
            int readCount = 0;
            while ((readCount = dbInputStream.read(buffer)) > 0) {
                dbOutputStream.write(buffer, 0, readCount);
            }

            dbInputStream.close();
            dbOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean checkDB() {
        String path = DB_PATH + dbName;
        File file = new File(path);
        if (file.exists()) {
            Log.e(getClass().getName(), "Database already exists");
            return true;
        }
        Log.e(getClass().getName(), "Database does not exists");
        return false;
    }


    public synchronized void close() {
        if (this.dataBase != null) {
            this.dataBase.close();
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creating table if not exist
        //db.execSQL(createTable1);
        //db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
