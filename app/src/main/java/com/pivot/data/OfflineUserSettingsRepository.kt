package com.pivot.data


class OfflineUserSettingsRepository(private val userSettingDao: UserSettingDao) : UserSettingsRepository {
    override fun getByType(type: String): List<UserSetting> = userSettingDao.getByType(type)
    override fun getAll(): List<UserSetting> = userSettingDao.getAll()
    override fun update(setting: UserSetting) = userSettingDao.update(setting)
    override fun insertSettings(settings: Collection<UserSetting>) = userSettingDao.insertSettings(settings)
}
