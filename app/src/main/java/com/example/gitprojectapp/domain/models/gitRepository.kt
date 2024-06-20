package com.example.gitprojectapp.domain.models

data class gitRepository(
    val id: Int,
    val name: String,
    val forksCount: Int,
    val stargazersCount: Int,
    val watchersCount: Int,
    val url: String,
    val license: String?
)