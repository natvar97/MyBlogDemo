package com.example.appdemoroomdatabase_coroutine_mvvm_livedata.utils

import android.app.Application
import androidx.room.Database
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.entity.BlogDatabase
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.repository.BlogRepository

class BlogApplication : Application() {

    private val database by lazy {
        BlogDatabase.getInstance(this@BlogApplication)
    }

    val repository by lazy {
        BlogRepository(database.blogDao())
    }

}