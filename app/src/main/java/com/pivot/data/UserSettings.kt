package com.pivot.data

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update


val DEFAULT_SETTINGS = listOf(
    UserSetting("notifications_enabled", "Activer les notifications", "false", "bool")
)

@Entity(tableName = "settings")
data class UserSetting (
    @PrimaryKey
    val key: String,
    val displayName: String,
    val value: String,
    val type: String,
)

@Dao
interface UserSettingDao {
    @Query("select * from settings where type = :type")
    fun getByType(type: String): List<UserSetting>

    @Update
    fun update(setting: UserSetting)

    @Insert
    fun insertSettings(settings: List<UserSetting>)
}
