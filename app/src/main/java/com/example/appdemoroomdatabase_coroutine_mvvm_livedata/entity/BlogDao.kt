package com.example.appdemoroomdatabase_coroutine_mvvm_livedata.entity

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BlogDao {

    @Insert
    suspend fun insert(blog: Blog)

    @Delete
    suspend fun delete(blog: Blog)

    @Update
    suspend fun update(blog: Blog)

    @Query("SELECT * FROM blogs_table ORDER BY title ASC")
    fun getAllBlogs(): Flow<List<Blog>>

}