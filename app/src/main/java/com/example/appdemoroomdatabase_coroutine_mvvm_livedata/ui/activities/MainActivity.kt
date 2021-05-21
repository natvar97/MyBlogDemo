package com.example.appdemoroomdatabase_coroutine_mvvm_livedata.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.R
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.databinding.ActivityMainBinding
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.entity.Blog
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.ui.adapters.BlogRecyclerAdapter
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.utils.BlogApplication
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.viewmodel.BlogViewModel
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.viewmodel.BlogViewModelFactory

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var blogRecyclerAdapter: BlogRecyclerAdapter
    private val blogViewModel: BlogViewModel by viewModels {
        BlogViewModelFactory((application as BlogApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        blogRecyclerAdapter = BlogRecyclerAdapter(this@MainActivity)
        mBinding.recyclerViewMain.layoutManager = LinearLayoutManager(this@MainActivity)
        mBinding.recyclerViewMain.adapter = blogRecyclerAdapter

        blogViewModel.getAllBlogs().observe(this, Observer {
            if(it.isEmpty()){
                mBinding.initialText.visibility = View.VISIBLE
                mBinding.recyclerViewMain.visibility = View.GONE
            } else {
                mBinding.initialText.visibility = View.GONE
                mBinding.recyclerViewMain.visibility = View.VISIBLE
                blogRecyclerAdapter.setData(it)
            }
        })
        mBinding.fab.setOnClickListener(this)

    }

    fun deleteBlog(blog : Blog) {
        blogViewModel.delete(blog)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.fab -> {
                    startActivity(Intent(this@MainActivity, AddNewBlog::class.java))
                }
            }
        }
    }
}