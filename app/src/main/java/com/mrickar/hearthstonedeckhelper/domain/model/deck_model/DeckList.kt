package com.mrickar.hearthstonedeckhelper.domain.model.deck_model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class DeckList(
    val decks: SnapshotStateList<Deck> = mutableStateListOf()
)
