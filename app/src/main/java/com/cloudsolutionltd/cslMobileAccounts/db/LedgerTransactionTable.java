package com.cloudsolutionltd.cslMobileAccounts.db;


import java.io.Serializable;

/**
 * Created by mamun on 8/12/15.
 */
public class LedgerTransactionTable implements Serializable {


        private int table2Pid;
        private int table2PidPair;
        private String table2TransactionDate;
        private int table2AccFrom;
        private int table2AccTo;
        private Double table2AmountDr;
        private Double table2AmountCr;
        private String table2RefBill;
        private String table2Description;
        private String table2BankCheque;
        private String table2Transaction_type;

    public LedgerTransactionTable(int table2Pid, int table2PidPair, String table2TransactionDate,
                                  int table2AccFrom, int table2AccTo, Double table2AmountDr, Double table2AmountCr,
                                  String table2RefBill, String table2Description, String table2BankCheque,
                                  String table2Transaction_type) {
        this.table2Pid = table2Pid;
        this.table2PidPair = table2PidPair;
        this.table2TransactionDate = table2TransactionDate;
        this.table2AccFrom = table2AccFrom;
        this.table2AccTo = table2AccTo;
        this.table2AmountDr = table2AmountDr;
        this.table2AmountCr = table2AmountCr;
        this.table2RefBill = table2RefBill;
        this.table2Description = table2Description;
        this.table2BankCheque = table2BankCheque;
        this.table2Transaction_type = table2Transaction_type;
    }

    public LedgerTransactionTable() {
    }

    //Getter and Setter for private variable

        public int getTable2Pid() {
            return table2Pid;
        }

        public void setTable2Pid(int table2Pid) {
            this.table2Pid = table2Pid;
        }

        public int getTable2PidPair() {
            return table2PidPair;
        }

        public void setTable2PidPair(int table2PidParent) {
            this.table2PidPair = table2PidParent;
        }

        public String getTable2TransactionDate() {
            return table2TransactionDate;
        }

        public void setTable2TransactionDate(String table2TransactionDate) {
            this.table2TransactionDate = table2TransactionDate;
        }

        public int getTable2AccFrom() {
            return table2AccFrom;
        }

        public void setTable2AccFrom(int table2AccFrom) {
            this.table2AccFrom = table2AccFrom;
        }

        public int getTable2AccTo() {
            return table2AccTo;
        }

        public void setTable2AccTo(int table2AccTo) {
            this.table2AccTo = table2AccTo;
        }

        public Double getTable2AmountDr() {
            return table2AmountDr;
        }

        public void setTable2AmountDr(Double table2AmountDr) {
            this.table2AmountDr = table2AmountDr;
        }

        public Double getTable2AmountCr() {
            return table2AmountCr;
        }

        public void setTable2AmountCr(Double table2AmountCr) {
            this.table2AmountCr = table2AmountCr;
        }

        public String getTable2RefBill() {
            return table2RefBill;
        }

        public void setTable2RefBill(String table2RefBill) {
            this.table2RefBill = table2RefBill;
        }

        public String getTable2Description() {
            return table2Description;
        }

        public void setTable2Description(String table2Description) {
            this.table2Description = table2Description;
        }

        public String getTable2BankCheque() {
            return table2BankCheque;
        }

        public void setTable2BankCheque(String table2BankCheque) {
            this.table2BankCheque = table2BankCheque;
        }

        public String getTable2Transaction_type() {
            return table2Transaction_type;
        }

        public void setTable2Transaction_type(String table2Transaction_type) {
            this.table2Transaction_type = table2Transaction_type;
        }
}
