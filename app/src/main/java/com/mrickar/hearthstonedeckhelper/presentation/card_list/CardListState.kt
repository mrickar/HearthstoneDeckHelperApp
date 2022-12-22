package com.mrickar.hearthstonedeckhelper.presentation.card_list

import com.mrickar.hearthstonedeckhelper.domain.model.card_model.CardSearchResult

data class CardListState (
    val cardSearchResult: CardSearchResult?= CardSearchResult(),
    val isNextPageAvailable:Boolean=false,
    val isPreviousPageAvailable:Boolean=false,
    val error:String="",
    val isLoading:Boolean=false
)
