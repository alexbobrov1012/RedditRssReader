package com.example.redditrssreader.view

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.redditrssreader.R
import com.example.redditrssreader.data.RedditDiffCallback
import com.example.redditrssreader.databinding.NewsViewItemBinding
import com.example.redditrssreader.model.RedditEntity
import com.squareup.picasso.Picasso


class RedditAdapter : PagingDataAdapter<RedditEntity, RedditViewHolder>(RedditDiffCallback) {
    override fun onBindViewHolder(holder: RedditViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_view_item, parent, false)
        return RedditViewHolder(view)
    }
}

class RedditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = NewsViewItemBinding.bind(itemView)
    private var item: RedditEntity? = null
    init {
        itemView.setOnClickListener {
            if (binding.containerDescription.isVisible) {
                binding.containerDescription.visibility = View.GONE
            } else {
                binding.containerDescription.visibility = View.VISIBLE
            }
        }
        binding.tvDescriptionLink.setOnClickListener {
            item?.link?.let {link ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                itemView.context.startActivity(intent)
            }
        }
    }

    fun bind(entity: RedditEntity?) {
        this.item = entity
        item?.let {
            binding.tvTitle.text = it.title
            binding.tvDate.text = it.date
            binding.tvDescriptionText.text = String.format(
                binding.tvDescriptionText.text.toString(),
                it.author, 
                it.category
            )
            if (it.pictureUrl?.startsWith("http") == true) {
                binding.imageViewPicture.visibility = View.VISIBLE
                Picasso.with(binding.imageViewPicture.context)
                    .load(it.pictureUrl)
                    .placeholder(R.drawable.ic_picture_placeholder)
                    .into(binding.imageViewPicture)
            } else {
                binding.imageViewPicture.visibility = View.GONE
            }
        }
    }
}