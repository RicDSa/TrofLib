package com.pm.troflib.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pm.troflib.data.entities.Book

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addBook(book: Book)

    @Update
    fun updateBook(book: Book)

    @Query("SELECT * FROM book ORDER BY id DESC")
    fun readAllBooks(): LiveData<List<Book>>

    @Delete
    fun deleteBook(book: Book)

}