package com.example.reminder.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reminder.databinding.ItemReminderBinding
import com.example.reminder.service.models.Reminder
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*

class MainAdapter(private val reminders: List<Reminder>) :
    RecyclerView.Adapter<MainAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(binding: ItemReminderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvTitle
        val subtitle = binding.tvSubtitle
        val time = binding.tvDateTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            ItemReminderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val reminder = reminders[position]
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        cal.time = sdf.parse(reminder.time)!!


        val month = Month.of(cal.time.month).toString().lowercase()
        val time = "${cal.time.hours}:${cal.time.minutes}"
        val fullDate = "${
            month.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        } ${cal.time.date} $time "


        holder.time.text = fullDate
        holder.title.text = reminder.title
        holder.subtitle.text = reminder.subtitle
    }

    override fun getItemCount(): Int {
        return reminders.count()
    }
}