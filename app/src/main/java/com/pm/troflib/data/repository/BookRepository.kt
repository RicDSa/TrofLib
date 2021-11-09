package com.pm.troflib.data.repository

import androidx.lifecycle.LiveData
import com.pm.troflib.data.dao.BookDao
import com.pm.troflib.data.entities.Book

class BookRepository(private  val bookDao: BookDao) {
    val readAllBooks : LiveData<List<Book>> = bookDao.readAllBooks()

    suspend fun addBook(book: Book){
        bookDao.addBook(book)
    }

    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }
}
