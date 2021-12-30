package com.pm.troflib.api.requests

import com.pm.troflib.api.dto.BookDto
import com.pm.troflib.api.models.Book
import retrofit2.Call
import retrofit2.http.*

interface BookApi {
    @GET("books/read")
    fun getReports(@Header("Authorization") token: String): Call<List<Book>>

    @FormUrlEncoded
    @POST("books/create")
    fun createReport(
        @Header("Authorization") token: String,
        @Field("title") title: String,
        @Field("author") author: String,
        @Field("genre") genre: String,
        @Field("dtlaunch") dtlaunch: String,
        @Field("publishcompany") publishcompany: String,
        @Field("npages") npages: Int,
        @Field("cover") cover: String
    ): Call<BookDto>

    @FormUrlEncoded
    @POST("books/update")
    fun updateReport(
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
    @POST("books/delete")
    fun deleteReport(@Header("Authorization") token: String, @Field("id") id: Int): Call<BookDto>
}