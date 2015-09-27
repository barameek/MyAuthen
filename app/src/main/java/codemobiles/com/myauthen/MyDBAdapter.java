package codemobiles.com.myauthen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codemobiles.util.CMAssetBundle;
import com.codemobiles.util.UserBean;

/**
 * Created by maboho_retina on 9/25/15 AD.
 */
public class MyDBAdapter {


    private SQLiteDatabase sqliteDB;


    public MyDBAdapter(Context context){
        final String dbFilePath = CMAssetBundle.getAppPackagePath(context) + "/Account.db";
        sqliteDB = SQLiteDatabase.openDatabase(dbFilePath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void insert(UserBean bean){
        ContentValues values = new ContentValues();
        values.put(UserBean.COLUMN_USERNAME, bean.username);
        values.put(UserBean.COLUMN_PASSWORD, bean.password);
        values.put(UserBean.COLUMN_PASSWORD_REM, bean.isPasswordRemembered);
        sqliteDB.insert(UserBean.TABLE_NAME, null, values);
        // sqliteDB.execSQL("Insert into Usernamepassword.. ");
    }

    public void update(UserBean bean){

        ContentValues values = new ContentValues();
        values.put(UserBean.COLUMN_USERNAME, bean.username);
        values.put(UserBean.COLUMN_PASSWORD, bean.password);
        values.put(UserBean.COLUMN_PASSWORD_REM, bean.isPasswordRemembered);
        String selection = UserBean.COLUMN_USERNAME + "=?" ;


        sqliteDB.update(UserBean.TABLE_NAME, values, selection, new String[]{bean.username});
    }


    public final UserBean query(final String username) {
        // An array specifying which columns to return.
        String columns[] = new String[] { UserBean.COLUMN_USERNAME, UserBean.COLUMN_PASSWORD, UserBean.COLUMN_PASSWORD_REM };
        String selection = UserBean.COLUMN_USERNAME + "=?";
        UserBean userBn = null;

        Cursor cur = null;

        try {
            cur = sqliteDB.query(UserBean.TABLE_NAME, columns, selection, new String[] { username }, null, null, null);

            // check if found any query result
            if (cur.getCount() > 0) {

                cur.moveToFirst();
                userBn = new UserBean();
                userBn.username = cur.getString(cur.getColumnIndex(UserBean.COLUMN_USERNAME));
                userBn.password = cur.getString(cur.getColumnIndex(UserBean.COLUMN_PASSWORD));
                userBn.isPasswordRemembered = Integer.valueOf(cur.getInt(cur.getColumnIndex(UserBean.COLUMN_PASSWORD_REM)));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            cur.close();
        }

        return userBn;
    }
}
