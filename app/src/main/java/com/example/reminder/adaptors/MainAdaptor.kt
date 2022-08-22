package com.example.reminder.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reminder.databinding.ItemReminderBinding
import com.example.reminder.models.Reminder

class MainAdaptor(private val reminders:List<Reminder>) : RecyclerView.Adapter<MainAdaptor.CustomViewHolder>() {
    inner class CustomViewHolder(binding: ItemReminderBinding) : RecyclerView.ViewHolder(binding.root){
        val remainder = binding.tvTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(ItemReminderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.remainder.text = reminder.title
    }

    override fun getItemCount(): Int {
        return reminders.count()
    }
}