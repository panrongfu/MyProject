package com.project.pan.myproject.ipc.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * @author: panrongfu
 * @date: 2018/7/31 9:55
 * @describe:
 */

public class BookProvider extends ContentProvider {

    public static final String AUTHORITY = "com.project.pan.myproject.book.provider";

    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY,"book",BOOK_URI_CODE);
        uriMatcher.addURI(AUTHORITY,"user",USER_URI_CODE);
    }

    private SQLiteDatabase db;
    private Context mContext;
    @Override
    public boolean onCreate() {
        mContext = getContext();
        AsyncTask.execute(()-> initProviderData());
        return false;
    }

    private void initProviderData() {
        db = new DbOpenHelper(mContext).getWritableDatabase();
        db.execSQL("delete from "+DbOpenHelper.BOOK_TABLE_NAME);
        db.execSQL("delete from "+DbOpenHelper.USER_TABLE_NAME);
        db.execSQL("insert into book values(3,'android');");
        db.execSQL("insert into book values(4,'java');");
        db.execSQL("insert into book values(5,'html5');");
        db.execSQL("insert into book values(6,'pyhon');");
        db.execSQL("insert into user values(1,'jack',1)");
        db.execSQL("insert into user values(2,'pan',1)");
        db.execSQL("insert into user values(3,'chen',0)");
    }

    private String getTableName(Uri uri){
        String tableName = null;
        switch (uriMatcher.match(uri)){
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:
               break;
        }
        return tableName;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName = getTableName(uri);
        if(TextUtils.isEmpty(tableName)){
            throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
        return db.query(tableName,projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getTableName(uri);
        if(TextUtils.isEmpty(tableName)){
            throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
        db.insert(tableName,null,values);
        mContext.getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        if(TextUtils.isEmpty(tableName)){
            throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
        int count = db.delete(tableName,selection,selectionArgs);
        if(count > 0){
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        if(TextUtils.isEmpty(tableName)){
            throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
        int count =db.update(tableName,values,selection,selectionArgs);
        if(count > 0){
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return count;
    }
}
