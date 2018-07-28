// IBookManager.aidl
package com.project.pan.myproject.ipc;

// Declare any non-default types here with import statements
import com.project.pan.myproject.ipc.Book;

interface IBookManager {

    List<Book> getBookList();
    void addBook(in Book book);
}
