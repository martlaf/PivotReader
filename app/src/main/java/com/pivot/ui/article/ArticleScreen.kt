package com.pivot.ui.article

import android.annotation.SuppressLint
import android.text.Html
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.SpanStyle
//import androidx.compose.ui.text.buildAnnotatedString
//import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.parseAsHtml
import coil.compose.AsyncImage
//import com.pivot.ui.AppViewModelProvider

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(id: Int, viewModel: ArticleViewModel = viewModel(factory=ArticleViewModel.Factory)){
    val article = viewModel.articleItem.collectAsState().value
    val scrollState = rememberScrollState(0)
    var content = article?.content
    content = content?.replace("<img .* />".toRegex(), "")
    content = content?.replace("<figure .*</figure>".toRegex(), "")
    content = content?.replace("<p>The post .*</p>".toRegex(), "")
    Scaffold {
        Surface(modifier = Modifier.fillMaxSize().verticalScroll(scrollState)) {
            Column{
                if (article != null) {
                    AsyncImage(
                        model = article.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier=Modifier.fillMaxWidth()
                    )
                    Text(article.title, style=MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(8.dp))
                    Text(article.author, style=MaterialTheme.typography.bodyLarge, modifier=Modifier.padding(top=8.dp, start=8.dp, bottom=4.dp))
                    Text(article.date, style=MaterialTheme.typography.bodySmall, modifier = Modifier.padding(bottom=8.dp, start=8.dp))
                    Text(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY).toString(), style=MaterialTheme.typography.bodyMedium, modifier=Modifier.padding(8.dp))
                }
            }
        }
    }
}

//@Composable
//fun ChunkHttp(string: String) {
//
//}

@Preview
@Composable
fun ParagraphViewer() {
    var string = "<p>Après qu’une frappe ait touché l’hôpital Al-Ahli à Gaza le 17&nbsp;octobre dernier, les deux belligérants se sont pressés de se jeter la faute, laissant les journalistes dans un brouillard d’information sans précédent. Des médias et des agences de presse reconnus, comme le <a target=\"_blank\" href=\"https://www.nytimes.com/2023/10/23/pageoneplus/editors-note-gaza-hospital-coverage.html\">New York Times</a>, ont instantanément diffusé de l’information qui ne peut toujours pas être prouvée à ce jour. En effet, même les preuves de l’État israélien, acceptées par plusieurs médias et gouvernements occidentaux, <a href=\"https://pivot.quebec/2023/10/23/les-manifestant%c2%b7es-pro-palestine-denoncent-lhypocrisie-des-gouvernements-occidentaux/\">sont contestées par certaines analyses</a>.</p>"
    assert("^<p>.*</p>$".toRegex().matches(string))
    string = string.substring(3, string.length-4)
    val hyperlinks_idx = "<a .*</a>".toRegex().findAll(string).map { it.range.start }.toList()
    Text(string.parseAsHtml().toString(), color=MaterialTheme.colorScheme.primary)
}


//@Composable
//fun HttpParagraph(string: String) {
//    val annotatedString = buildAnnotatedString {
//        append("By joining, you agree to the ")
//        pushStringAnnotation(tag = "policy", annotation = "https://google.com/policy")
//        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
//            append("privacy policy")
//        }
//        pop()
//        append(" and ")
//        pushStringAnnotation(tag = "terms", annotation = "https://google.com/terms")
//        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
//            append("terms of use")
//        }
//        pop()
//    }
//    ClickableText(text = annotatedString, style = MaterialTheme.typography.bodySmall, onClick = { offset ->
//        annotatedString.getStringAnnotations(tag = "policy", start = offset, end = offset).firstOrNull()?.let {
//            Log.d("policy URL", it.item)
//        }
//        annotatedString.getStringAnnotations(tag = "terms", start = offset, end = offset).firstOrNull()?.let {
//            Log.d("terms URL", it.item)
//        }
//    })
//}