package com.example.appdemoroomdatabase_coroutine_mvvm_livedata.ui.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.R
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.databinding.BlogListItemBinding
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.entity.Blog
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.ui.activities.AddNewBlog
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.ui.activities.MainActivity
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.utils.Constants

class BlogRecyclerAdapter(private val activity : Activity) :
    RecyclerView.Adapter<BlogRecyclerAdapter.BlogRecyclerViewHolder>() {

    private var blogsList = ArrayList<Blog>()

    class BlogRecyclerViewHolder(itemView: BlogListItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val tvTitle = itemView.tvTitle
        val tvAuthor = itemView.tvAuthor
        val tvDescription = itemView.tvDescription
        val ivBlogImage = itemView.ivBlogImage
        val ivMore = itemView.ivMore
        val cardViewItem = itemView.cardViewItem
        val layout = itemView.layout

        fun bind(blog: Blog) {
            tvTitle.text = blog.title
            tvAuthor.text = blog.author
            tvDescription.text = blog.description
            Glide.with(itemView.context)
                .load(blog.image)
                .centerCrop()
//                .listener(object : RequestListener<Drawable> {
//                    override fun onLoadFailed(
//                        e: GlideException?,
//                        model: Any?,
//                        target: Target<Drawable>?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        return false
//                    }
//
//                    override fun onResourceReady(
//                        resource: Drawable?,
//                        model: Any?,
//                        target: Target<Drawable>?,
//                        dataSource: DataSource?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        resource?.let {
//                            Palette.from(resource.toBitmap()).generate() { palette ->
//                                val intColor = palette?.vibrantSwatch?.rgb ?: 0
//                                cardViewItem.setCardBackgroundColor(intColor)
//                            }
//                        }
//                        return false
//                    }
//
//                })
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

        holder.ivMore.setOnClickListener {
            val popMenu = PopupMenu(activity.applicationContext , holder.ivMore)
            popMenu.menuInflater.inflate(R.menu.menu_more , popMenu.menu)

            popMenu.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_edit) {
                    val intent = Intent(activity , AddNewBlog::class.java)
                    intent.putExtra(Constants.EXTRA_BLOG_DETAILS , blog)
                    activity.startActivity(intent)
                }
                else if (it.itemId == R.id.action_delete) {
                    if (activity is MainActivity){
                        activity.deleteBlog(blog)
                    }
                }
                true
            }
            popMenu.show()
        }
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