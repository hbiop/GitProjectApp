package com.example.gitprojectapp.domain.repository

import com.example.gitprojectapp.domain.models.UserInfo

interface RepositoryApi {
    suspend fun getOwner(token: String): UserInfo?
}