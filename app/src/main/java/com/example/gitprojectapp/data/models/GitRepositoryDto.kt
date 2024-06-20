package com.example.gitprojectapp.data.models

import com.google.gson.annotations.SerializedName

data class GitRepositoryDto(
    val id: Int,
    val name: String,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("watchers_count")
    val watchersCount: Int,
    val url: String,
    val license: String?
)
