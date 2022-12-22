package com.mrickar.hearthstonedeckhelper.presentation.auth_screen.sign_in_screen

data class SignInState(
    var email: String = "",
    var password: String = "",
    val isUserAuthenticated:Boolean=false,
    val error:String="",
    val isLoading: Boolean =false,
)