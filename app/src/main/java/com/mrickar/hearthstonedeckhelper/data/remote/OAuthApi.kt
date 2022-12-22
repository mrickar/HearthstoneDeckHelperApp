package com.mrickar.hearthstonedeckhelper.data.remote

import com.mrickar.hearthstonedeckhelper.common.Constants
import com.mrickar.hearthstonedeckhelper.data.remote.dto.TokenDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OAuthApi {
    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun getAccessToken(
        @Field(Constants.GRANT_TYPE) grant:String=Constants.CLIENT_CREDENTIALS
    ): TokenDto
}