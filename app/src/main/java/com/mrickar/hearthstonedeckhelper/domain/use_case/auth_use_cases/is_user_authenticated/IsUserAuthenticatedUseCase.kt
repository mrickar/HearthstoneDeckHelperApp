package com.mrickar.hearthstonedeckhelper.domain.use_case.auth_use_cases.is_user_authenticated

import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class IsUserAuthenticatedUseCase @Inject constructor(
    private val repository: AuthRepository
){
    operator fun invoke(
    ): Flow<Resource<Boolean>> {
        return flow {
            try {
                emit(Resource.Loading<Boolean>())
                val isUserAuthenticated = repository.isUserAuthenticatedInFirebase()
                emit(Resource.Success<Boolean>(data = isUserAuthenticated))
            } catch (e: HttpException) {
                emit(Resource.Error<Boolean>("An unexpected error has occured."))
            } catch (e: IOException) {
                emit(Resource.Error<Boolean>("Couldn't reach the server."))
            } catch (e: Exception) {
                emit(Resource.Error<Boolean>(message=e.message?:"Unexpected error while looking for authentication"))
            }
        }
    }
}