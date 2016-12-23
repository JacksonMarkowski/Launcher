package jacksonmarkowski.launcher;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "launcher";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_APP = "app";
    public static final String APP_ID = "app_id";
    public static final String APP_NAME = "name";
    private static final String SQL_CREATE_APP = "create table " + TABLE_APP + " (" + APP_ID + " integer primary key autoincrement, " + APP_NAME + " text)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_APP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_APP);
        onCreate(db);
    }
}
