package com.example.gitprojectapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitprojectapp.repository.GitRepository

class GitViewModelFactory(private val gitRepository: GitRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GitViewModel(gitRepository) as T
    }
}