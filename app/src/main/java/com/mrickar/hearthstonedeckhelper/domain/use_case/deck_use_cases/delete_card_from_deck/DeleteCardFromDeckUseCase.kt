package com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.delete_card_from_deck

import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.Deck
import com.mrickar.hearthstonedeckhelper.domain.repository.DeckRepository
import javax.inject.Inject

class DeleteCardFromDeckUseCase @Inject constructor(
    private val repository: DeckRepository
){
    operator fun invoke(
        deletedNum: Int,
        cardIdName: String,
        deck: Deck
    ){
        repository.deleteCardFromDeck(deletedNum = deletedNum, cardIdName =cardIdName,deck=deck )
    }
}