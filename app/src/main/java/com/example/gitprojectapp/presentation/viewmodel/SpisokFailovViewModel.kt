package com.example.gitprojectapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitprojectapp.domain.models.mFile
import com.example.gitprojectapp.domain.repository.RepositoryApi
import com.example.gitprojectapp.domain.usecases.GetNameUseCase
import com.example.gitprojectapp.domain.usecases.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpisokFailovViewModel @Inject constructor(private val apiRepository: RepositoryApi) :
    ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state
    private val currentPath = MutableLiveData("")
    private val listPath = mutableListOf("")

    @Inject
    lateinit var getTokenUseCase: GetTokenUseCase

    @Inject
    lateinit var getNameUseCase: GetNameUseCase
    fun goStraight(path: String) {
        listPath.add(path)
        currentPath.value = listPath[listPath.size - 1]
    }

    fun goBack() {
        if(listPath.size!=1){
            listPath.removeAt(listPath.size - 1)
            currentPath.value = listPath[listPath.size - 1]
        }
    }

    fun loadFiles(repName: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            val result = apiRepository.getSpisokFilov(
                token = "Bearer ${getTokenUseCase.execute()}",
                owner = getNameUseCase.execute(),
                path = currentPath.value.toString(),
                repName = repName
            )
            if (result.isSuccess) {
                if (result.getOrThrow() != null) {
                    _state.value = State.Loaded(result.getOrThrow()!!)
                } else {
                    _state.value = State.Empty
                }
            } else {
                _state.value =
                    State.Error(result.exceptionOrNull()!!.message.toString())
            }

        }
    }

    sealed interface State {
        object Loading : State
        data class Loaded(val repos: List<mFile>) : State
        data class Error(val error: String) : State
        object Empty : State
    }
}