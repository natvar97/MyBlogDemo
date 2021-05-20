package com.example.appdemoroomdatabase_coroutine_mvvm_livedata.repository

import androidx.annotation.WorkerThread
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.entity.Blog
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.entity.BlogDao
import kotlinx.coroutines.flow.Flow

class BlogRepository(private val blogDao: BlogDao) {

    @WorkerThread
    suspend fun insert(blog: Blog) {
        blogDao.insert(blog)
    }

    @WorkerThread
    suspend fun update(blog: Blog) {
        blogDao.update(blog)
    }

    @WorkerThread
    suspend fun delete(blog: Blog) {
        blogDao.delete(blog)
    }

    fun getAllBlogs(): Flow<List<Blog>> {
        return blogDao.getAllBlogs()
    }

}