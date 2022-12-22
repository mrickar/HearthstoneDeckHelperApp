package com.mrickar.hearthstonedeckhelper.data.repository

import com.mrickar.hearthstonedeckhelper.data.remote.HsApi
import com.mrickar.hearthstonedeckhelper.data.remote.dto.CardInfoDto
import com.mrickar.hearthstonedeckhelper.data.remote.dto.CardSearchResultDto
import com.mrickar.hearthstonedeckhelper.domain.repository.CardSearchRepository
import javax.inject.Inject


class CardSearchRepositoryImpl @Inject constructor(
    private val api: HsApi
):CardSearchRepository{
    private  var  cardSearchResultDto:CardSearchResultDto? = null


    override fun getCardSearchResultDto(): CardSearchResultDto? {
        return cardSearchResultDto
    }

    override suspend fun getCardInfoByIdName(idName:String): CardInfoDto {
        return api.getCardInfoByIdName(idName)
    }

    override suspend fun makeCardSearch(
        set:String?,
        manaCost:String?,
        attack:String?,
        health:String?,
        rarity:String?,
        type:String?,
        keyword:String?,
        textFilter:String?,
        classes:String?,
        page:String?
    ): CardSearchResultDto {
        cardSearchResultDto= api.getCardSearch(set=set, manaCost=manaCost,attack =attack, health=health, rarity=rarity, type=type, keyword=keyword, textFilter=textFilter,page=page,classes=classes)
        return cardSearchResultDto!!
    }

}