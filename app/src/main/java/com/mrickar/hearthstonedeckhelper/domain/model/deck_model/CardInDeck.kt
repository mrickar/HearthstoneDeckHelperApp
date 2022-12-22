package com.mrickar.hearthstonedeckhelper.domain.model.deck_model

import com.mrickar.hearthstonedeckhelper.common.MetaData
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.Rarity

data class CardInDeck(
    val idName:String,
    val name: String,
    val manaCost: Int,
    val cropImage: String,
    val rarity: Rarity,
    var cardNum:Int,
){
    constructor(hashMap: HashMap<String,*>) : this(
        idName = hashMap["idName"] as String,
        name = hashMap["name"] as String,
        manaCost = (hashMap["manaCost"] as Long).toInt(),
        cropImage = hashMap["cropImage"] as String,
        cardNum = (hashMap["cardNum"] as Long).toInt(),
        rarity = MetaData.RarityByName(hashMap["rarity"] as String)
    )
}

