package com.mrickar.hearthstonedeckhelper.domain.model.card_model

enum class CardTypes(val type:String) {
    MINION(type = "Minion"),
    SPELL(type = "Spell"),
    WEAPON(type = "Weapon"),
}
object AllCardTypes{
    val cardTypesList=
        listOf(
            CardTypes.MINION,
            CardTypes.SPELL,
            CardTypes.WEAPON
        )
}