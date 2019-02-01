package com.project.pan.myproject.ipc.customBinder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * @author: panrongfu
 * @date: 2018/11/7 20:03
 * @describe:
 */

public class BookManagerNative extends Binder implements IBookManager {

    static final String DESCRIPTOR = "com.project.pan.myproject.ipc.customBinder.IBookManager";
    static final int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION+0;
    static final int TRANSACTION_deleteBook = IBinder.FIRST_CALL_TRANSACTION+1;

    public BookManagerNative() {
        this.attachInterface(this,DESCRIPTOR);
    }

    public IBookManager asInterface(IBinder obj){

        if((obj == null)){
            return null;
        }
        /**
         * 如果是在同一个进程中，则返回本地的binder
         */
        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if((iin != null) && (iin instanceof IBookManager)){
            return (IBookManager) iin;
        }
        //如果不是同一个进程，则返回一个代理类
        return new BookManagerProxy(obj);
    }

    /**
     * 1）当客户端发起跨进程请求是，远程请求会通过系统底层封装后交给此方法处理
     * 2）服务端通过code可以确定客户端所请求的目标方法是什么，接着从data里面
     * 取出目标方法所需的参数（如果目标方法有参数）然后执行膜表方法
     * 3）当方法执行完成后，就像reply中写入返回值（如果目标方法有返回值的话）
     * @param code
     * @param data
     * @param reply
     * @param flags
     * @return
     * @throws RemoteException
     */
    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code){
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSACTION_addBook:
                data.enforceInterface(DESCRIPTOR);
                Book book;
                if((0!=data.readInt())){
                    book = Book.CREATOR.createFromParcel(data);
                }else {
                    book = null;
                }
                this.addBook(book);
                return true;
            case TRANSACTION_deleteBook:
                data.enforceInterface(DESCRIPTOR);
                Book bookDelete;
                if((0!=data.readInt())){
                    bookDelete = Book.CREATOR.createFromParcel(data);
                }else {
                    bookDelete = null;
                }
                this.deleteBook(bookDelete);
                return true;
        }
        return super.onTransact(code,data,reply,flags);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    public void addBook(Book book) throws RemoteException {
    }

    @Override
    public void deleteBook(Book book) throws RemoteException{

    }

    /**
     * 不同进程的情况下，回返回这个binder的代理对象
     */
    public static class BookManagerProxy extends Binder implements IBookManager{

        private IBinder mRemote;
        public BookManagerProxy(IBinder remote) {
            mRemote = remote;
        }
        @Override
        public void addBook(Book book) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            data.writeInterfaceToken(DESCRIPTOR);
            if((book !=null)){
                data.writeInt(1);
                book.writeToParcel(data,0);
            }else {
                data.writeInt(0);
            }
            mRemote.transact(TRANSACTION_addBook,data,reply,0);
            reply.readException();
        }

        @Override
        public void deleteBook(Book book) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            data.writeInterfaceToken(DESCRIPTOR);
            if((book !=null)){
                data.writeInt(1);
                book.writeToParcel(data,0);
            }else {
                data.writeInt(0);
            }
            mRemote.transact(TRANSACTION_deleteBook,data,reply,0);
            reply.readException();
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }
    }
}
