package lk.ac.mrt.cse.dbs.simpleexpensemanager.ui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="190525X.db";
    public static final String TABLE_NAME_1="accountTable";
    public static final String TABLE_NAME_2="transactionTable";

    public static final String COL_1="accountNo";
    public static final String COL_2="bankName";
    public static final String COL_3="accountHolderName";
    public static final String COL_4="balance";

    public static final String COL_5="ID";
    public static final String COL_6="date";
    public static final String COL_7="expenseType";
    public static final String COL_8="amount";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME_1 + "(" +
                COL_1 + " TEXT PRIMARY KEY," +
                COL_2 + " TEXT," +
                COL_3 + " TEXT," +
                COL_4 + " REAL)" );

        sqLiteDatabase.execSQL("create table " + TABLE_NAME_2 + "("
                + COL_5 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_6 + " TEXT," +
                COL_1 + " TEXT," +
                COL_7 + " TEXT," +
                COL_8 + " REAL," +
                "FOREIGN KEY ("+COL_1+") REFERENCES " + TABLE_NAME_1 + "(" + COL_1 + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_2);

        onCreate(sqLiteDatabase);
    }

}
