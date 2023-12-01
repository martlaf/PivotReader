package com.pivot.ui.article

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.pivot.ReaderApplication
import com.pivot.data.ArticleItem
import com.pivot.data.ArticlesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ArticleViewModel(articlesRepository: ArticlesRepository, savedStateHandle: SavedStateHandle): ViewModel() {
    val articleItem  = MutableStateFlow<ArticleItem?>(null)
    val id: Int = savedStateHandle["id"]!!

    init {
        Log.d("ArticleViewModel", "Getting article: $id")
        viewModelScope.launch(Dispatchers.IO) {
            val article = articlesRepository.getArticle(id)!!
            Log.d("ArticleViewModel", "Retrieved article from ${article.author}")
            articleItem.value = article
            Log.d("ArticleViewModel", "ViewModel initialized")

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
                val savedStateHandle = extras.createSavedStateHandle()

                return ArticleViewModel(
                    (application as ReaderApplication).container.articlesRepository,
                    savedStateHandle,
                ) as T
            }
        }
    }
}