package com.pm.troflib.api.requests

import com.pm.troflib.api.dto.UserDto
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Field

interface UsersApi {
    @FormUrlEncoded
    @POST(("users/signin"))
    fun signin(@Field("username") username: String, @Field("password") password : String) : Call<UserDto>
}