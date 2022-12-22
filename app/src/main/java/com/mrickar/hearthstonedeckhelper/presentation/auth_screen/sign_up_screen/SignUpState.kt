package com.mrickar.hearthstonedeckhelper.presentation.auth_screen.sign_up_screen

data class SignUpState(
    var email: String = "",
    var password: String = "",
    var rePassword: String = "",
    val isUserAuthenticated:Boolean=false,
    val error:String="",
    val isLoading: Boolean =false,
)