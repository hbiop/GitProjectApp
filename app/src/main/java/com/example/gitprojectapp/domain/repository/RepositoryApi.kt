package com.example.gitprojectapp.domain.repository

import com.example.gitprojectapp.domain.models.Readme
import com.example.gitprojectapp.domain.models.UserInfo
import com.example.gitprojectapp.domain.models.gitRepository

interface RepositoryApi {
    suspend fun getOwner(token: String): UserInfo?
    suspend fun getRepos(token: String): Pair<Boolean, List<gitRepository>?>
    suspend fun getRep(token: String, owner:String, repName:String): Pair<Boolean, gitRepository?>
    suspend fun getReadme(token: String, owner:String, repName:String): Pair<Boolean, Readme?>

}