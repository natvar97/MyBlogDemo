package com.example.appdemoroomdatabase_coroutine_mvvm_livedata.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.databinding.BlogListItemBinding
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.entity.Blog

class BlogRecyclerAdapter :
    RecyclerView.Adapter<BlogRecyclerAdapter.BlogRecyclerViewHolder>() {

    private var blogsList = ArrayList<Blog>()

    class BlogRecyclerViewHolder(itemView: BlogListItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val tvTitle = itemView.tvTitle
        val tvAuthor = itemView.tvAuthor
        val tvDescription = itemView.tvDescription
        val ivBlogImage = itemView.ivBlogImage

        fun bind(blog: Blog) {
            tvTitle.text = blog.title
            tvAuthor.text = blog.author
            tvDescription.text = blog.description
            Glide.with(itemView.context)
                .load(blog.image)
                .into(ivBlogImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogRecyclerViewHolder {
        val view = BlogListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BlogRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlogRecyclerViewHolder, position: Int) {
        val blog = blogsList[position]
        holder.bind(blog)
    }

    override fun getItemCount(): Int {
        return blogsList.size
    }

    fun setData(list: List<Blog>) {
        blogsList.clear()
        blogsList.addAll(list)
        notifyDataSetChanged()
    }

}