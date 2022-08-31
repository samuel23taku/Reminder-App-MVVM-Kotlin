package com.example.reminder.service.repository

import androidx.lifecycle.LiveData
import com.example.reminder.service.models.Reminder
import com.example.reminder.service.database.ReminderDao

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