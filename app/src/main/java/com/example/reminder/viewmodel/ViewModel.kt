package com.example.reminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.reminder.service.database.ReminderDatabase
import com.example.reminder.service.models.Reminder
import com.example.reminder.service.repository.ReminderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application:Application) :AndroidViewModel(application) {
    var allReminders:LiveData<List<Reminder>>
    var repository:ReminderRepository

    init {
        val dao = ReminderDatabase.getInstance(application).reminderDao()
        repository = ReminderRepository(dao)
        allReminders = repository.getAllReminders()
    }

    fun insertReminder(reminder:Reminder){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertReminder(reminder)
        }
    }

    fun deleteReminder(reminder:Reminder){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteReminder(reminder)
        }
    }

    fun updateReminder(reminder:Reminder){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateReminder(reminder)
        }
    }

}