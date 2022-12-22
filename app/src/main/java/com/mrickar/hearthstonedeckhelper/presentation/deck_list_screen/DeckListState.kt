package com.mrickar.hearthstonedeckhelper.presentation.deck_list_screen

import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.DeckList

data class DeckListState(
    val deckList: DeckList?=null,
    val isEmpty:Boolean=true,
    val error:String="",
    val isLoading: Boolean =false,
)