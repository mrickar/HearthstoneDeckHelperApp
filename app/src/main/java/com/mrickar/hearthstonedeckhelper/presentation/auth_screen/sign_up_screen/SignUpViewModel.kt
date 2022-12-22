package com.mrickar.hearthstonedeckhelper.presentation.auth_screen.sign_up_screen

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.use_case.auth_use_cases.sign_up.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel(){
    private val _state = mutableStateOf<SignUpState>(SignUpState())
    val state : State<SignUpState> = _state

    fun setEmail(newEmail:String)
    {
        _state.value= SignUpState(email = newEmail, password = _state.value.password, rePassword = _state.value.rePassword)
    }
    fun setPassword(newPassword:String)
    {
        _state.value= SignUpState(email = _state.value.email, password = newPassword,rePassword = _state.value.rePassword)
    }
    fun setRePassword(newRePassword:String)
    {
        _state.value= SignUpState(email = _state.value.email, password = _state.value.password,rePassword = newRePassword)
    }

    fun signUp()
    {
        viewModelScope.launch {
            signUpUseCase(email=_state.value.email, password = _state.value.password, rePasswords =_state.value.rePassword ).onEach {
                when(it)
                {
                    is Resource.Success->
                    {
                        _state.value = SignUpState( isUserAuthenticated = it.data!!)
                    }
                    is Resource.Error->
                    {
                        _state.value = SignUpState(error = it.message?:"SignUp Error")
                        Log.d(ContentValues.TAG, "SignUp : ${it.message}")
                    }
                    is Resource.Loading->
                    {
                        _state.value = SignUpState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}