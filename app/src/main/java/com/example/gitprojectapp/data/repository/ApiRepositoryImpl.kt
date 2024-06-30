package com.example.gitprojectapp.data.repository

import android.util.Log
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
    override suspend fun getOwner(token: String): Result<UserInfo?> {
        try {
            val response = apiService.getUsers(token)
            return if (!response.isSuccessful) {
                Result.failure(Exception("При загрузке произошла ошибка"))
            } else {
                val a = response.body()
                if (a != null) {
                    Result.success(userMapper.mapFromEntity(response.body()!!))
                } else {
                   Result.success(null)
                }
            }
        }catch (e:Exception){
            return Result.failure(Exception("При загрузке произошла ошибка"))
        }


    }

    override suspend fun getRepos(token: String): Result<List<gitRepository>?> {
        try {
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
        catch (e: Exception){
            return Result.failure(Exception("При загрузке произошла ошибка"))
        }

    }
    override suspend fun getRep(
        token: String,
        owner: String,
        repName: String
    ): Result<gitRepository?> {
        return try {
            val response = apiService.getRepositoryInfo(token, owner, repName)
            if (!response.isSuccessful) {
                Result.failure(Exception("При загрузке произошла ошибка"))
            } else {
                Result.success(repositoryMapper.mapFromEntity(response.body()!!))
            }
        }catch (e:Exception){
            Result.failure(Exception("При загрузке произошла ошибка"))
        }

    }

    override suspend fun getReadme(
        token: String,
        owner: String,
        repName: String
    ): Result<Readme> {
        return try {
            val response = apiService.getReadme(token, owner, repName)
            if (!response.isSuccessful) {
                Result.failure(Exception("При загрузке произошла ошибка"))
            } else {
                Result.success(readmeMapper.mapFromEntity(response.body()!!))
            }
        }catch (e:Exception){
            Result.failure(Exception("При загрузке произошла ошибка"))
        }

    }

    override suspend fun getSpisokFilov(
        token: String,
        owner: String,
        repName: String,
        path: String,
        branchName: String
    ): Result<List<mFile>?> {
        try {
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
        }catch (e:Exception){
            return Result.failure(Exception("При загрузке произошла ошибка"))
        }

    }

    override suspend fun getListBranches(
        token: String,
        owner: String,
        repName: String
    ): Result<List<Branch>?> {
        try {
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
        catch (e:Exception){
            return Result.failure(Exception("При загрузке произошла ошибка"))
        }

    }

    override suspend fun getFile(
        token: String,
        owner: String,
        repName: String,
        path: String,
        branchName: String
    ): Result<mFile?> {
        try {
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
        }catch (e:Exception){
            return Result.failure(Exception("При загрузке произошла ошибка"))
        }

    }

}
