package com.example.gitprojectapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitprojectapp.domain.models.gitRepository
import com.example.gitprojectapp.domain.repository.RepositoryApi
import com.example.gitprojectapp.other.models.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class SpisokRepositoriewViewModel @Inject constructor(private val apiRepository: RepositoryApi) : ViewModel() {
    private val _state = MutableLiveData<SpisokRepositoriewViewModel.State>()
    val state: LiveData<SpisokRepositoriewViewModel.State> = _state

    fun loadRepos(token: String){
        viewModelScope.launch {
            _state.value = SpisokRepositoriewViewModel.State.Loading
            if(token != "token"){
                    val owner = apiRepository.getRepos("Bearer $token")
                    if(owner.first == false){
                        _state.value = State.Error("Произошла ошибка")
                    }else if(owner.second == null){
                        _state.value = State.Empty
                    }else{
                        _state.value = State.Loaded(owner.second!!)
                    }

            }else{
                _state.value = State.Error("Произошла ошибка")
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