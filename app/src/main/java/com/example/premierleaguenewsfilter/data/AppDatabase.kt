package com.example.premierleaguenewsfilter.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DatabasePlayerItem::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun playerDao(): DatabasePlayerItemDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "players.db")
                    .build()
            }
            return INSTANCE!!
        }
    }
}