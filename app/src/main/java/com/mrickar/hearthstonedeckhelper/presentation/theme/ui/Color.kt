package com.mrickar.hearthstonedeckhelper.presentation.theme

import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val FreeColor= Color(0xffffffff)
val CommonColor= Color(0xff9d9d9d)
val RareColor= Color(0xff0070dd)
val EpicColor= Color(0xffa335ee)
val LegendaryColor= Color(0xffff8000)
val BackgroundColor=Color(0xFFFFC31F)

fun colorListUntilRarity(rarity:String):List<Color>
{
    val colors= mutableListOf<Color>(CommonColor)
    if(rarity=="Common") return colors
    colors.add(RareColor)
    if(rarity=="Rare") return colors
    colors.add(EpicColor)
    if(rarity=="Epic") return colors
    colors.add(LegendaryColor)
    if(rarity=="Legendary") return colors
    return listOf(Color.LightGray)

}
