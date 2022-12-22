package com.mrickar.hearthstonedeckhelper.presentation.card_details

import com.mrickar.hearthstonedeckhelper.domain.model.card_model.CardInfo

data class CardDetailsState (
    val card: CardInfo?=null,
    val isLoading:Boolean=false,
    val error:String=""
)
