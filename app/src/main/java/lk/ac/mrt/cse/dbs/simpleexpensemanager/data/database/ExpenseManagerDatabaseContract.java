package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database;

import android.provider.BaseColumns;

public final class ExpenseManagerDatabaseContract {

    private ExpenseManagerDatabaseContract() {}

    public static class AccountsTable implements BaseColumns {
        public static final String TABLE_NAME= "AccountsTable";
        public static final String COL1 = "ID";
        public static final String COL2 = "accountNo";
        public static final String COL3 = "bankName";
        public static final String COL4 = "accountHolderName";
        public static final String COL5 = "balance";
    }

    public static class TransactionsTable implements BaseColumns {
        public static final String TABLE_NAME= "TransactionsTable";
        public static final String COL1 = "ID";
        public static final String COL2 = "date";
        public static final String COL3 = "accountNo";
        public static final String COL4 = "expenseType";
        public static final String COL5 = "amount";
    }

}
