package cn.alien95.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linlongxin on 2016/1/5.
 * 数据库辅助类
 */
public class SqlHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME;

    private static Context mContext;

    private static SqlHelper instance;

    //key是表的name,value是表的sql建表语句
    private Map<String, String> tables = new HashMap<>();

    private SqlHelper(Context context, String dataBaseName) {
        super(context, dataBaseName, null, Utils.getAppVersion());
    }

    public static SqlHelper getInstance() {
        return instance;
    }

    /**
     * 初始化数据库
     * @param context
     * @param dataBaseName
     */
    public static void init(Context context, String dataBaseName) {
        instance = new SqlHelper(context, dataBaseName);
        DATABASE_NAME = dataBaseName;
        mContext = context;
    }

    /**
     * 添加表的创建
     *
     * @param tableName
     * @param sql
     */
    public void addTable(String tableName, String sql) {
        tables.put(tableName, sql);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建所有的表
        for (Map.Entry<String, String> table : tables.entrySet()) {
            db.execSQL(table.getValue());
        }
    }

    /**
     * 更新数据库通过APP版本
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            String sql;
            for (Map.Entry<String,String> entry: tables.entrySet()) {
                sql = "DROP TABLE IF EXISTS " + entry.getKey();
                db.execSQL(sql);
            }
            onCreate(db);
        }

    }

    /**
     * 删除数据库
     */
    public boolean deleteDataBase() {
        return mContext.deleteDatabase(DATABASE_NAME);
    }


}
