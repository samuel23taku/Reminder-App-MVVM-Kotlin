package com.example.reminder.service.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.reminder.constants.Constants.TABLENAME


@Entity(tableName = TABLENAME)
data class Reminder(
    @PrimaryKey(autoGenerate = true) val reminderId:Int,
    @ColumnInfo(name="title") val title:String,
    @ColumnInfo(name="subtitle") val subtitle:String,
    @ColumnInfo(name="date") val time:String,
    @ColumnInfo(name="isCompleted") val isCompleted:Int
)