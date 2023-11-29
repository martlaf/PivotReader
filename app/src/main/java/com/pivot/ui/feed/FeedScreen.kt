package com.pivot.ui.feed

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import coil.compose.AsyncImage
import com.pivot.data.ArticleItem
import com.pivot.reader.R
import com.pivot.work.NotifierWorker
import java.util.concurrent.TimeUnit

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(navController: NavHostController){
    val viewModel: FeedViewModel = viewModel(factory=FeedViewModel.Factory)
    Scaffold(topBar = { AppBar() }) { innerPadding ->
        Surface(modifier=Modifier.padding(innerPadding)) {
            Column {
                NotifierButton()
                LazyColumn(verticalArrangement = Arrangement.SpaceBetween){
                    items(viewModel.feedState.value) {article ->
                        ArticleCard(article){
                            navController.navigate("article/${article.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotifierButton() {
    val application = LocalContext.current.applicationContext as Application
    val activity = LocalContext.current as Activity
    Button(onClick= {
//        ActivityCompat.requestPermissions(MainActivity.this, String[]{android.permission.POST_NOTIFICATIONS}, 1);

        requestPermissions(activity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            1,
        )
        val workManager = WorkManager.getInstance(application)
        val myWorkRequestBuilder = OneTimeWorkRequestBuilder<NotifierWorker>()
        myWorkRequestBuilder.setInputData(
                    workDataOf(
                        "ID" to 123,
                        "TITLE" to "Stuff. Definitely."
                    )
                )
        myWorkRequestBuilder.setInitialDelay(5, TimeUnit.SECONDS)
        workManager.enqueue(myWorkRequestBuilder.build())
    }) {
        Text("HELP!!")
    }
}
//private fun checkPermission(permission: String, requestCode: Int) {
//        if (ContextCompat.checkSelfPermission(this@MainActivity, permission) == PackageManager.PERMISSION_DENIED) {
//            // Requesting the permission
//            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
//        } else {
//            Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
//        }
//   }

@Composable
fun ArticleCard(article: ArticleItem, clickAction: () -> Unit) {
    Card(modifier= Modifier
        .padding(8.dp)
        .clickable(onClick = { clickAction.invoke() })) {
        Row {
            Card(modifier=Modifier.padding(8.dp).fillMaxHeight().requiredHeightIn(50.dp)) {
                AsyncImage(
                    model = article.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxHeight(),
                    contentScale = ContentScale.Crop,
                )
            }
            Column(modifier=Modifier.padding(8.dp)) {
                Text(text = article.title, modifier=Modifier.padding(8.dp), style=MaterialTheme.typography.bodyLarge)
                Text(text=article.author ,style=MaterialTheme.typography.bodyMedium, modifier=Modifier.padding(start=8.dp, top=8.dp))
                Text(text=article.date, style=MaterialTheme.typography.bodySmall, modifier=Modifier.padding(start=8.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    CenterAlignedTopAppBar(
        title = { Image(painterResource(id = R.drawable.pivot_logo), contentDescription = null) },
        navigationIcon = { Icon(Icons.Default.Home, contentDescription=null, Modifier.padding(12.dp)) },
//        modifier = Modifier.padding(8.dp)
//        backgroundColor = MaterialTheme.colorScheme.primary
    )
}

//@Preview(showBackground = true)
//@Composable
//fun FeedScreenPreview() {
//    FeedScreen()
//}