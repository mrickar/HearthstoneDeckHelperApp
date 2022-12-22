package com.mrickar.hearthstonedeckhelper.presentation.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.Rarity

@Composable
fun PartlyColoredText(
    textList: List<String>,
    colorList:List<Color?>
){
    Row(modifier=Modifier.fillMaxWidth())
    {
        for(i in textList.indices)
        {
            Text(
                modifier=Modifier,
                text = textList[i],
                color = if(colorList[i]!=null) colorList[i]!! else Color.Unspecified
                )
            Text(text = " ")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PartlyModifiedTextPreview()
{
    val textList:List<String> = listOf<String>("Name:","Alex")
    val colorList = listOf<Color?>(null, Rarity.LEGENDARY.color)
    PartlyColoredText(textList, colorList)
}