package com.pivot.data

interface UserSettingsRepository {
    fun getByType(type: String): List<UserSetting>
    fun getAll(): List<UserSetting>
    fun update(setting: UserSetting)
    fun insertSettings(settings: Collection<UserSetting>)
}
