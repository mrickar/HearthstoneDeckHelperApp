package com.mrickar.hearthstonedeckhelper.presentation.profile_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.use_case.auth_use_cases.get_user.GetUserUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.auth_use_cases.sign_out.SignOutUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.empty_deck_list.EmptyDeckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val emptyDeckUseCase: EmptyDeckUseCase
): ViewModel(){
    private val _state = mutableStateOf<ProfileScreenState>(ProfileScreenState())
    val state : State<ProfileScreenState> = _state

    init {
        getUser()
    }
    private fun getUser()
    {
        _state.value=ProfileScreenState(user=getUserUseCase())
    }

    fun signOut()
    {
        viewModelScope.launch {
            signOutUseCase().onEach {
                when(it)
                {
                    is Resource.Success->
                    {
                        _state.value = ProfileScreenState(isSignedOut = it.data!!)
                        emptyDeckUseCase()
                    }
                    is Resource.Error->
                    {
                        _state.value = ProfileScreenState(error = it.message?:"signOutUseCaseVM Error")
                        Log.d("Error", "SignOut: ${it.message}")
                    }
                    is Resource.Loading->
                    {
                        _state.value = ProfileScreenState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}