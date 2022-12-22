package com.mrickar.hearthstonedeckhelper.domain.repository

import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.CardInDeck
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.Deck
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.DeckList

interface DeckRepository {
    suspend fun getDeckListFromFirebase(): DeckList
    fun getDeckByID(id:String): Deck?
    fun addNewDeckToFirebase(newDeck:Deck): DeckList
    fun deleteDeckFromFirebase(deletedDeck: Deck):DeckList
    fun createNewDeck(name:String,classType: String):Deck
    fun editDeckFirebase(deck:Deck)
    fun addCardToDeck(newCard: CardInDeck, deck: Deck): Resource<Boolean>
    fun getCurrentDeckList():DeckList
    fun deleteCardFromDeck(deletedNum:Int,cardIdName:String,deck:Deck)
    fun emptyDeckList(): Boolean
    var deckList:DeckList
}