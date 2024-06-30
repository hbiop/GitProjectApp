package com.example.gitprojectapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitprojectapp.domain.models.UserInfo
import com.example.gitprojectapp.domain.repository.RepositoryApi
import com.example.gitprojectapp.domain.usecases.GetTokenUseCase
import com.example.gitprojectapp.domain.usecases.SaveTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val apiRepository: RepositoryApi) : ViewModel() {
    private val _state = MutableLiveData<AuthViewModel.State>()
    val state: LiveData<AuthViewModel.State> = _state
    @Inject
    lateinit var getTokenUseCase: GetTokenUseCase
    @Inject
    lateinit var saveTokenUseCase: SaveTokenUseCase
    private val _action = MutableLiveData<AuthViewModel.Action>()
    val action: LiveData<AuthViewModel.Action> = _action

    private val _userList = MutableLiveData<UserInfo?>()
    val userList: LiveData<UserInfo?>
        get() = _userList

    fun getToken() : String{
        val a = getTokenUseCase.execute()
        return a
    }
    fun saveToken(token: String) {
        saveTokenUseCase.execute(userList.value!!, token)
    }

    fun loadUsers(token: String) {
        try {
            viewModelScope.launch {
                _state.value = State.Loading
                delay(100)
                val result = apiRepository.getOwner("Bearer $token")
                if (result.isSuccess) {
                    _userList.value = result.getOrThrow()
                    if (result.getOrThrow() != null) {
                        _action.value = Action.RouteToMain
                    } else {
                        _state.value = State.InvalidInput("Введён не верные данные для входа")
                    }
                } else {
                    _action.value = Action.ShowError(result.exceptionOrNull()!!.message.toString())
                    _state.value = State.Idle
                }
            }
        }
        catch (e: Exception){
            _action.value = Action.ShowError("Произошла ошибка")
        }

    }
    fun loadUsers() {
        try {
            viewModelScope.launch {
                _state.value = State.Loading
                val a = getTokenUseCase.execute()
                val result = apiRepository.getOwner("Bearer ${a}")
                if (result.isSuccess) {
                    _userList.value = result.getOrThrow()
                    if (result.getOrThrow() != null) {
                        _action.value = Action.RouteToMainWithToken
                    } else {
                        _state.value = State.InvalidInput("Введён не верные данные для входа")
                    }
                } else {
                    _state.value = State.Idle
                }
            }
        }
        catch (e: Exception){
            _action.value = Action.ShowError("Произошла ошибка")
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
        object RouteToMainWithToken : Action

    }
}