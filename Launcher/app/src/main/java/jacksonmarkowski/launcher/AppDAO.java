package jacksonmarkowski.launcher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AppDAO {

    private DbHelper dbHelper;
    private static final String[] columns = {DbHelper.APP_ID, DbHelper.APP_NAME};

    public AppDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public App createApp(String packageName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.APP_NAME, packageName);
        db.insert(DbHelper.TABLE_APP, null, values);

        //ToDo: rework, potential error if duplicate appNames
        Cursor cursor = db.rawQuery("select * from " + DbHelper.TABLE_APP + " where " + DbHelper.APP_NAME + " = '" + packageName + "'", null);
        if (cursor.moveToFirst()) {
            App app = cursorToApp(cursor);
            db.close();
            return app;
        } else {
            //ToDo: error
            db.close();
        }
        return null;
    }

    public void deleteApp(String packageName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + DbHelper.TABLE_APP + " where " + DbHelper.APP_NAME + " = '" + packageName + "'", null);
        //ToDo: return error if id is not found, not 0
        int id = 0;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(DbHelper.APP_ID));
            db.delete(DbHelper.TABLE_APP, DbHelper.APP_NAME + "='" + packageName + "'", null);
            db.close();
        } else {
            //ToDo: error
            db.close();
        }

    }

    public List<App> getAllApps() {
        List<App> apps = new ArrayList<App>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DbHelper.TABLE_APP, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                apps.add(cursorToApp(cursor));
                cursor.moveToNext();
            }
        }
        return apps;
    }

    private App cursorToApp(Cursor cursor) {
        App app = new App();
        app.setId(cursor.getInt(cursor.getColumnIndex(DbHelper.APP_ID)));
        app.setName(cursor.getString(cursor.getColumnIndex(DbHelper.APP_NAME)));
        return app;
    }
}
