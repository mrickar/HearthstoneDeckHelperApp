package com.mrickar.hearthstonedeckhelper.data.remote.dto

import com.mrickar.hearthstonedeckhelper.domain.model.card_model.CardSearchResult

data class CardSearchResultDto(
    val cardCount: Int,
    val cards: List<CardInfoDto>,
    val page: Int,
    val pageCount: Int
)

fun CardSearchResultDto.toCardSearchResult(): CardSearchResult {
    return CardSearchResult(
        cardCount=cardCount,
        pageCount=pageCount,
        curPage=page,
        cards=cards
            .filter {
                it.copyOfCardId == 0
            }
            .map {
            it.toCardInfo()
        }
    )
}