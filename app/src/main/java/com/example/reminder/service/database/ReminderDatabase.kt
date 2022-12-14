package com.example.reminder.service.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.reminder.constants.Constants
import com.example.reminder.service.models.Reminder

@Database (entities = [Reminder::class], version = 1)
abstract class ReminderDatabase : RoomDatabase() {
    abstract  fun reminderDao():ReminderDao

    companion object{
        @Volatile
        private var INSTANCE:ReminderDatabase? = null

        fun getInstance(context: Context):ReminderDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ReminderDatabase::class.java,
                        Constants.TABLE_NAME
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}