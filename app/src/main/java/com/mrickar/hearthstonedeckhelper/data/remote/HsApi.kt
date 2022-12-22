package com.mrickar.hearthstonedeckhelper.data.remote

import com.mrickar.hearthstonedeckhelper.data.remote.dto.CardInfoDto
import com.mrickar.hearthstonedeckhelper.data.remote.dto.CardSearchResultDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HsApi {

    @GET("/hearthstone/cards")
    suspend fun getCardSearch(
        @Query("set") set:String?,
        @Query("class") classes:String?,
        @Query("manaCost") manaCost:String?,
        @Query("attack") attack:String?,
        @Query("health") health:String?,
        @Query("rarity") rarity:String?,
        @Query("type") type:String?,
        @Query("keyword") keyword:String?,
        @Query("textFilter") textFilter:String?,
        @Query("page") page:String?,
    ): CardSearchResultDto

    @GET("/hearthstone/cards/{idName}")
    suspend fun getCardInfoByIdName(
        @Path("idName") idName:String,
    ): CardInfoDto
}
