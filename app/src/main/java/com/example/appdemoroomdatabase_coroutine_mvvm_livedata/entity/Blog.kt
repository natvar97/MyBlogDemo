package com.example.appdemoroomdatabase_coroutine_mvvm_livedata.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.utils.Constants

@Entity(tableName = Constants.DATABASE_TABLE_NAME)
data class Blog(
    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "author")
    var author: String,

    @ColumnInfo(name = "description")
    var description: String,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
