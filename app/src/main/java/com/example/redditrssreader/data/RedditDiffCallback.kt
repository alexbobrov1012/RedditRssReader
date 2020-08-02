package com.example.redditrssreader.data

import androidx.recyclerview.widget.DiffUtil
import com.example.redditrssreader.model.RedditEntity

object RedditDiffCallback : DiffUtil.ItemCallback<RedditEntity>(){
    override fun areItemsTheSame(oldItem: RedditEntity, newItem: RedditEntity) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RedditEntity, newItem: RedditEntity) =
        oldItem == newItem
}