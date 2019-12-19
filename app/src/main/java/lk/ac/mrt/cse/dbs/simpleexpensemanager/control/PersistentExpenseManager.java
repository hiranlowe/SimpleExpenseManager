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

package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.ExpenseManagerDatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

/**
 *
 */
public class PersistentExpenseManager extends ExpenseManager {
    private transient Context context;

    public PersistentExpenseManager(Context context) {
        this.context=context;
        setup();
    }

    @Override
    public void setup() {

        SQLiteDatabase db = new ExpenseManagerDatabaseHelper(context).getWritableDatabase();
        TransactionDAO PersistentTransactionDAO = new PersistentTransactionDAO(db);
        setTransactionsDAO(PersistentTransactionDAO);

        AccountDAO PersistentAccountDAO = new PersistentAccountDAO(db);
        setAccountsDAO(PersistentAccountDAO);

        // dummy data
        //Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
        //Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
        //getAccountsDAO().addAccount(dummyAcct1);
        //getAccountsDAO().addAccount(dummyAcct2);

        /*** End ***/
    }
}
