package com.example.appdemoroomdatabase_coroutine_mvvm_livedata.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.entity.Blog
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.repository.BlogRepository
import kotlinx.coroutines.launch

class BlogViewModel(private val blogRepository: BlogRepository) : ViewModel() {

    fun insert(blog: Blog) = viewModelScope.launch {
        blogRepository.insert(blog)
    }

    fun update(blog: Blog) = viewModelScope.launch {
        blogRepository.update(blog)
    }

    fun delete(blog: Blog) = viewModelScope.launch {
        blogRepository.delete(blog)
    }

    fun getAllBlogs() = blogRepository.getAllBlogs().asLiveData()

}