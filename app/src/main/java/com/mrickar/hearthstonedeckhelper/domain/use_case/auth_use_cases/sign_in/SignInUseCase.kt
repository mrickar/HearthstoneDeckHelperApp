package com.mrickar.hearthstonedeckhelper.domain.use_case.auth_use_cases.sign_in

import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase@Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Flow<Resource<Boolean>> {
        return repository.signInFirebase(email=email,password=password)
    }
}

