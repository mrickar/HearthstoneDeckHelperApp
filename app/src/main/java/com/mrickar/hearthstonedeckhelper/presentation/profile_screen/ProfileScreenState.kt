package com.mrickar.hearthstonedeckhelper.presentation.profile_screen

import com.google.firebase.auth.FirebaseUser

data class ProfileScreenState (
    val user: FirebaseUser?=null,
    val isSignedOut: Boolean =false,
    val error:String="",
    val isLoading: Boolean =false,
)