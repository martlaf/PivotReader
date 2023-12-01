package com.pivot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.pivot.ui.article.ArticleScreen
import com.pivot.ui.feed.FeedScreen
import com.pivot.ui.settings.SettingsScreen
import com.pivot.ui.theme.PivotReaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PivotReaderTheme {
                PivotApplication()
            }
        }
    }
}

@Composable
fun PivotApplication() {
    val navController = rememberNavController()
    NavHost(navController=navController, startDestination = "rssFeed"){
        composable("rssFeed"){
            FeedScreen(navController)
        }
        composable("userSettings"){
            SettingsScreen(navController)
        }
        composable(
            "article/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType}),
            deepLinks = listOf(navDeepLink {
                    uriPattern = "app://pivot.quebec/?p={id}"
                }
            )
        ) {
            navBackStackEntry ->  ArticleScreen(navBackStackEntry.arguments!!.getInt("id"))
        }
    }
}