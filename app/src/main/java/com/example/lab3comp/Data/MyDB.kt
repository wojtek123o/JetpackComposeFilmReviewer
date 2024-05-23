package com.example.lab3comp.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [DBItem::class], version = 6, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class MyDB : RoomDatabase() {

    abstract fun myDao(): MyDao

    companion object {
        @Volatile
        private var INSTANCE: MyDB? = null

        fun getInstance(context: Context): MyDB {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDB::class.java,
                        "my_db"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}