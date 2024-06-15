package com.example.gitprojectapp.domain.repository

interface SharedPreferenceRepository {
    fun saveToken(token: String)
    fun getToken(): String
}