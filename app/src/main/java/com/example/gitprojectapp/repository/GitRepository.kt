package com.example.gitprojectapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitprojectapp.api.ApiInterface
import com.example.gitprojectapp.models.Repository

class GitRepository(private val apiInterface: ApiInterface, private val token:String) {
    private val gitLiveData = MutableLiveData<Repository>()
    private val responseResult = MutableLiveData<Boolean>()
    val git : LiveData<Repository>
        get() = gitLiveData
    val result : LiveData<Boolean>
        get() = responseResult

    suspend fun getGit(){
        val result = apiInterface.getRepos("Bearer " + token)
        responseResult.postValue(result.isSuccessful)
        if(result.body() != null){
            gitLiveData.postValue(result.body())
        }

    }
}