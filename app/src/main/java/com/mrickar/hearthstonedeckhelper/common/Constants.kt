package com.mrickar.hearthstonedeckhelper.common

import com.mrickar.hearthstonedeckhelper.domain.model.card_model.CardInfo
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.ClassType
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.Rarity
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.SpellSchool

object Constants {
    const val BASE_URL_AUTH="https://us.battle.net/"
    const val BASE_URL="https://us.api.blizzard.com/"
    var ACCESS_TOKEN: String? =null
    const val CLIENT_CREDENTIALS="client_credentials"
    const val CARD_ADDED_SUCCESS="Card is added successfully"
    const val DECK_LIST="deckList"
    const val DECK_ID="deckID"
    const val GRANT_TYPE="grant_type"
    const val CARD_NAME="name"
    const val ID_NAME="idName"
    const val RARITY="rarity"
    const val ATTACK="attack"
    const val HEALTH="health"
    const val MANA="mana"
    val EMPTY_CARD= CardInfo(
        id = -1,
        idName = "",
        attack = -1,
        cardSet = "",
        cardType="",
        classType= ClassType.NEUTRAL,
        collectible=false,
        cropImage="",
        verbalText="",
        health=-1,
        image="",
        keywordIds= listOf(),
        manaCost=-1,
        minionType="",
        multiClassIds= listOf(),
        name="",
        rarity= Rarity.COMMON,
        text="",
        spellSchool = SpellSchool.NEUTRAL
    )
}