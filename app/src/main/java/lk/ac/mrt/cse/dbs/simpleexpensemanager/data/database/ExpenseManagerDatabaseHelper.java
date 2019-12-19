package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.ExpenseManagerDatabaseContract.*;

public class ExpenseManagerDatabaseHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_AccountsTable =
            "CREATE TABLE " + AccountsTable.TABLE_NAME + " (" +
                    AccountsTable.COL1 + " INTEGER PRIMARY KEY," +
                    AccountsTable.COL2+ " TEXT," +
                    AccountsTable.COL3+ " TEXT," +
                    AccountsTable.COL4+ " TEXT," +
                    AccountsTable.COL5 + " TEXT)";

    private static final String SQL_CREATE_TransactionsTable =
            "CREATE TABLE " + TransactionsTable.TABLE_NAME + " (" +
                    TransactionsTable.COL1 + " INTEGER PRIMARY KEY," +
                    TransactionsTable.COL2+ " TEXT," +
                    TransactionsTable.COL3+ " TEXT," +
                    TransactionsTable.COL4+ " TEXT," +
                    TransactionsTable.COL5 + " TEXT)";

    private static final String SQL_DELETE_AccountsTable =
            "DROP TABLE IF EXISTS " + AccountsTable.TABLE_NAME;

    private static final String SQL_DELETE_TransactionsTable =
            "DROP TABLE IF EXISTS " + TransactionsTable.TABLE_NAME;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ExpenseManager.db";

    public ExpenseManagerDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TransactionsTable);
        db.execSQL(SQL_CREATE_AccountsTable);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_AccountsTable);
        db.execSQL(SQL_DELETE_TransactionsTable);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}