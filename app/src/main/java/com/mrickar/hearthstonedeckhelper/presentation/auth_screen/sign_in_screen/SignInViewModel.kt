package com.mrickar.hearthstonedeckhelper.presentation.auth_screen.sign_in_screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.use_case.auth_use_cases.is_user_authenticated.IsUserAuthenticatedUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.auth_use_cases.sign_in.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val isUserAuthenticatedUseCase: IsUserAuthenticatedUseCase
): ViewModel(){
    private val _state = mutableStateOf<SignInState>(SignInState())
    val state : State<SignInState> = _state
    init {
        isUserAuthenticated()
    }

    fun setEmail(newEmail:String)
    {
        _state.value= SignInState(email = newEmail, password = _state.value.password)
    }
    fun setPassword(newPassword:String)
    {
        _state.value=SignInState(email = _state.value.email, password = newPassword)
    }

    private fun isUserAuthenticated()
    {
        viewModelScope.launch {
            isUserAuthenticatedUseCase().onEach {
                when(it)
                {
                    is Resource.Success->
                    {
                        _state.value = SignInState( isUserAuthenticated = it.data!!)
                    }
                    is Resource.Error->
                    {
                        _state.value = SignInState(error = it.message?:"isUserAuthenticatedUseCaseVM Error")
                        Log.d(TAG, "isUserAuthenticated : ${it.message}")
                    }
                    is Resource.Loading->
                    {
                        _state.value = SignInState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun signIn()
    {
        viewModelScope.launch {
            signInUseCase(email=_state.value.email, password = _state.value.password).onEach {
                when(it)
                {
                    is Resource.Success->
                    {
                        _state.value = SignInState( isUserAuthenticated = it.data!!)
                    }
                    is Resource.Error->
                    {
                        _state.value = SignInState(error = it.message?:"isUserAuthenticatedUseCaseVM Error")
                        Log.d(TAG, "isUserAuthenticated : ${it.message}")
                    }
                    is Resource.Loading->
                    {
                        _state.value = SignInState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}