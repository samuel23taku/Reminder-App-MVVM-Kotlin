package com.example.reminder.service.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.reminder.service.database.ReminderDatabase
import com.example.reminder.service.models.Reminder
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import com.example.reminder.service.database.ReminderDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

class ReminderRepository(private val reminderDao:ReminderDao) {
    private var reminders:List<Reminder> = mutableListOf()

    fun getAllReminders() :LiveData<List<Reminder>> = reminderDao.getAllReminders()

    suspend fun insertReminder(reminder:Reminder){
        reminderDao.insertReminder(reminder)
    }

    suspend fun deleteReminder(reminder:Reminder){
        reminderDao.deleteReminder(reminder)
    }

    suspend fun updateReminder(reminder:Reminder){
        reminderDao.updateReminder(reminder)
    }
}