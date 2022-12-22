package com.mrickar.hearthstonedeckhelper.domain.repository

import com.mrickar.hearthstonedeckhelper.data.remote.dto.CardInfoDto
import com.mrickar.hearthstonedeckhelper.data.remote.dto.CardSearchResultDto


interface CardSearchRepository {
    suspend fun makeCardSearch(
        set:String?=null,
        manaCost:String?=null,
        attack:String?=null,
        health:String?=null,
        rarity:String?=null,
        type:String?=null,
        keyword:String?=null,
        textFilter:String?=null,
        classes:String?=null,
        page:String?="1",
    ): CardSearchResultDto

    fun getCardSearchResultDto():CardSearchResultDto?

    suspend fun getCardInfoByIdName(idName:String):CardInfoDto

}