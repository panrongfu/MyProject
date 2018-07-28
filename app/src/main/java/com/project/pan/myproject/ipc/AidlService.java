package com.project.pan.myproject.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pan
 */
public class AidlService extends Service {


     List<Book> bookList;
     IBinder iBinder = new IBookManager.Stub() {
         @Override
         public List<Book> getBookList() throws RemoteException {
             return bookList;
         }

         @Override
         public void addBook(Book book) throws RemoteException {
            bookList.add(book);
         }
     };
    public AidlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        bookList = new ArrayList<>();
        return iBinder;
    }
}
