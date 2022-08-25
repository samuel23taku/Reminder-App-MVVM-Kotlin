package com.example.reminder.view.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.lifecycle.lifecycleScope
import com.example.reminder.service.database.ReminderDao
import com.example.reminder.service.database.ReminderDatabase
import com.example.reminder.databinding.ActivityNewReminderBinding
import com.example.reminder.service.models.Reminder
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NewReminder : AppCompatActivity() {
    private var binding: ActivityNewReminderBinding? = null
    private var date:Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewReminderBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        binding?.timePick?.setIs24HourView(true)

        setupDatePicker()
        setupTimePicker()

        binding?.btnCreateEditReminder?.setOnClickListener {
            if (validateText()) {
                val dao = ReminderDatabase.getInstance(this).reminderDao()

                addReminder(dao)
            }
        }

    }

    private fun setupDatePicker(){
        val picker = binding?.datePick
        picker?.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            date?.set(year,monthOfYear,dayOfMonth)
        }
    }

    private fun setupTimePicker(){
        val picker = binding?.timePick
        picker?.setOnTimeChangedListener { view, hourOfDay, minute ->
            date?.set(hourOfDay,minute)
        }
    }


    private fun addReminder(reminderDao: ReminderDao) {
        val title = binding!!.tvTitle.editText?.text.toString()
        val subtitle = binding!!.tvSubTitle.editText?.text.toString()
        val dateTime = date.time

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)

//        lifecycleScope.launch {
//            val returnValue = reminderDao.insert(Reminder(0, title, subtitle, date.toString()))
//
//            Log.e("Return Value is ", returnValue.toString())
//        }
    }

    private fun validateText(): Boolean {
        return if (binding!!.tvTitle.isEmpty()) {
            Toast.makeText(this, "Title can't be empty", Toast.LENGTH_LONG).show()
            false
        } else if (binding!!.tvSubTitle.isEmpty()) {
            Toast.makeText(this, "Subtitle can't be empty", Toast.LENGTH_LONG).show()
            false
        } else {
            true
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}