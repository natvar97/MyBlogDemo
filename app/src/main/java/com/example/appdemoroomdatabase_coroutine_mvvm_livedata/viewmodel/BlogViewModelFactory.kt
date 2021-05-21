package com.example.appdemoroomdatabase_coroutine_mvvm_livedata.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.repository.BlogRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class BlogViewModelFactory(private val blogRepository : BlogRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BlogViewModel::class.java)) {
            return BlogViewModel(blogRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model class found")
    }
}