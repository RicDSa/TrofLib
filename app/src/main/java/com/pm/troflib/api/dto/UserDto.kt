package com.pm.troflib.api.dto

import com.pm.troflib.api.models.User

data class UserDto(
    val status : String,
    val message : String,
    val user : List<User>,
    val token : String
)
