package com.mrickar.hearthstonedeckhelper.domain.use_case.auth_use_cases.get_user

import com.mrickar.hearthstonedeckhelper.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: AuthRepository
){
    operator fun invoke(): FirebaseUser {
        return repository.getUser()
    }
}