package com.mrickar.hearthstonedeckhelper.domain.use_case.card_search_use_cases.get_acces_token

import android.util.Log
import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.data.remote.OAuthApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val api: OAuthApi
){
    operator fun invoke(
    ): Flow<Resource<String>> {
        return flow {
            try {
                emit(Resource.Loading<String>())
                val token = api.getAccessToken().access_token
                Log.d("AccessToken",token)
                emit(Resource.Success<String>(data = token))
            } catch (e: HttpException) {
                emit(Resource.Error<String>("An unexpected error has occured."))
            } catch (e: IOException) {
                emit(Resource.Error<String>("Couldn't reach the server."))
            } catch (e: Exception) {
                emit(Resource.Error<String>(message=e.message?:"Error getting token"))
            }
        }
}
}