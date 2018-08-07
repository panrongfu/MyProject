// IBookManager.aidl
package com.project.pan.myproject.ipc;

// Declare any non-default types here with import statements
import com.project.pan.myproject.ipc.Book;
import com.project.pan.myproject.ipc.IOnNewBookAddListener;
interface IBookManager {

    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookAddListener listener);
    void unRegisterListener(IOnNewBookAddListener listener);

}
