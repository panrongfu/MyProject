package com.project.pan.myproject.ipc;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pan
 */
public class AidlService extends Service {

    List<Book>   bookList = new ArrayList<>();
    RemoteCallbackList<IOnNewBookAddListener> remoteCallbackList = new RemoteCallbackList<IOnNewBookAddListener>();
    IBinder iBinder = new IBookManager.Stub() {
         @Override
         public List<Book> getBookList() throws RemoteException {
             return bookList;
         }

         @Override
         public void addBook(Book book) throws RemoteException {
            bookList.add(book);
         }

        @Override
        public void registerListener(IOnNewBookAddListener listener) throws RemoteException {
            remoteCallbackList.register(listener);
        }

        @Override
        public void unRegisterListener(IOnNewBookAddListener listener) throws RemoteException {
            remoteCallbackList.unregister(listener);
        }
    };

    public AidlService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //默认添加一本书
        Book book = new Book();
        bookList.add(book);

        int N = remoteCallbackList.beginBroadcast();
        for(int i = 0; i < N; i++){
            IOnNewBookAddListener listener = remoteCallbackList.getBroadcastItem(1);
            if(listener != null){
                try {
                    listener.onNewBookAdd(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
       remoteCallbackList.finishBroadcast();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //检查是否添加了该权限
        int check = checkCallingOrSelfPermission("com.project.pan.myproject.permission.ACCESS_BOOK_SERVER");
        if(check == PackageManager.PERMISSION_DENIED){
            return null;
        }
        return iBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
