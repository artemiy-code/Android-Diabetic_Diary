package ru.artem_torpedo.diabetesdiary.data.backup

import android.content.Context
import com.google.gson.GsonBuilder
import ru.artem_torpedo.diabetesdiary.data.local.AppDatabase
import androidx.room.withTransaction

class BackupManager(
    context: Context,
) {

    private val db = AppDatabase.getDatabase(context)

    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    suspend fun exportToStream(outputStream: java.io.OutputStream) {

        val backupData = BackupData(
            version = 1,
            exportTime = System.currentTimeMillis(),
            profiles = db.profileDao().getAllProfilesDirect(),
            measurements = db.measurementDao().getAllDirect(),
            products = db.productDao().getAllDirect(),
            foodEntries = db.foodEntryDao().getAllDirect(),
            reminders = db.reminderDao().getAllDirect()
        )

        val json = gson.toJson(backupData)

        outputStream.bufferedWriter().use {
            it.write(json)
        }
    }

    suspend fun importFromStream(inputStream: java.io.InputStream) {

        val json = inputStream.bufferedReader().use {
            it.readText()
        }

        val backupData = gson.fromJson(
            json,
            BackupData::class.java
        )

        if (backupData.version != 1) {
            throw IllegalStateException("Unsupported backup version")
        }

        db.withTransaction {

            db.foodEntryDao().deleteAll()
            db.measurementDao().deleteAll()
            db.reminderDao().deleteAll()
            db.productDao().deleteAll()
            db.profileDao().deleteAll()

            db.profileDao().insertAll(backupData.profiles)
            db.productDao().insertAll(backupData.products)
            db.measurementDao().insertAll(backupData.measurements)
            db.foodEntryDao().insertAll(backupData.foodEntries)
            db.reminderDao().insertAll(backupData.reminders)
        }
    }
}