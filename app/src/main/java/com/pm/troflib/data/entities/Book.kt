package com.pm.troflib.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Book")

class Book (
    @PrimaryKey(autoGenarate = true)
    val id: Int,
    val name: String
    ) : Parcelable

