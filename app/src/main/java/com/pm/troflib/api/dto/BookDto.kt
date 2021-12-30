package com.pm.troflib.api.dto

import com.pm.troflib.api.models.Book

data class BookDto(
    val status : String,
    val message : String,
    val book : Book
)
