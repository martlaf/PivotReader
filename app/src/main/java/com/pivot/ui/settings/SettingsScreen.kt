package com.pivot.ui.settings

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pivot.reader.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController, viewModel: SettingsViewModel = viewModel(factory=SettingsViewModel.Factory)) {
    val activity = LocalContext.current as Activity
    requestPermissions(activity,
        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
        0,
    )

    Scaffold(topBar = { AppBar(navController) }) { innerPadding ->
        Surface(modifier=Modifier.padding(innerPadding)) {
            Column { viewModel.settingsState.value["notifications_enabled"]?.displayName?.let {
                Text(
                    it
                )
            } }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavHostController) {
    CenterAlignedTopAppBar(
        title = { Image(painterResource(id = R.drawable.pivot_logo), contentDescription = null) },
        navigationIcon = { Icon(Icons.Default.Home, contentDescription=null,
            Modifier
                .padding(12.dp)
                .clickable {
                    navController.navigate("rssFeed")
                })
                         },
    )
}