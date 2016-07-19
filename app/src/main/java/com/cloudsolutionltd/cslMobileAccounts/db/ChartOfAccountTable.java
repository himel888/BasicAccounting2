package com.cloudsolutionltd.cslMobileAccounts.db;

import java.io.Serializable;

/**
 * Created by mamun on 8/12/15.
 */
public class ChartOfAccountTable implements Serializable {

    private int table1Pid;
    private String table1AccountName;
    private int table1AccountId;
    private String table1AccountType;


    public ChartOfAccountTable() {
    }

    public ChartOfAccountTable(int table1Pid, String table1AccountName, int table1AccountId, String table1AccountType) {
        this.table1Pid = table1Pid;
        this.table1AccountName = table1AccountName;
        this.table1AccountId = table1AccountId;
        this.table1AccountType = table1AccountType;
    }

    //Getter and setter
    public int getTable1Pid() {
        return table1Pid;
    }

    public void setTable1Pid(int table1Pid) {
        this.table1Pid = table1Pid;
    }


    public String getTable1AccountName() {
        return table1AccountName;
    }

    public void setTable1AccountName(String table1AccountName) {
        this.table1AccountName = table1AccountName;
    }

    public int getTable1AccountId() {
        return table1AccountId;
    }

    public void setTable1AccountId(int table1AccountId) {
        this.table1AccountId = table1AccountId;
    }

    public String getTable1AccountType() {
        return table1AccountType;
    }

    public void setTable1AccountType(String table1AccountType) {
        this.table1AccountType = table1AccountType;
    }


    @Override
    public String toString() {
        return "ChartOfAccountTable{" +
                "table1Pid=" + table1Pid +
                ", table1AccountName='" + table1AccountName + '\'' +
                ", table1AccountId=" + table1AccountId +
                ", table1AccountType='" + table1AccountType + '\'' +
                '}';
    }
}
