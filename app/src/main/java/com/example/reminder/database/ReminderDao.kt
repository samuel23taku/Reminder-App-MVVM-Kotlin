package com.example.reminder.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.reminder.constants.Constants
import com.example.reminder.models.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert
    suspend fun insert(remainderEntity:Reminder):Long

    @Query("SELECT * FROM ${Constants.TABLENAME}")
    fun getAllReminder(): Flow<List<Reminder>>
}