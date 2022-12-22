package com.mrickar.hearthstonedeckhelper.domain.model.deck_model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.mrickar.hearthstonedeckhelper.common.MetaData
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.ClassType

data class Deck(

    val id:String="",
    var name:String="New Deck",
    val classType:ClassType=ClassType.DRUID, //TODO NEUTRAL YAP
    var isCompleted : Boolean=false,
    var cards:SnapshotStateList<CardInDeck> = mutableStateListOf(),
    var cardCount:Int = 0
){
    constructor(hashMap: HashMap<*,*>) : this(
        id=hashMap["id"] as String,
        name=hashMap["name"] as String,
        isCompleted=hashMap["completed"] as Boolean,
        cards= mutableStateListOf(),
        cardCount=(hashMap["cardCount"] as Long).toInt(),
        classType=MetaData.ClassByName(hashMap["classType"] as String)
        )
    {
        if(hashMap.containsKey("cards"))
        {
            for (card in (hashMap["cards"] as HashMap<String,*>).values)
            {
                val cardInDeck = CardInDeck(card as HashMap<String, *>)
                cards.add(cardInDeck)
            }
        }
    }

    fun checkIsDeckCompleted()
    {
        isCompleted = cardCount==30
    }
}
