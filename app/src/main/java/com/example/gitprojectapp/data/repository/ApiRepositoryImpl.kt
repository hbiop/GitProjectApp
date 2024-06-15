package com.example.gitprojectapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.gitprojectapp.data.api.ApiService
import com.example.gitprojectapp.data.mapper.UserMapper
import com.example.gitprojectapp.data.models.UserInfoDto
import com.example.gitprojectapp.domain.models.UserInfo
import com.example.gitprojectapp.domain.repository.RepositoryApi
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(private val apiService: ApiService) : RepositoryApi {
    private val gitLiveData = MutableLiveData<UserInfo>()
    val userMapper:UserMapper = UserMapper()
    override suspend fun getOwner(token: String): UserInfo? {
        val a = apiService.getUsers(token)
        if(a.isSuccessful){

        }
        return if(a.body() != null){
            userMapper.mapFromEntity(UserInfoDto(a.body()!!.id))
        } else {
            null
        }
    }
}