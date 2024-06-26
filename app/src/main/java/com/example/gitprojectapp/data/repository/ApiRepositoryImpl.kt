package com.example.gitprojectapp.data.repository

import com.example.gitprojectapp.data.api.ApiService
import com.example.gitprojectapp.data.mapper.BranchMapper
import com.example.gitprojectapp.data.mapper.FileMapper
import com.example.gitprojectapp.data.mapper.ReadmeMapper
import com.example.gitprojectapp.data.mapper.RepositoryMapper
import com.example.gitprojectapp.data.mapper.UserMapper
import com.example.gitprojectapp.data.models.UserInfoDto
import com.example.gitprojectapp.domain.models.Branch
import com.example.gitprojectapp.domain.models.Readme
import com.example.gitprojectapp.domain.models.UserInfo
import com.example.gitprojectapp.domain.models.gitRepository
import com.example.gitprojectapp.domain.models.mFile
import com.example.gitprojectapp.domain.repository.RepositoryApi
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userMapper: UserMapper,
    private val repositoryMapper: RepositoryMapper,
    private val readmeMapper: ReadmeMapper,
    private val fileMapper: FileMapper,
    private val branchMapper: BranchMapper
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

    override suspend fun getSpisokFilov(
        token: String,
        owner: String,
        repName: String,
        path: String,
        branchName: String
    ): Result<List<mFile>?> {
        val response = apiService.getListFailov(
            token = token,
            owner = owner,
            repo = repName,
            path = path,
            branch = branchName
        )
        return if (!response.isSuccessful) {
            Result.failure(Exception("При загрузке произошла ошибка"))
        } else {
            if (response.body() != null) {
                val list = response.body()
                Result.success(fileMapper.mapFromEntityList(list!!))
            } else {
                Result.success(null)
            }
        }
    }

    override suspend fun getListBranches(
        token: String,
        owner: String,
        repName: String
    ): Result<List<Branch>?> {
        val response = apiService.getListBranches(token,owner,repName)
        return if(!response.isSuccessful){
            Result.failure(Exception("При загрузке произошла ошибка"))
        }
        else{
            if (response.body() != null) {
                val list = response.body()
                Result.success(branchMapper.mapFromEntityList(list!!))
            } else {
                Result.success(null)
            }
        }
    }

    override suspend fun getFile(
        token: String,
        owner: String,
        repName: String,
        path: String,
        branchName: String
    ): Result<mFile?> {
        val response = apiService.getFile(
            token = token,
            owner = owner,
            repo = repName,
            path = path,
            branch = branchName
        )
        return if (!response.isSuccessful) {
            Result.failure(Exception("При загрузке произошла ошибка"))
        } else {
            if (response.body() != null) {
                val list = response.body()
                Result.success(fileMapper.mapFromEntity(list!!))
            } else {
                Result.success(null)
            }
        }
    }

}