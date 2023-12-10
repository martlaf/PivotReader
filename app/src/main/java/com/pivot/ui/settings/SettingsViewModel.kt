package com.pivot.ui.settings

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.pivot.ReaderApplication
import com.pivot.data.AppContainer
import com.pivot.data.UserSetting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(container: AppContainer): ViewModel() {
    val settingsState = MutableStateFlow<MutableMap<String, UserSetting>>(mutableMapOf<String, UserSetting>())

    init {
        Log.d("SettingsViewModel", "In SettingsViewModel")
        viewModelScope.launch(Dispatchers.IO){
            Log.d("SettingsViewModel", "In coroutine")
            val settingsList = container.settingsRepository.getAll()
            for (item in settingsList) {
                Log.d("SettingsViewModel", "Found setting ${item.key}")
                settingsState.value[item.key] = item
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

                return SettingsViewModel(
                    (application as ReaderApplication).container,
                ) as T
            }
        }
    }
}