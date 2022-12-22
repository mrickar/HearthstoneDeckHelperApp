package com.mrickar.hearthstonedeckhelper.domain.use_case.auth_use_cases.sign_up

import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase@Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        rePasswords:String
    ): Flow<Resource<Boolean>> {
        if(password!=rePasswords) return flow{emit(Resource.Error<Boolean>("Passwords does not match!"))}
        return repository.signUpFirebase(email=email,password=password)
    }
}

