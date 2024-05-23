package com.example.lab3comp.Data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
private object DatabaseModule {

    @Singleton
    @Provides
    fun provideMyDao(database: MyDB): MyDao {
        return database.myDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): MyDB {
        return Room.databaseBuilder(context.applicationContext, MyDB::class.java, "my_db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}