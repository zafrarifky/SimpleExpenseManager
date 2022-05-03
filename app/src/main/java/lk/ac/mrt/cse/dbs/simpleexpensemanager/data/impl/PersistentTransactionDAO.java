package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.DatabaseHelper;

public class PersistentTransactionDAO implements TransactionDAO {
    DatabaseHelper db;

    public PersistentTransactionDAO(Context context){
        db = new DatabaseHelper(context);
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        Transaction transaction = new Transaction(date, accountNo, expenseType, amount);
        SQLiteDatabase sqLiteDatabase= db.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put(DatabaseHelper.COL_1,accountNo);
        contentValues.put(DatabaseHelper.COL_6, String.valueOf(date));
        contentValues.put(DatabaseHelper.COL_7, expenseType.name());
        contentValues.put(DatabaseHelper.COL_8,amount);

        long result = sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME_2, null, contentValues);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_NAME_2;

        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Transaction transaction=new Transaction(
                        new Date(cursor.getString(1)),
                        cursor.getString(2),
                        ExpenseType.valueOf(cursor.getString(3)),
                        cursor.getDouble(4));
                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }

        return transactionList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactionListLimited = new ArrayList<Transaction>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_NAME_2 + " LIMIT " + limit;

        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Transaction transaction=new Transaction(
                        new Date(cursor.getString(1)),
                        cursor.getString(2),
                        ExpenseType.valueOf(cursor.getString(3)),
                        cursor.getDouble(4));

                transactionListLimited.add(transaction);
            } while (cursor.moveToNext());
        }

        return transactionListLimited;
    }
}
