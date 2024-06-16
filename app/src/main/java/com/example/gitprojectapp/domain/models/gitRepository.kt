package com.example.gitprojectapp.domain.models

data class gitRepository(
    val Id: Int,
    val Name: String,
    val ForksCount: Int,
    val StargazersCount: Int,
    val WatchersCount: Int,
    val Url: String,
    val License: String?
)