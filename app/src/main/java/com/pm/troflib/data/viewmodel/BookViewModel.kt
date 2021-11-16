package com.pm.troflib.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.pm.troflib.data.database.BookDatabase
import com.pm.troflib.data.entities.Book
import com.pm.troflib.data.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel (application: Application) : AndroidViewModel(application){
    val readAllBooks : LiveData<List<Book>>
    private val repository: BookRepository

    init {
        val bookDao = BookDatabase.getDatabase(application).BookDao()
        repository = BookRepository(bookDao)
        readAllBooks = repository.readAllBooks
    }

    fun  addBook(book: Book){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBook(book)
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBook(book)
        }
    }

    fun  deleteBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBook(book)
        }
    }
}