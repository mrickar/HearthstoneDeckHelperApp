package com.mrickar.hearthstonedeckhelper.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.mrickar.hearthstonedeckhelper.common.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getUser(): FirebaseUser
    fun isUserAuthenticatedInFirebase(): Boolean
    suspend fun signInFirebase(email:String,password:String): Flow<Resource<Boolean>>
    suspend fun signUpFirebase(email:String,password:String): Flow<Resource<Boolean>>
    suspend fun signOutFirebase(): Flow<Resource<Boolean>>
}