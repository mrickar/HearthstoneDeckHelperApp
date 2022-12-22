package com.mrickar.hearthstonedeckhelper.domain.model.card_model

import androidx.compose.ui.graphics.Color
import com.mrickar.hearthstonedeckhelper.R

enum class ClassType(val className:String, val color:Color, val classImageId:Int,val heroImageId:Int) {
    DRUID(className = "Druid",color=Color(0xFFFF7C0A), classImageId = R.drawable.ic_druid,heroImageId=R.drawable.hero_druid),
    DEMON_HUNTER(className = "Demon Hunter",color=Color(0xFFA330C9),classImageId = R.drawable.ic_demon_hunter,heroImageId=R.drawable.hero_demon_hunter),
    HUNTER(className = "Hunter",color=Color(0xFFAAD372),classImageId = R.drawable.ic_hunter,heroImageId=R.drawable.hero_hunter),
    MAGE(className = "Mage",color=Color(0xFF3FC7EB),classImageId = R.drawable.ic_mage,heroImageId=R.drawable.hero_mage),
    PALADIN(className = "Paladin",color=Color(0xFFF48CBA),classImageId = R.drawable.ic_paladin,heroImageId=R.drawable.hero_paladin),
    PRIEST(className = "Priest",color=Color(0xFFFFFFFF),classImageId = R.drawable.ic_priest,heroImageId=R.drawable.hero_priest),
    ROGUE(className = "Rogue",color=Color(0xFFFFF468),classImageId = R.drawable.ic_rogue,heroImageId=R.drawable.hero_rogue),
    SHAMAN(className = "Shaman",color=Color(0xFF0070DD),classImageId = R.drawable.ic_shaman,heroImageId=R.drawable.hero_shaman),
    WARLOCK(className = "Warlock",color=Color(0xFF8788EE),classImageId = R.drawable.ic_warlock,heroImageId=R.drawable.hero_warlock),
    WARRIOR(className = "Warrior",color=Color(0xFFC69B6D),classImageId = R.drawable.ic_warrior,heroImageId=R.drawable.hero_warrior),
    NEUTRAL(className = "Neutral",color=Color.LightGray,classImageId = R.drawable.ic_neutral,heroImageId=R.drawable.ic_druid)
}

object AllClassTypes{
    val classesList=
        listOf<ClassType>(
            ClassType.NEUTRAL,
            ClassType.DRUID,
            ClassType.DEMON_HUNTER,
            ClassType.HUNTER,
            ClassType.MAGE,
            ClassType.PALADIN,
            ClassType.PRIEST,
            ClassType.ROGUE,
            ClassType.SHAMAN,
            ClassType.WARLOCK,
            ClassType.WARRIOR,
        )
}