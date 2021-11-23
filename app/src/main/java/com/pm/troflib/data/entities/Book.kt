package com.pm.troflib.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Book")

class Book (
    @PrimaryKey(autoGenerate = true)
    val id: Int,


    @ColumnInfo(defaultValue = "")
    val name: String,
    val genre: String,
    val author: String,
    val date_test: String,
    val pubcom: String,
    val npag: Int,
    val enc: String

    ) : Parcelable

