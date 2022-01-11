package com.pm.troflib.api.requests

import com.pm.troflib.api.dto.BookDto
import com.pm.troflib.api.models.Book
import retrofit2.Call
import retrofit2.http.*

interface BookApi {
    @GET("book/read")
    fun getBooks(@Header("Authorization") token: String): Call<List<Book>>

    @FormUrlEncoded
    @POST("book/create")
    fun createBook(
        @Header("Authorization") token: String,
        @Field("users_id") users_id: String?,
        @Field("title") title: String,
        @Field("author") author: String,
        @Field("genre") genre: String,
        @Field("dtlaunch") dtlaunch: String,
        @Field("publishcompany") publishcompany: String,
        @Field("npages") npages: Int,
        @Field("cover") cover: String
    ): Call<BookDto>

    @FormUrlEncoded
    @POST("book/update")
    fun updateBook(
        @Header("Authorization") token: String,
        @Field("id") id: Int,
        @Field("title") title: String,
        @Field("author") author: String,
        @Field("genre") genre: String,
        @Field("dtlaunch") dtlaunch: String,
        @Field("publishcompany") publishcompany: String,
        @Field("npages") npages: Int,
        @Field("cover") cover: String
    ): Call<BookDto>

    @FormUrlEncoded
    @POST("book/delete")
    fun deleteBook(@Header("Authorization") token: String, @Field("id") id: Int): Call<BookDto>
}