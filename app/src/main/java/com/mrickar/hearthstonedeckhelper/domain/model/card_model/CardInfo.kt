package com.mrickar.hearthstonedeckhelper.domain.model.card_model

import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.CardInDeck


data class CardInfo(
    val id: Int,
    val idName:String,
    val attack: Int,
    val cardSet: String,
    val cardType: String,
    val classType: ClassType,
    val collectible: Boolean,
    val cropImage: String,
    val verbalText: String,
    val health: Int,
    val image: String,
    val keywordIds: List<Int> = listOf(),
    val manaCost: Int,
    val minionType: String,
    val multiClassIds: List<Any>,
    val name: String,
    val rarity: Rarity,
    val text: String,
    val spellSchool: SpellSchool
)

fun CardInfo.toCardInDeck(
    cardNum:Int=1
): CardInDeck {
    return CardInDeck(
     idName=idName,
     name=name,
     manaCost=manaCost,
     cropImage=cropImage,
     rarity=rarity,
     cardNum = cardNum
    )
}


