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
import com.example.gitprojectapp.other.models.Repository
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(private val apiService: ApiService) : RepositoryApi {
    private val repositoryLiveData = MutableLiveData<List<GitRepositoryDto>>()
    val userMapper:UserMapper = UserMapper()
    val repositoryMapper: RepositoryMapper = RepositoryMapper()
    val readmeMapper: ReadmeMapper = ReadmeMapper()

    override suspend fun getOwner(token: String): UserInfo? {
        val a = apiService.getUsers(token)
        return if(a.body() != null){
            userMapper.mapFromEntity(UserInfoDto(a.body()!!.id, a.body()!!.login))
        } else {
            null
        }
    }

    override suspend fun getRepos(token: String): Pair<Boolean, List<gitRepository>?> {
        val response = apiService.getRepository(token)
        return if(!response.isSuccessful){
            Pair(false, null)
        } else{
            if(response.body() != null){
                val list = response.body()
                Pair(true, repositoryMapper.mapFromEntityList(list!!))
            } else{
                Pair(true, null)
            }
        }
    }

    override suspend fun getRep(
        token: String,
        owner: String,
        repName: String
    ): Pair<Boolean, gitRepository?> {
        val response = apiService.getRepositoryInfo(token,owner,repName)
        return if(!response.isSuccessful){
            Pair(false, null)
        } else{
            if(response.body() != null){
                val list = response.body()
                Pair(true, repositoryMapper.mapFromEntity(list!!))
            } else{
                Pair(true, null)
            }
        }
    }

    override suspend fun getReadme(
        token: String,
        owner: String,
        repName: String
    ): Pair<Boolean, Readme?> {
        val response = apiService.getReadme(token,owner,repName)
        return if(!response.isSuccessful){
            Pair(false, null)
        } else{
            if(response.body() != null){
                val readme = response.body()
                Pair(true, readmeMapper.mapFromEntity(readme!!))
            } else{
                Pair(true, null)
            }
        }
    }

}