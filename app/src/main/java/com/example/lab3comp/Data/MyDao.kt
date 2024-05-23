package com.example.lab3comp.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: DBItem)

    @Update
    suspend fun update(item: DBItem)

    @Delete
    suspend fun delete(item: DBItem) // operacje na bazie danych powinny być wykonywane w kontekście korutyn

    @Query("SELECT * from movies WHERE id = :id")
    fun getItem(id: String): DBItem

    @Query("SELECT * FROM movies")
    fun getAllItems(): List<DBItem>
}