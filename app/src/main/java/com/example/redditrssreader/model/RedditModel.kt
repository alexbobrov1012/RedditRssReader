package com.example.redditrssreader.model

import androidx.core.text.HtmlCompat
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import java.text.DateFormat
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

object ModelConverter {
    private const val UNKNOWN_VALUE = "unknown"

    private val simpleDateFormat: DateFormat = DateFormat.getDateTimeInstance()

    fun convert(modelList: List<RedditModel>): List<RedditEntity> {
        return modelList.map { model ->
            RedditEntity(
                model.id,
                HtmlCompat.fromHtml(model.title ?: UNKNOWN_VALUE, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
                model.link?.href ?: UNKNOWN_VALUE,
                model.author?.name ?: UNKNOWN_VALUE,
                model.category?.term ?: UNKNOWN_VALUE,
                if (model.date != null) simpleDateFormat.format(model.date) else "",
                model.picture?.url
            )
        }
    }
}