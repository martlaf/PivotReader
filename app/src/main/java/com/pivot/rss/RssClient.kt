package com.pivot.rss

import com.pivot.data.ArticleItem
import com.prof18.rssparser.RssParser

class RssClient {
    val feedUrl: String = "https://pivot.quebec/feed/"

    suspend fun fetchRssData() : List<ArticleItem> {
        val parser = RssParser()
        val itemList = mutableListOf<ArticleItem>()

        val channel = parser.getRssChannel(url = feedUrl)

        for (item in channel.items) {
            val parsedUrl = item.guid!!.split("p=")
            val id = parsedUrl[parsedUrl.lastIndex].toInt()
            val articleItem = ArticleItem(
                id=id,
                title=item.title!!,
                imageUrl=item.image!!,
                content=item.content!!,
                date=item.pubDate!!.substring(0,item.pubDate!!.length-9),
                author=item.author!!,
                link=channel.link!!,
            )
            itemList.add(articleItem)
        }

        return itemList
    }
}
