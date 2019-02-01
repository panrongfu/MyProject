package com.project.pan.myproject.ipc.customBinder;

import android.os.IInterface;
import android.os.RemoteException;

/**
 * @author: panrongfu
 * @date: 2018/11/7 20:09
 * @describe:
 */

public interface IBookManager extends IInterface{

    public void addBook(Book book) throws RemoteException;
    public void deleteBook(Book book) throws RemoteException;
}
