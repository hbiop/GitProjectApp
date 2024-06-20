package com.example.gitprojectapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitprojectapp.domain.models.gitRepository
import com.example.gitprojectapp.domain.repository.RepositoryApi
import com.example.gitprojectapp.domain.usecases.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class SpisokRepositoriewViewModel @Inject constructor(private val apiRepository: RepositoryApi) :
    ViewModel() {
    private val _state = MutableLiveData<SpisokRepositoriewViewModel.State>()
    val state: LiveData<SpisokRepositoriewViewModel.State> = _state

    @Inject
    lateinit var getTokenUseCase: GetTokenUseCase
    fun loadRepos() {
        viewModelScope.launch {
            _state.value = SpisokRepositoriewViewModel.State.Loading
            val result = apiRepository.getRepos("Bearer ${getTokenUseCase.execute()}")
            if (result.isSuccess) {
                if (result.getOrThrow() != null) {
                    _state.value = SpisokRepositoriewViewModel.State.Loaded(result.getOrThrow()!!)
                } else {
                    _state.value = SpisokRepositoriewViewModel.State.Empty
                }
            } else {
                _state.value =
                    SpisokRepositoriewViewModel.State.Error(result.exceptionOrNull()!!.message.toString())
            }

        }
    }

    sealed interface State {
        object Loading : State
        data class Loaded(val repos: List<gitRepository>) : State
        data class Error(val error: String) : State
        object Empty : State
    }
}