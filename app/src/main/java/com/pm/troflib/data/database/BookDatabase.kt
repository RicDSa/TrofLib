package com.pm.troflib.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pm.troflib.data.dao.BookDao
import com.pm.troflib.data.entities.Book

@Database(entities = [Book :: class],
    version = 3,
    exportSchema = true,
    autoMigrations = [AutoMigration (from = 2, to = 3)])
abstract class BookDatabase : RoomDatabase() {

    abstract fun BookDao() : BookDao
    companion object {
        @Volatile

        private var INSTANCE: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}