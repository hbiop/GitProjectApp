package com.example.gitprojectapp.data.api

import com.example.gitprojectapp.data.models.BranchDto
import com.example.gitprojectapp.data.models.FileDto
import com.example.gitprojectapp.data.models.GitRepositoryDto
import com.example.gitprojectapp.data.models.ReadmeDto
import com.example.gitprojectapp.data.models.UserInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("user")
    suspend fun getUsers(@Header("Authorization") token: String): Response<UserInfoDto>

    @GET("user/repos")
    suspend fun getRepository(@Header("Authorization") token: String): Response<List<GitRepositoryDto>>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepositoryInfo(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<GitRepositoryDto>

    @GET("repos/{owner}/{repo}/readme")
    suspend fun getReadme(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<ReadmeDto>

    @GET("https://api.github.com/repos/{owner}/{repo}/contents/{path}")
    suspend fun getListFailov(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String,
        @Query("ref") branch: String
    ): Response<List<FileDto>>
    @GET("https://api.github.com/repos/{owner}/{repo}/contents/{path}")
    suspend fun getFile(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String,
        @Query("ref") branch: String
    ): Response<FileDto>
    @GET("repos/{owner}/{repo}/branches")
    suspend fun getListBranches(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): Response<List<BranchDto>>

}