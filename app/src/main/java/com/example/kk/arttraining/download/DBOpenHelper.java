package com.example.kk.arttraining.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context) {
		super(context, "downs.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//数据库的结构为:表名:filedownlog 字段:id,downpath:当前下载的资源,
		//threadid:下载的线程id，downlength:线程下载的最后位置
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog " +
				"(id integer primary key autoincrement," +
				" downpath varchar(100)," +
				" threadid INTEGER, downlength INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//当版本号发生改变时调用该方法,这里删除数据表,在实际业务中一般是要进行数据备份的
		db.execSQL("DROP TABLE IF EXISTS filedownlog");
		onCreate(db);
	}

}
