package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.ExpenseManagerDatabaseContract;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.ExpenseManagerDatabaseContract.*;

public class PersistentTransactionDAO implements TransactionDAO {

    private transient SQLiteDatabase db;
    public PersistentTransactionDAO(SQLiteDatabase db) {this.db=db;}
    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        String sql="INSERT INTO "+ TransactionsTable.TABLE_NAME+"("+TransactionsTable.COL3+","+ TransactionsTable.COL4+","+
                TransactionsTable.COL2+","+ TransactionsTable.COL5+") VALUES(?,?,?,?);";

        SQLiteStatement stmt=db.compileStatement(sql);
        stmt.bindString(1,accountNo);
        stmt.bindString(2,expenseType.name());
        stmt.bindString(3,date.toString());
        stmt.bindDouble(4,amount);

        stmt.executeInsert();

    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        String query = "SELECT * FROM "+ TransactionsTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        List<Transaction> transactions=new ArrayList<>();
        while(cursor.moveToNext()){
            String accountNo = cursor.getString(cursor.getColumnIndex(TransactionsTable.COL3));
            double amount = cursor.getDouble(cursor.getColumnIndex(TransactionsTable.COL5));
            String type = cursor.getString(cursor.getColumnIndex(TransactionsTable.COL4));
            String date = cursor.getString(cursor.getColumnIndex(TransactionsTable.COL2));
            Transaction transaction = new Transaction(new Date(date),accountNo, ExpenseType.valueOf(type),amount);
            transactions.add(transaction);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        String sql = "SELECT * FROM "+ TransactionsTable.TABLE_NAME+" LIMIT "+limit;
        Cursor cursor = db.rawQuery(sql,null);
        List<Transaction> transactions = new ArrayList<>();
        while(cursor.moveToNext()){
            String accountNo = cursor.getString(cursor.getColumnIndex(TransactionsTable.COL3));
            double amount = cursor.getDouble(cursor.getColumnIndex(TransactionsTable.COL5));
            String type = cursor.getString(cursor.getColumnIndex(TransactionsTable.COL4));
            String date = cursor.getString(cursor.getColumnIndex(TransactionsTable.COL2));
            Transaction transaction = new Transaction(new Date(date),accountNo, ExpenseType.valueOf(type),amount);
            transactions.add(transaction);
        }

        return  transactions;
    }

}
