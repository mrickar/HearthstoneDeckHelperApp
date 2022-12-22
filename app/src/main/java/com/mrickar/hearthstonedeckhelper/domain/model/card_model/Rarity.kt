package com.mrickar.hearthstonedeckhelper.domain.model.card_model

import androidx.compose.ui.graphics.Color
import com.mrickar.hearthstonedeckhelper.R
import com.mrickar.hearthstonedeckhelper.presentation.theme.*

enum class Rarity(val rarity:String, val color: Color,val imageId:Int,val colorsUntil:List<Color>,val value:Int, val dustToMake:Int, val dustToBreak:Int){
    FREE("Free", FreeColor, R.drawable.ic_common, listOf(Color.LightGray,Color.LightGray),1,0,0,),
    COMMON("Common", CommonColor,R.drawable.ic_common,listOf(CommonColor,CommonColor),2,40,5),
    RARE("Rare", RareColor,R.drawable.ic_rare,listOf(CommonColor, RareColor),3,100,20),
    EPIC("Epic", EpicColor,R.drawable.ic_epic,listOf(CommonColor, RareColor, EpicColor),4,400,100),
    LEGENDARY("Legendary", LegendaryColor,R.drawable.ic_legendary,listOf(CommonColor, RareColor, EpicColor, LegendaryColor),5,1600,400),
}

object AllRarities{
    val raritiesList=
        listOf(
            Rarity.FREE,
            Rarity.COMMON,
            Rarity.RARE,
            Rarity.EPIC,
            Rarity.LEGENDARY
        )
}

