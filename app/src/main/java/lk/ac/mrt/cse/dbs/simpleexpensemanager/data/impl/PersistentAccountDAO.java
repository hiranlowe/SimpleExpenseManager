/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.ExpenseManagerDatabaseContract;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.ExpenseManagerDatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.ExpenseManagerDatabaseContract.*;
/**
 * This is an In-Memory implementation of the AccountDAO interface. This is not a persistent storage. A HashMap is
 * used to store the account details temporarily in the memory.
 */
public class PersistentAccountDAO implements AccountDAO, Serializable {

    private transient SQLiteDatabase db;

    public PersistentAccountDAO(SQLiteDatabase db){this.db = db;}



    @Override
    public List<String> getAccountNumbersList() {
        String query = "SELECT " + AccountsTable.COL2 + " FROM "+AccountsTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        List<String> AccountNumbersList = new ArrayList<>();
        while(cursor.moveToNext()){
            String accountNo = cursor.getString(cursor.getColumnIndex(AccountsTable.COL2));
            AccountNumbersList.add(accountNo);
        }
        return AccountNumbersList;
    }

    @Override
    public List<Account> getAccountsList() {
        String query = "SELECT * FROM " +AccountsTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        List<Account> AccountNumbersList = new ArrayList<>();
        while(cursor.moveToNext()){
            String accountNo = cursor.getString(cursor.getColumnIndex(AccountsTable.COL2));
            String bankName = cursor.getString(cursor.getColumnIndex(AccountsTable.COL3));
            String accountHolderName = cursor.getString(cursor.getColumnIndex(AccountsTable.COL4));
            double balance = cursor.getDouble(cursor.getColumnIndex(AccountsTable.COL5));
            Account account = new Account(accountNo,bankName,accountHolderName,balance);
            AccountNumbersList.add(account);
        }
        return getAccountsList();
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        String query="SELECT * FROM "+ AccountsTable.TABLE_NAME+" WHERE "+ AccountsTable.COL2+" = '"+accountNo+"';";
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.getCount()==0){
            String msgtxt = "The Account associated with " + accountNo + " is invalid.";
            throw new InvalidAccountException(msgtxt);
        }
        else{
            cursor.moveToFirst();
            String bankName = cursor.getString(cursor.getColumnIndex(AccountsTable.COL3));
            String accountHolderName = cursor.getString(cursor.getColumnIndex(AccountsTable.COL4));
            double balance = cursor.getDouble(cursor.getColumnIndex(AccountsTable.COL5));
            Account account = new Account(accountNo,bankName,accountHolderName,balance);
            return  account;
        }

    }

    @Override
    public void addAccount(Account account) {
        String query="INSERT INTO "+ AccountsTable.TABLE_NAME+"("+ AccountsTable.COL2+","+ AccountsTable.COL3+","+
                AccountsTable.COL4+","+ AccountsTable.COL5+") VALUES(?,?,?,?);";
        SQLiteStatement stmt=db.compileStatement(query); //avoid sql injection
        stmt.bindString(1,account.getAccountNo());
        stmt.bindString(2,account.getBankName());
        stmt.bindString(3,account.getAccountHolderName());
        stmt.bindDouble(4,account.getBalance());
        try{
            stmt.executeInsert();
        }
        catch (SQLiteConstraintException ex){
            Log.e("Error","Error");
        }

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        String query="DELETE FROM "+ AccountsTable.TABLE_NAME+" WHERE "+ AccountsTable.COL2+" = ?;";
        SQLiteStatement stmt=db.compileStatement(query); //avoid sql injection
        stmt.bindString(1,accountNo);
        stmt.executeUpdateDelete();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        Account account=getAccount(accountNo);


        // specific implementation based on the transaction type
        switch (expenseType) {
            case EXPENSE:
                account.setBalance(account.getBalance() - amount);
                break;
            case INCOME:
                account.setBalance(account.getBalance() + amount);
                break;
        }
        String query="UPDATE "+ AccountsTable.TABLE_NAME+" SET "+ AccountsTable.COL5+" = ? WHERE "+
                AccountsTable.COL2+" = ? ;"  ;
        SQLiteStatement stmt=db.compileStatement(query); //avoid sql injection
        stmt.bindDouble(1,account.getBalance());
        stmt.bindString(2,accountNo);
        stmt.executeUpdateDelete();
    }
}
