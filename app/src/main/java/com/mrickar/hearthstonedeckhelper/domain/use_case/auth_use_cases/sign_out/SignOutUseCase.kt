package com.mrickar.hearthstonedeckhelper.domain.use_case.auth_use_cases.sign_out

import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignOutUseCase@Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Flow<Resource<Boolean>> {
        return repository.signOutFirebase()
    }
}

