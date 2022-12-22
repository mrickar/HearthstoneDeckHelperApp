package com.mrickar.hearthstonedeckhelper.presentation.deck_details_screen

import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.Deck

data class DeckDetailsState(
    val deck: Deck?=null,
    val error:String="",
    val isLoading: Boolean =false,
)