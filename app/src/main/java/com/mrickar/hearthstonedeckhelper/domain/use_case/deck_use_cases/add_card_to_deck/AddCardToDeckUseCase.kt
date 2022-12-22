package com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.add_card_to_deck

import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.CardInDeck
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.Deck
import com.mrickar.hearthstonedeckhelper.domain.repository.DeckRepository
import javax.inject.Inject

class AddCardToDeckUseCase @Inject constructor(
    private val repository: DeckRepository
){
    operator fun invoke(
        cardInDeck: CardInDeck,
        deck: Deck
    ): Resource<Boolean> {
        return repository.addCardToDeck(newCard = cardInDeck, deck = deck)
    }
}