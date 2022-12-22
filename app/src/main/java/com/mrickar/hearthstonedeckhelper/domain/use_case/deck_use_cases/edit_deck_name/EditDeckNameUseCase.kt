package com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.edit_deck_name

import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.Deck
import com.mrickar.hearthstonedeckhelper.domain.repository.DeckRepository
import javax.inject.Inject

class EditDeckUseCase @Inject constructor(
    private val repository: DeckRepository
){
    operator fun invoke(
        deck: Deck
    ){
        repository.editDeckFirebase(deck)
    }
}