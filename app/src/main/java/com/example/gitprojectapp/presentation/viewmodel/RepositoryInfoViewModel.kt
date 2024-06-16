package com.example.gitprojectapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitprojectapp.domain.models.gitRepository
import com.example.gitprojectapp.domain.repository.RepositoryApi
import com.example.gitprojectapp.presentation.usecases.GetNameUseCase
import com.example.gitprojectapp.presentation.usecases.GetTokenUseCase
import com.example.gitprojectapp.presentation.usecases.SaveTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryInfoViewModel @Inject constructor(private val apiRepository: RepositoryApi)  : ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state
    private val _readmeState = MutableLiveData<ReadmeState>()
    val readmeState: LiveData<ReadmeState> = _readmeState
    @Inject
    lateinit var getNameUseCase: GetNameUseCase
    fun loadRepositoryInfo(token: String, repo: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            val repoResponse = apiRepository.getRep(token, getNameUseCase.execute(), repo)
            val readmeResponse = apiRepository.getReadme(token, getNameUseCase.execute(), repo)
            if(repoResponse.first){
                if(repoResponse.second != null){
                    if(readmeResponse.first){
                        if(readmeResponse.second == null){
                            _state.value = State.Loaded(repoResponse.second!!, "")
                        }
                        else{
                            _state.value = State.Loaded(repoResponse.second!!, readmeResponse!!.second!!.content)
                        }
                    }
                    else{
                        _state.value = State.Loaded(repoResponse.second!!, "")
                    }
                }
                else{
                    _state.value = State.Error("Readme не был загружен")
                }
            }
            else{
                _state.value = State.Error("Ошибка загрузки")
            }
        }
    }

    sealed interface State {
        object Loading : State
        data class Error(val error: String) : State

        data class Loaded(
            val githubRepo: gitRepository,
            val markdown: String
        ) : State
    }

    sealed interface ReadmeState {
        object Loading : ReadmeState
        object Empty : ReadmeState
        data class Error(val error: String) : ReadmeState
        data class Loaded(val markdown: String) : ReadmeState
    }
}