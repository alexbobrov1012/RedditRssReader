package com.example.redditrssreader.di

import com.example.redditrssreader.service.RedditService
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.converters.date.rfc3339.DateRfc3339TypeConverter
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import java.util.*
import javax.inject.Singleton

@Module
class RedditServiceModule {
    @Singleton
    @Provides
    fun redditService() : RedditService {
        return Retrofit.Builder()
            .baseUrl("https://www.reddit.com/")
            .addConverterFactory(
                TikXmlConverterFactory.create(
                    TikXml.Builder().exceptionOnUnreadXml(false)
                        .addTypeConverter(Date::class.java, DateRfc3339TypeConverter())
                        .build()
                )
            )
            .build()
            .create(RedditService::class.java)
    }
}