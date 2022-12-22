package com.mrickar.hearthstonedeckhelper.data.remote.dto

import com.mrickar.hearthstonedeckhelper.common.MetaData
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.CardInfo

data class CardInfoDto(
    val artistName: String="",
    val attack: Int=-1,
    val cardSetId: Int,
    val cardTypeId: Int,
    val childIds: List<Int>? = listOf(),
    val classId: Int,
    val collectible: Int,
    val copyOfCardId: Int=0,
    val cropImage: String?,
    val duels: Duels?=null,
    val flavorText: String,
    val health: Int=-1,
    val id: Int,
    val image: String,
    val imageGold: String="",
    var keywordIds: List<Int>? =  listOf(),
    val manaCost: Int,
    val minionTypeId: Int=0,
    var multiClassIds: List<Any>? = listOf(),
    val name: String,
    val rarityId: Int,
    val slug: String="",
    val text: String,
    val spellSchoolId: Int= -1
)
fun CardInfoDto.toCardInfo(): CardInfo {
    return CardInfo(
         attack=attack,
         cardSet=MetaData.SetById(cardSetId),
         cardType=MetaData.CardTypeById[cardTypeId]!!,
         classType=MetaData.ClassById[classId]!!,
         collectible=collectible==1,
         cropImage=cropImage?:"",
         verbalText=flavorText,
         health=health,
         id=id,
         image=image ,
         keywordIds= keywordIds?: listOf(),
         manaCost=manaCost,
         minionType=MetaData.MinionTypesById(minionTypeId),
         multiClassIds= multiClassIds?: listOf(),
         name=name,
         rarity=MetaData.RarityById[rarityId]!!,
         text=text,
         idName = slug,
         spellSchool=MetaData.SpellSchoolById(spellSchoolId)
    )
}