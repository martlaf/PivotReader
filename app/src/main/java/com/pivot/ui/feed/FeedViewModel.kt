package com.pivot.ui.feed

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.pivot.base.BaseApplication
import com.pivot.data.AppContainer
import com.pivot.data.ArticleItem
import com.pivot.rss.RssClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class FeedViewModel(container: AppContainer): ViewModel() {
    val feedState = mutableStateOf(value=emptyList<ArticleItem>())

    init {
        viewModelScope.launch(Dispatchers.IO){
            feedState.value = RssClient().fetchRssData()
            for (article in feedState.value) {
                Log.d("FeedViewModel", "Storing article: ${article.id}")
                container.articlesRepository.insertArticle(article)
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
//                val savedStateHandle = extras.createSavedStateHandle()

                return FeedViewModel(
                    (application as BaseApplication).container,
                ) as T
            }
        }
    }
}
