package com.example.redditrssreader.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import java.util.*

@Xml
data class RedditFeedModel(@Element(name = "entry") val newsList: List<RedditModel>)

@Xml
data class RedditModel(
    @PropertyElement
    val id: String,
    @Element
    val author: RedditAuthor?,
    @Element
    val category: RedditCategory?,
    @PropertyElement
    val title: String?,
    @Element
    val link: RedditLink?,
    @PropertyElement(name = "updated")
    val date: Date?,
    @Element
    val picture: RedditThumbnail?
)

@Xml(name = "author")
data class RedditAuthor(@PropertyElement val name: String?)

@Xml(name = "category")
data class RedditCategory(@Attribute val term: String?)

@Xml(name = "link")
data class RedditLink(@Attribute val href: String?)

@Xml(name = "media:thumbnail")
data class RedditThumbnail(@Attribute val url: String?)

@Entity(tableName = "reddit_table")
data class RedditEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val link: String,
    val author: String,
    val category: String,
    val date: String,
    val pictureUrl: String?
)