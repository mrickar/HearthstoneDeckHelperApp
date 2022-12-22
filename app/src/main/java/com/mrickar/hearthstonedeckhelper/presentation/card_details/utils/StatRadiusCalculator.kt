package com.mrickar.hearthstonedeckhelper.presentation.card_details.utils

import android.util.Log
import com.mrickar.hearthstonedeckhelper.common.Constants
import com.mrickar.hearthstonedeckhelper.common.MetaData

fun statRadiusCalculator(
    tag:String,
    stat:Int,
    radius:Int
):Float
{
    if(tag==Constants.RARITY) return radius*stat/MetaData.MAX_RARITY.toFloat()
    val maxFactor : Int= when(tag)
    {
        Constants.ATTACK-> MetaData.MAX_ATK*(MetaData.MAX_ATK)
        Constants.HEALTH-> MetaData.MAX_HEALTH* (MetaData.MAX_HEALTH)
        Constants.MANA->MetaData.MAX_MANA *(MetaData.MAX_MANA)
        else -> {
            100
            Log.d("utils/Stat.CalculatorKt","Unknown Tag")
        }
    }
    val maxStat : Int= when(tag)
    {
        Constants.ATTACK-> MetaData.MAX_ATK
        Constants.HEALTH -> MetaData.MAX_HEALTH
        Constants.MANA ->MetaData.MAX_MANA
        else -> {
            10
            Log.d("utils.kt","Unknown Tag")
        }
    }
    if(stat*(stat)/maxFactor.toFloat() > 1) return radius.toFloat()

    return radius*(maxFactor-(maxStat-stat)*(maxStat-stat))/maxFactor.toFloat()
}