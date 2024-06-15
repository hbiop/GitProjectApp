package com.example.gitprojectapp.data.api

import com.example.gitprojectapp.data.models.UserInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("user")
    suspend fun getUsers(@Header("Authorization")token: String): Response<UserInfoDto>
}