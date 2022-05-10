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

package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType.EXPENSE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest{
    private static ExpenseManager expenseManager;

    @Before
    public void setUp() throws ExpenseManagerException {
        Context context = ApplicationProvider.getApplicationContext();
        expenseManager = new PersistentExpenseManager(context);
    }

    @Test
    public void addAccountTest() {
        expenseManager.addAccount("8756D", "XYZ", "Zee", 7025.0);
        List<String> accountNumbers = expenseManager.getAccountNumbersList();
        assertTrue(accountNumbers.contains("8756D"));
    }

    @Test
    public void addTransactionTest() throws ParseException {
        Date transactionDate = new SimpleDateFormat("dd-MM-yyyy").parse("08-12-2021");
        expenseManager.getTransactionsDAO().logTransaction(transactionDate, "1234Z", EXPENSE, 2200.0);
        List<Transaction> transactions = expenseManager.getTransactionsDAO().getAllTransactionLogs();

        Transaction transaction = transactions.get(transactions.size()-1);
        assertTrue(transaction.getAccountNo().equals("1234Z") && transaction.getAmount()==2200.0 && transaction.getExpenseType()==EXPENSE);
    }
}
