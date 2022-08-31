package com.example.reminder.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminder.constants.Constants
import com.example.reminder.view.adapters.ReminderAdapter
import com.example.reminder.databinding.ActivityMainBinding
import com.example.reminder.service.models.Reminder
import com.example.reminder.view.adapters.DeleteReminderInterface
import com.example.reminder.view.adapters.ReminderClickedInterface
import com.example.reminder.view.adapters.OnReminderCheckBoxClickedInterface
import com.example.reminder.viewmodel.ViewModel

class MainActivity : AppCompatActivity(),ReminderClickedInterface,DeleteReminderInterface,OnReminderCheckBoxClickedInterface{
    private var binding : ActivityMainBinding? = null
    private lateinit var viewModel: ViewModel
    private lateinit var reminderAdapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)


        viewModel = ViewModelProvider(
            this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ViewModel::class.java]
        reminderAdapter = ReminderAdapter(this,this,this)
        initializeView()
        observeEvents()
    }

    private fun initializeView(){
        binding?.btnNewReminder?.setOnClickListener {
            val intent = Intent(this, NewReminder::class.java)
            startActivity(intent)
        }
        binding?.rvRemainders?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = reminderAdapter
        }
    }

    private fun observeEvents(){
        viewModel.allReminders.observe(this, Observer { list->
            reminderAdapter.updateReminders(list)
        })
    }

    override fun onReminderClick(reminder: Reminder) {
        val intent = Intent(this, NewReminder::class.java)
        intent.putExtra(Constants.EXTRA_NEW_EDIT_REMINDER,reminder)
        startActivity(intent)
    }

    override fun onClickDeleteReminder(reminder: Reminder) {
        viewModel.deleteReminder(reminder)
    }

    override fun onReminderCheckBoxClick(reminder: Reminder) {
        viewModel.updateReminder(reminder)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}