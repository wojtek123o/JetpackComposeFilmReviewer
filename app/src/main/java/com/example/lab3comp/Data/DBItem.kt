package com.example.lab3comp.Data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lab3comp.R
import kotlinx.parcelize.Parcelize
import java.util.Date
import kotlin.random.Random

@Parcelize
@Entity(tableName = "movies")
data class DBItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int=0,
    var text_main: String,
    var text_2: String,
    var review: String,
    var rating: Float,
    var imgResourceId: Int,
    var date: Date?
) : Parcelable