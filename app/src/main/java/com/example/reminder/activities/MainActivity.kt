package com.example.reminder.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminder.adaptors.MainAdaptor
import com.example.reminder.databinding.ActivityMainBinding
import androidx.lifecycle.lifecycleScope
import com.example.reminder.database.ReminderDao
import com.example.reminder.database.ReminderDatabase
import com.example.reminder.models.Reminder
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        val dao = ReminderDatabase.getInstance(this).reminderDao()
        getReminders(dao)
        binding?.btnNewReminder?.setOnClickListener {
            val intent = Intent(this,NewReminder::class.java)
            startActivity(intent)
        }

    }
    private fun getReminders(reminderDao:ReminderDao){
        lifecycleScope.launch{
            reminderDao.getAllReminder().collect{ _reminders->
                if(_reminders.isNotEmpty()){
                    runOnUiThread {
                        setupRecyclerView(_reminders)
                    }
                    Log.e("Error","No reminders")
                }
            }
        }
    }




    private fun setupRecyclerView(theReminders:List<Reminder>){
        Log.e("Reminders!",theReminders.toString())
        binding?.rvRemainders?.layoutManager = LinearLayoutManager(this)
        val remaindersAdaptors = MainAdaptor(theReminders)
        binding?.rvRemainders?.adapter = remaindersAdaptors
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}