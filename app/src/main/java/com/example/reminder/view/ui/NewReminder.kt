package com.example.reminder.view.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.reminder.constants.Constants
import com.example.reminder.databinding.ActivityNewReminderBinding
import com.example.reminder.service.models.Reminder
import com.example.reminder.viewmodel.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class NewReminder : AppCompatActivity() {
    private var binding: ActivityNewReminderBinding? = null
    private var date: Calendar = Calendar.getInstance()
    private lateinit var viewModel: ViewModel
    private var reminder: Reminder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewReminderBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ViewModel::class.java]

        didPreviousIntentParseData()
        setupDatePicker()
        setupTimePicker()
        initializeView()
    }

    private fun didPreviousIntentParseData() {
        if (intent.hasExtra(Constants.EXTRA_NEW_EDIT_REMINDER)) {
            binding?.btnCreateEditReminder?.text = "Finish Edit"
            reminder = intent.getSerializableExtra(Constants.EXTRA_NEW_EDIT_REMINDER) as Reminder
            binding?.tvTitle?.editText?.setText(reminder!!.title)
            binding?.tvSubTitle?.editText?.setText(reminder!!.subtitle)


            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
            cal.time = sdf.parse(reminder!!.time)!!
            binding?.timePick?.minute = cal.get(Calendar.MINUTE)
            binding?.timePick?.hour = cal.get(Calendar.HOUR_OF_DAY)
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            binding?.datePick?.init(year, month, day, null)

        }
    }


    private fun initializeView() {
        binding?.timePick?.setIs24HourView(true)
        binding?.btnBack?.setOnClickListener {
            finish()
        }
        binding?.btnCreateEditReminder?.setOnClickListener {
            val isTextValidate = validateText()
            if (isTextValidate) {
                val title = binding!!.tvTitle.editText?.text.toString()
                val subtitle = binding!!.tvSubTitle.editText?.text.toString()
                val dateTime = date.time

                val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
                val date = sdf.format(dateTime)
                if (intent.hasExtra(Constants.EXTRA_NEW_EDIT_REMINDER)) {
                    viewModel.updateReminder(
                        Reminder(
                            reminder!!.reminderId,
                            title,
                            subtitle,
                            date.toString(),
                            reminder!!.isCompleted
                        )
                    )
                } else {
                    viewModel.insertReminder(Reminder(0, title, subtitle, date.toString(), 0))
                }
                finish()
            }
        }
    }


    private fun setupDatePicker() {
        val picker = binding?.datePick
        picker?.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            date.set(year, monthOfYear, dayOfMonth)
        }
    }

    private fun setupTimePicker() {
        val picker = binding?.timePick
        picker?.setOnTimeChangedListener { _, hourOfDay, minute ->
            date.set(Calendar.HOUR_OF_DAY, hourOfDay)
            date.set(Calendar.MINUTE, minute)
        }
    }


    private fun validateText(): Boolean {
        return if (binding!!.tvTvTitle.text != null && binding!!.tvTvTitle.text!!.isEmpty()) {
            Toast.makeText(this, "Title can't be empty", Toast.LENGTH_LONG).show()
            false
        } else if (binding!!.tvTvSubtitle.text != null && binding!!.tvTvSubtitle.text!!.isEmpty()) {
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