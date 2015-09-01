package com.mamun.csl.basicaccounting.db;

/**
 * Created by mamun on 8/12/15.
 */
public class ChartOfAccountTable {

    private int table1Pid ;
    private int table1Pid_parent;
    private String table1AccountName;
    private int table1AccountId;
    private String table1AccountType;
    private String table1AccountGroup;
    private String table1Status;



    public ChartOfAccountTable(){}

    public ChartOfAccountTable(int table1Pid, int table1Pid_parent, String table1AccountName, int table1AccountId, String table1AccountType, String table1AccountGroup, String table1Status) {
        this.table1Pid = table1Pid;
        this.table1Pid_parent = table1Pid_parent;
        this.table1AccountName = table1AccountName;
        this.table1AccountId = table1AccountId;
        this.table1AccountType = table1AccountType;
        this.table1AccountGroup = table1AccountGroup;
        this.table1Status = table1Status;
    }

    //Getter and setter
    public int getTable1Pid() {
        return table1Pid;
    }

    public void setTable1Pid(int table1Pid) {
        this.table1Pid = table1Pid;
    }

    public int getTable1Pid_parent() {
        return table1Pid_parent;
    }

    public void setTable1Pid_parent(int table1Pid_parent) {
        this.table1Pid_parent = table1Pid_parent;
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

    public String getTable1AccountGroup() {
        return table1AccountGroup;
    }

    public void setTable1AccountGroup(String table1AccountGroup) {
        this.table1AccountGroup = table1AccountGroup;
    }

    public String getTable1Status() {
        return table1Status;
    }

    public void setTable1Status(String table1Status) {
        this.table1Status = table1Status;
    }
}
