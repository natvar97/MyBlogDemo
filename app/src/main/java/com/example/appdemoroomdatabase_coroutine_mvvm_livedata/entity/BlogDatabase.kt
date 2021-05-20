package com.example.appdemoroomdatabase_coroutine_mvvm_livedata.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.utils.Constants

@Database(entities = [Blog::class], version = 1)
abstract class BlogDatabase : RoomDatabase() {

    abstract fun blogDao(): BlogDao

    companion object {
        @Volatile
        private var INSTANCE: BlogDatabase? = null

        fun getInstance(context: Context): BlogDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    BlogDatabase::class.java,
                    Constants.DATABASE_NAME
                ).build()
            }
            return INSTANCE!!
        }

    }

}