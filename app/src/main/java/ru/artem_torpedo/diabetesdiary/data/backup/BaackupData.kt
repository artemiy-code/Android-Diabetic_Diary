package ru.artem_torpedo.diabetesdiary.data.backup

import ru.artem_torpedo.diabetesdiary.data.local.entity.*

data class BackupData(
    val version: Int,
    val exportTime: Long,
    val profiles: List<ProfileEntity>,
    val measurements: List<MeasurementEntity>,
    val products: List<ProductEntity>,
    val foodEntries: List<FoodEntryEntity>,
    val reminders: List<ReminderEntity>,
)