package com.example.reminder.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminder.view.adapters.MainAdapter
import com.example.reminder.databinding.ActivityMainBinding
import com.example.reminder.service.database.ReminderDao
import com.example.reminder.service.database.ReminderDatabase
import com.example.reminder.service.models.Reminder
import com.example.reminder.viewmodel.ViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var binding : ActivityMainBinding? = null
    lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ViewModel::class.java]

        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        binding?.btnNewReminder?.setOnClickListener {
            val intent = Intent(this, NewReminder::class.java)
            startActivity(intent)
        }

    }


    private fun setupRecyclerView(theReminders:List<Reminder>){
        Log.e("Reminders!",theReminders.toString())
        binding?.rvRemainders?.layoutManager = LinearLayoutManager(this)
        val remaindersAdaptors = MainAdapter(theReminders)
        binding?.rvRemainders?.adapter = remaindersAdaptors
    }



}