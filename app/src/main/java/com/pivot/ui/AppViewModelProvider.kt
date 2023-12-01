package com.pivot.ui
//
//import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
//
//import androidx.lifecycle.viewmodel.CreationExtras
//import androidx.lifecycle.viewmodel.initializer
//import androidx.lifecycle.viewmodel.viewModelFactory
//import com.pivot.BaseApplication
//import com.pivot.ui.article.ArticleViewModel
//import com.pivot.ui.feed.FeedViewModel

//object AppViewModelProvider {
//    val Factory = viewModelFactory {
//        initializer {
//            FeedViewModel(
//                baseApplication().container.articlesRepository
//            )
//        }

//        initializer {
//            ArticleViewModel(
//                baseApplication().container.articlesRepository
//            )
//        }
//    }
//}


//fun CreationExtras.baseApplication(): BaseApplication =
//    (this[AndroidViewModelFactory.APPLICATION_KEY] as BaseApplication)
