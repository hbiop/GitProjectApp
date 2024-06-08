package com.example.gitprojectapp.api

import com.example.gitprojectapp.models.Repository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterface {
    @GET("user/repos")
    suspend fun getRepos(@Header("Authorization")token: String) : Response<Repository>
}