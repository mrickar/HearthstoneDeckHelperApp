package com.mrickar.hearthstonedeckhelper.presentation.search_screen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mrickar.hearthstonedeckhelper.common.QueryParameters
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.AllCardTypes
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.AllClassTypes
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.AllRarities
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(

): ViewModel(){
    val cardTextState= mutableStateOf("")
    val isClickedManaButtons = mutableStateListOf<Boolean>(false,false,false,false,false,false,false,false,false,false)
    val isClickedClassButtons = mutableStateListOf<Boolean>(false,false,false,false,false,false,false,false,false,false,false)
    val isCheckedCardTypes = mutableStateListOf(false,false,false)
    val isCheckedRarity = mutableStateListOf(false,false,false,false,false)

    val minAttackTF = mutableStateOf<String>(" ")
    val maxAttackTF = mutableStateOf<String>(" ")
    val minHealthTF = mutableStateOf<String>(" ")
    val maxHealthTF = mutableStateOf<String>(" ")

    fun getQueryParametersPath():String
    {
        var path="?"
        val textFilterQueryParameter = textFilterQueryParameter()

        if(textFilterQueryParameter !=""){
            path+="${QueryParameters.TEXT_FILTER}=$textFilterQueryParameter"
        }
        val manaCostsQueryParameter = manaCostsQueryParameter()
        if(manaCostsQueryParameter !=""){
            if (path!="?") path+="&"
            path+="${QueryParameters.MANA_COST}=$manaCostsQueryParameter"
        }
        val rarityQueryParameter = rarityQueryParameter()
        if(rarityQueryParameter !=""){
            if (path!="?") path+="&"
            path+="${QueryParameters.RARITY}=$rarityQueryParameter"
        }
        val classQueryParameter = classQueryParameter()
        if(classQueryParameter !=""){
            if (path!="?") path+="&"
            path+="${QueryParameters.CLASS}=$classQueryParameter"
        }
        val cardTypeQueryParameter = cardTypeQueryParameter()
        if(cardTypeQueryParameter !=""){
            if (path!="?") path+="&"
            path+="${QueryParameters.CARD_TYPE}=$cardTypeQueryParameter"
        }

        if(path=="?") return ""
        return path
    }
    fun textFilterQueryParameter():String
    {
        return cardTextState.value
    }

    fun manaCostsQueryParameter():String
    {
        var selectedManaCosts=""
        for(mana in 0..8)
        {
            if(isClickedManaButtons[mana])
            {
                selectedManaCosts+= "$mana,"
            }
        }
        if (isClickedManaButtons[9]) selectedManaCosts+="9,10,11,12,13,20,25"
        return selectedManaCosts
    }
    fun rarityQueryParameter():String
    {
        var selectedRarities=""
        for(rarityInd in isCheckedRarity.indices)
        {
            if(isCheckedRarity[rarityInd])
            {
                selectedRarities+="${AllRarities.raritiesList[rarityInd].rarity.lowercase()},"
            }
        }
        return selectedRarities
    }
    fun classQueryParameter():String
    {
        var selectedClasses=""
        for(classInd in isClickedClassButtons.indices)
        {
            if(isClickedClassButtons[classInd])
            {
                selectedClasses+="${AllClassTypes.classesList[classInd].className.lowercase()},"
            }
        }
        return selectedClasses
    }
    fun cardTypeQueryParameter():String
    {
        var selectedCardTypes=""
        for(i in AllCardTypes.cardTypesList.indices)
        {
            if(isCheckedCardTypes[i])
            {
                selectedCardTypes += "${AllCardTypes.cardTypesList[i].type.lowercase()},"
            }
        }
        if(selectedCardTypes =="") selectedCardTypes="minion,spell,weapon"
        return selectedCardTypes
    }
}