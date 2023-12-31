package com.pivot.ui.article

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

class XmlParser {

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<*> {
        inputStream.use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            return read(parser)
        }
    }

    // We don't use namespaces.
    private val ns: String? = null

    @Throws(XmlPullParserException::class, IOException::class)
    private fun read(parser: XmlPullParser): List<Entry> {
        val entries = mutableListOf<Entry>()

        parser.require(XmlPullParser.START_TAG, ns, "feed")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // Starts by looking for the entry tag.
            if (parser.name == "entry") {
//                entries.add(readEntry(parser))
            } //else {
//                skip(parser)
//            }
        }
        return entries
    }

    data class Entry(val title: String?, val summary: String?, val link: String?)

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
//    @Throws(XmlPullParserException::class, IOException::class)
//    private fun readEntry(parser: XmlPullParser): Entry {
//        parser.require(XmlPullParser.START_TAG, ns, "entry")
//        var title: String? = null
//        var summary: String? = null
//        var link: String? = null
//        while (parser.next() != XmlPullParser.END_TAG) {
//            if (parser.eventType != XmlPullParser.START_TAG) {
//                continue
//            }
//            when (parser.name) {
//                "title" -> title = readTitle(parser)
//                "summary" -> summary = readSummary(parser)
//                "link" -> link = readLink(parser)
//                else -> skip(parser)
//            }
//        }
//        return Entry(title, summary, link)
//    }

//    // Processes title tags in the feed.
//    @Throws(IOException::class, XmlPullParserException::class)
//    private fun readTitle(parser: XmlPullParser): String {
//        parser.require(XmlPullParser.START_TAG, ns, "title")
//        val title = readText(parser)
//        parser.require(XmlPullParser.END_TAG, ns, "title")
//        return title
//    }

//    // Processes link tags in the feed.
//    @Throws(IOException::class, XmlPullParserException::class)
//    private fun readLink(parser: XmlPullParser): String {
//        var link = ""
//        parser.require(XmlPullParser.START_TAG, ns, "link")
//        val tag = parser.name
//        val relType = parser.getAttributeValue(null, "rel")
//        if (tag == "link") {
//            if (relType == "alternate") {
//                link = parser.getAttributeValue(null, "href")
//                parser.nextTag()
//            }
//        }
//        parser.require(XmlPullParser.END_TAG, ns, "link")
//        return link
//    }

    // Process hyperlinks
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readHyperlink(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "a")
        val hyperlink = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "a")
        return hyperlink
    }

    // Process paragraphs
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readParagraph(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "p")
        val paragraph = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "p")
        return paragraph
    }


//    // Processes summary tags in the feed.
//    @Throws(IOException::class, XmlPullParserException::class)
//    private fun readSummary(parser: XmlPullParser): String {
//        parser.require(XmlPullParser.START_TAG, ns, "summary")
//        val summary = readText(parser)
//        parser.require(XmlPullParser.END_TAG, ns, "summary")
//        return summary
//    }

    // For the tags title and summary, extracts their text values.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

//    @Throws(XmlPullParserException::class, IOException::class)
//    private fun skip(parser: XmlPullParser) {
//        if (parser.eventType != XmlPullParser.START_TAG) {
//            throw IllegalStateException()
//        }
//        var depth = 1
//        while (depth != 0) {
//            when (parser.next()) {
//                XmlPullParser.END_TAG -> depth--
//                XmlPullParser.START_TAG -> depth++
//            }
//        }
//    }
}


