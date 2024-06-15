package com.example.gitprojectapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitprojectapp.domain.models.UserInfo
import com.example.gitprojectapp.domain.repository.RepositoryApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val apiRepository: RepositoryApi)   : ViewModel() {
    private val _state = MutableLiveData<AuthViewModel.State>()
    val state: LiveData<AuthViewModel.State> = _state

    private val _action = MutableLiveData<AuthViewModel.Action>()
    val action: LiveData<AuthViewModel.Action> = _action

    private val _userList = MutableLiveData<UserInfo?>()
    val userList: LiveData<UserInfo?>
        get() = _userList
    fun loadUsers(token: String) {
        viewModelScope.launch {
            _state.value = AuthViewModel.State.Loading
            delay(100)
            if(token.trim() != ""){
                try {
                    val s = _userList.value
                    val id = _userList.value?.id
                    val dfs = apiRepository.getOwner("Bearer $token")
                    //_userList.postValue(dfs!!)
                    _userList.value = dfs
                    if(_userList.value == null){
                        _action.value = AuthViewModel.Action.ShowError("Авторизация прошла не успешно")
                        _state.value = AuthViewModel.State.Idle
                    }
                    else{
                        _state.value = AuthViewModel.State.Idle
                        _action.value = AuthViewModel.Action.RouteToMain
                    }
                    if(dfs == null){

                    }
                }
                catch (e: Error){
                    _action.value = AuthViewModel.Action.ShowError("Авторизация прошла не успешно")
                    _state.value = AuthViewModel.State.Idle
                }
            }
            else{
                _state.value = AuthViewModel.State.InvalidInput("Токен не должен быть пустым")
                _action.value = AuthViewModel.Action.ShowError("Токен не должен быть пустым")
            }

        }
    }

    sealed interface State {
        object Idle : State
        object Loading : State
        data class InvalidInput(val reason: String) : State
    }

    sealed interface Action {
        data class ShowError(val message: String) : Action
        object RouteToMain : Action
    }
}