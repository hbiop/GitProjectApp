package com.example.gitprojectapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitprojectapp.domain.models.Branch
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
    private val _branchState = MutableLiveData<BranchState>()
    val branchState: LiveData<BranchState> = _branchState
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
    fun goDefault() {
        listPath.clear()
        listPath.add("")
        currentPath.value = listPath[listPath.size - 1]
    }
    fun goBack() {
        if(listPath.size!=1){
            listPath.removeAt(listPath.size - 1)
            currentPath.value = listPath[listPath.size - 1]
        }else{
            _state.value = SpisokFailovViewModel.State.NavigateBack
        }
    }
    fun loadBranches(repName:String){
        viewModelScope.launch {
            _branchState.value = BranchState.Loading
            val result = apiRepository.getListBranches(
                token = "Bearer ${getTokenUseCase.execute()}",
                owner = getNameUseCase.execute(),
                repName = repName
            )
            if (result.isSuccess){
                if (result.getOrThrow() != null) {
                    _branchState.value = BranchState.Loaded(result.getOrThrow()!!)
                } else {
                    _branchState.value = BranchState.Empty
                }
            }else{
                _branchState.value =
                    BranchState.Error(result.exceptionOrNull()!!.message.toString())
            }
        }
    }
    fun loadFiles(repName: String, branchName: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            val result = apiRepository.getSpisokFilov(
                token = "Bearer ${getTokenUseCase.execute()}",
                owner = getNameUseCase.execute(),
                path = currentPath.value.toString(),
                repName = repName,
                branchName = branchName
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
        object NavigateBack : State
    }
    sealed interface BranchState {
        object Loading : BranchState
        data class Loaded(val branches: List<Branch>) : BranchState
        data class Error(val error: String) : BranchState
        object Empty : BranchState
    }
}