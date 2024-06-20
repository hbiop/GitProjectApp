package com.example.gitprojectapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.gitprojectapp.data.api.ApiService
import com.example.gitprojectapp.data.mapper.ReadmeMapper
import com.example.gitprojectapp.data.mapper.RepositoryMapper
import com.example.gitprojectapp.data.mapper.UserMapper
import com.example.gitprojectapp.data.models.GitRepositoryDto
import com.example.gitprojectapp.data.models.UserInfoDto
import com.example.gitprojectapp.domain.models.Readme
import com.example.gitprojectapp.domain.models.UserInfo
import com.example.gitprojectapp.domain.models.gitRepository
import com.example.gitprojectapp.domain.repository.RepositoryApi
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userMapper: UserMapper,
    private val repositoryMapper: RepositoryMapper,
    private val readmeMapper: ReadmeMapper
) : RepositoryApi {
    override suspend fun getOwner(token: String): UserInfo? {
        val a = apiService.getUsers(token)
        return if (a.body() != null) {
            userMapper.mapFromEntity(UserInfoDto(a.body()!!.id, a.body()!!.login))
        } else {
            null
        }
    }

    override suspend fun getRepos(token: String): Result<List<gitRepository>?> {
        val response = apiService.getRepository(token)
        return if (!response.isSuccessful) {
            Result.failure(Exception("При загрузке произошла ошибка"))
        } else {
            if (response.body() != null) {
                val list = response.body()
                Result.success(repositoryMapper.mapFromEntityList(list!!))
            } else {
                Result.success(null)
            }
        }
    }

    override suspend fun getRep(
        token: String,
        owner: String,
        repName: String
    ): Result<gitRepository?> {
        val response = apiService.getRepositoryInfo(token, owner, repName)
        return if (!response.isSuccessful) {
            Result.failure(Exception("При загрузке произошла ошибка"))
        } else {
            Result.success(repositoryMapper.mapFromEntity(response.body()!!))
        }
    }

    override suspend fun getReadme(
        token: String,
        owner: String,
        repName: String
    ): Result<Readme> {
        val response = apiService.getReadme(token, owner, repName)
        return if (!response.isSuccessful) {
            Result.failure(Exception("При загрузке произошла ошибка"))
        } else {
            Result.success(readmeMapper.mapFromEntity(response.body()!!))
        }
    }

}