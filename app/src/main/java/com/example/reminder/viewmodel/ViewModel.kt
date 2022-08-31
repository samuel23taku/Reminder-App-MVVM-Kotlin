package com.example.reminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.reminder.service.database.ReminderDatabase
import com.example.reminder.service.models.Reminder
import com.example.reminder.service.worker_notification.NotificationServiceWorker
import com.example.reminder.service.repository.ReminderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ViewModel(application:Application) :AndroidViewModel(application) {
    var allReminders:LiveData<List<Reminder>>
    private var repository:ReminderRepository
    init {
        val dao = ReminderDatabase.getInstance(application).reminderDao()
        repository = ReminderRepository(dao)
        allReminders = repository.getAllReminders()
    }

    fun insertReminder(reminder:Reminder){
        addNotification(reminder)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertReminder(reminder)
        }
    }

    fun deleteReminder(reminder:Reminder){
        cancelNotification(reminder)
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteReminder(reminder)
        }
    }

    fun updateReminder(reminder:Reminder){
        if(reminder.isCompleted == 1){
            cancelNotification(reminder)
        }else {
            cancelNotification(reminder)//cancel previous workers and add a new one
            addNotification(reminder)
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.updateReminder(reminder)
        }

    }

    private fun cancelNotification(reminder:Reminder){
        WorkManager.getInstance(getApplication()).cancelAllWorkByTag("notification-${reminder.title}-${reminder.time}")
    }

    private fun addNotification(reminder:Reminder){
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        cal.time = sdf.parse(reminder.time)!!

        val data = Data.Builder()
        data.putString("title",reminder.title)
        data.putString("subtitle",reminder.subtitle)
        data.putInt("notificationId",reminder.reminderId)
        val exec_time = cal.timeInMillis - Calendar.getInstance().timeInMillis
        val work =
            OneTimeWorkRequestBuilder<NotificationServiceWorker>()
                .setInitialDelay(exec_time, TimeUnit.MILLISECONDS)
                .addTag("notification-${reminder.title}-${reminder.time}")

        work.setInputData(data.build())

        WorkManager.getInstance(getApplication()).enqueue(work.build())
    }

}