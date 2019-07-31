package com.example.menudemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.webkit.URLUtil;

import java.net.URI;

public class MenuProvider extends ContentProvider {
    Context context;
    SQLiteDatabase db = null;
    MenuDao md = null;
    //设置contentprovider唯一标识
    public static final String AUTHORITY = "com.imooc.menuprovider";

//    //UriMather类使用：在contentprovider中注册uri
//    private static final UriMatcher matcher;
//    static {
//        matcher = new UriMatcher(UriMatcher.NO_MATCH);
//
//    }
    public MenuProvider() {
    }

    //初始化contentprovider
    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        context = getContext();
        md = new MenuDao(getContext());
        db = md.getWritableDatabase();
//        db = MenuDao.getInstance(getContext());
        return true;
    }
    //添加数据
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long id = db.insert("dish_tb",null,values);
        return ContentUris.withAppendedId(uri,id);
    }
    //删除数据
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = db.delete("dish_tb","dish_id=?",selectionArgs);
        return count;
    }
    //获取父类名字
    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    //查询数据
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor c = db.query("dish_tb",null,null,null,null,null,"dish_type");

        return c;
    }
    //更新数据
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
