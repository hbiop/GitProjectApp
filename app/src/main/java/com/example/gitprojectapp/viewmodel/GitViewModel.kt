package com.example.gitprojectapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitprojectapp.models.Repository
import com.example.gitprojectapp.repository.GitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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