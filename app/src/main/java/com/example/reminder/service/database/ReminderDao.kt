package com.example.reminder.service.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.reminder.constants.Constants
import com.example.reminder.service.models.Reminder

@Dao
interface ReminderDao {
    @Insert
    suspend fun insertReminder(reminderEntity:Reminder):Long

    @Query("SELECT * FROM ${Constants.TABLE_NAME}")
     fun getAllReminders(): LiveData<List<Reminder>>

    @Delete
    suspend fun deleteReminder(reminderEntity: Reminder)

    @Update
    suspend fun updateReminder(reminderEntity: Reminder)
}