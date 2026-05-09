package ru.artem_torpedo.diabetesdiary.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.artem_torpedo.diabetesdiary.data.local.entity.ProfileEntity

@Dao
interface ProfileDao {

    @Insert
    suspend fun insert(profile: ProfileEntity)

    @Delete
    suspend fun delete(profile: ProfileEntity)

    @Query("SELECT * FROM profiles")
    suspend fun getAllProfiles(): List<ProfileEntity>

    @Query("SELECT * FROM profiles")
    suspend fun getAllProfilesDirect(): List<ProfileEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(profiles: List<ProfileEntity>)

    @Query("DELETE FROM profiles")
    suspend fun deleteAll()
}