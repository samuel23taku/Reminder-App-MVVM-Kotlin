package com.example.reminder.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reminder.databinding.ItemReminderBinding
import com.example.reminder.service.models.Reminder
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*

class ReminderAdapter(
    private val deleteReminderInterface: DeleteReminderInterface,
    private val reminderClickedInterface: ReminderClickInterface,
    private val reminderCheckBoxClickInterface: OnReminderCheckBoxClickInterface
) :
    RecyclerView.Adapter<ReminderAdapter.CustomViewHolder>() {

    private var reminders: ArrayList<Reminder> = ArrayList()

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

        val monthNum = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        val month = Month.of(monthNum).toString().lowercase()
        val time = "${hour}:${minute}"
        val fullDate = "${
            month.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        } $day $time "

        holder.deleteReminder.setOnClickListener {
            deleteReminderInterface.onClickDeleteReminder(reminder)
        }

        holder.card.setOnClickListener {
            reminderClickedInterface.onReminderClick(reminder)
        }
        holder.reminderCheckBox.setOnCheckedChangeListener { _, isChecked ->
            val newReminder = Reminder(reminder.reminderId,reminder.title,reminder.subtitle,reminder.time,if(isChecked)1 else 0 )
            reminderCheckBoxClickInterface.onReminderCheckBoxClick(newReminder)
        }

        if(reminder.isCompleted ==1){
            holder.reminderCheckBox.isChecked = true
        }
        holder.time.text = fullDate
        holder.title.text = reminder.title
        holder.subtitle.text = reminder.subtitle
    }

    fun updateReminders(newReminders: List<Reminder>) {
        reminders.clear()
        reminders.addAll(newReminders)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return reminders.count()
    }

    inner class CustomViewHolder(binding: ItemReminderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvTitle
        val subtitle = binding.tvSubtitle
        val time = binding.tvDateTime
        val deleteReminder = binding.btnDeleteReminder
        val card = binding.reminderCard
        val reminderCheckBox = binding.reminderCheckBox
    }
}

interface DeleteReminderInterface {
    fun onClickDeleteReminder(reminder: Reminder)
}

interface ReminderClickInterface {
    fun onReminderClick(reminder: Reminder)
}

interface OnReminderCheckBoxClickInterface{
    fun onReminderCheckBoxClick(reminder:Reminder)
}