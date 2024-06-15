package com.example.gitprojectapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitprojectapp.other.models.Repository
import com.example.gitprojectapp.other.repository.GitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class GitViewModel(private val gitRepository: GitRepository) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            gitRepository.getGit()
        }
    }
    val isSuccessfull : LiveData<Boolean>
        get() = gitRepository.result
    val gits: LiveData<Repository>
        get() = gitRepository.git
}