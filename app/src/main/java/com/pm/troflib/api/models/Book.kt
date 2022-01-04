package com.pm.troflib.api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(
    val id : Int,
    val title : String,
    val author : String,
    val genre : String,
    val dtlaunch : String,
    val publishcompany : String,
    val npages : Int,
    val cover : String,
    val users_id : Int,
    val created_at : String,
    val user_name: String
) : Parcelable
