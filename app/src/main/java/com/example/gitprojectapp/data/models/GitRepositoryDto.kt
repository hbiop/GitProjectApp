package com.example.gitprojectapp.data.models

data class GitRepositoryDto(
    val id: Int,
    val name: String,
    val forks_count: Int,
    val stargazers_count: Int,
    val watchers_count: Int,
    val url: String,
    val license: String?
)
