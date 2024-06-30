package com.example.gitprojectapp.domain.repository

import com.example.gitprojectapp.domain.models.Branch
import com.example.gitprojectapp.domain.models.Readme
import com.example.gitprojectapp.domain.models.UserInfo
import com.example.gitprojectapp.domain.models.gitRepository
import com.example.gitprojectapp.domain.models.mFile

interface RepositoryApi {
    suspend fun getOwner(token: String): Result<UserInfo?>
    suspend fun getRepos(token: String): Result<List<gitRepository>?>
    suspend fun getRep(token: String, owner: String, repName: String): Result<gitRepository?>
    suspend fun getReadme(token: String, owner: String, repName: String): Result<Readme>
    suspend fun getSpisokFilov(token: String, owner: String, repName: String, path: String, branchName: String): Result<List<mFile>?>

    suspend fun getListBranches(token: String, owner: String, repName: String): Result<List<Branch>?>
    suspend fun getFile(token: String, owner: String, repName: String, path: String, branchName: String): Result<mFile?>


}