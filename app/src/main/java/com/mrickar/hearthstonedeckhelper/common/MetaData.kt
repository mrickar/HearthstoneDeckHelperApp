package com.mrickar.hearthstonedeckhelper.common

import androidx.compose.ui.graphics.Color
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.ClassType
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.Keyword
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.Rarity
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.SpellSchool
import com.mrickar.hearthstonedeckhelper.presentation.theme.*

object MetaData {
    val RarityById: HashMap<Int, Rarity> = hashMapOf(
        1 to Rarity.COMMON,
        2 to Rarity.FREE,
        3 to Rarity.RARE,
        4 to Rarity.EPIC,
        5 to Rarity.LEGENDARY
    )
    fun RarityByName(name:String):Rarity = when(name) {
        Rarity.COMMON.name -> Rarity.COMMON
        Rarity.FREE.name -> Rarity.FREE
        Rarity.RARE.name -> Rarity.RARE
        Rarity.EPIC.name -> Rarity.EPIC
        Rarity.LEGENDARY.name -> Rarity.LEGENDARY
        else -> Rarity.FREE
    }
    val ColorByRarity: HashMap<String, Color> = hashMapOf(
        "Free" to FreeColor,
        "Common" to CommonColor,
        "Rare" to RareColor,
        "Epic" to EpicColor,
        "Legendary" to LegendaryColor
    )

    val ClassById: HashMap<Int, ClassType> = hashMapOf(
        2 to ClassType.DRUID,
        3 to ClassType.HUNTER,
        4 to ClassType.MAGE,
        5 to ClassType.PALADIN,
        6 to ClassType.PRIEST,
        7 to ClassType.ROGUE,
        8 to ClassType.SHAMAN,
        9 to ClassType.WARLOCK,
        10 to ClassType.WARRIOR,
        12 to ClassType.NEUTRAL,
        14 to ClassType.DEMON_HUNTER,
    )
    fun ClassByName(name:String): ClassType = when(name)
    {
        ClassType.DRUID.name->ClassType.DRUID
        ClassType.HUNTER.name -> ClassType.HUNTER
        ClassType.MAGE.name -> ClassType.MAGE
        ClassType.PALADIN.name->ClassType.PALADIN
        ClassType.PRIEST.name -> ClassType.PRIEST
        ClassType.ROGUE.name->ClassType.ROGUE
        ClassType.SHAMAN.name -> ClassType.SHAMAN
        ClassType.WARLOCK.name->ClassType.WARLOCK
        ClassType.WARRIOR.name -> ClassType.WARRIOR
        ClassType.DEMON_HUNTER.name->ClassType.DEMON_HUNTER
        else -> ClassType.NEUTRAL
    }

    val CardTypeById: HashMap<Int, String> = hashMapOf(
        3 to "Hero",
        4 to "Minion",
        5 to "Spell",
        7 to "Weapon",
    )
    fun MinionTypesById(id:Int):String{
        return when(id)
        {
            0 ->
            {
                "Basic"
            }
            1 ->
            {
                "Blood Elf"
            }
            2 ->
            {
                "Draenei"
            }3 ->
            {
                "Dwarf"
            }
            4 ->
            {
                "Gnome"
            }
            6 ->
            {
                "Human"
            }
            7 ->
            {
                "Night Elf"
            }
            8 ->
            {
                "Orc"
            }
            9 ->
            {
                "Tauren"
            }
            10 ->
            {
                "Troll"
            }
            11 ->
            {
                "Undead"
            }
            14 ->
            {
                "Murloc"
            }
            15 ->
            {
                "Demon"
            }
            17 ->
            {
                "Mech"
            }
            18 ->
            {
                "Elemental"
            }
            20 ->
            {
                "Beast"
            }
            21 ->
            {
                "Totem"
            }
            23 ->
            {
                "Pirate"
            }
            24 ->
            {
                "Dragon"
            }
            26 ->
            {
                "All"
            }
            43 ->
            {
                "Quilboar"
            }
            88 ->
            {
                "Half-Orc"
            }
            92 ->
            {
                "Naga"
            }
            93 ->
            {
                "Old God"
            }
            94 ->
            {
                "Pandaren"
            }
            95 ->
            {
                "Gronn"
            }
            else ->
            {
                ""
            }
        }
    }

    fun SpellSchoolById(id:Int): SpellSchool
    {
        return when(id)
        {
            1-> SpellSchool.ARCANE
            2-> SpellSchool.FIRE
            3-> SpellSchool.FROST
            4-> SpellSchool.NATURE
            5-> SpellSchool.HOLY
            6-> SpellSchool.SHADOW
            7-> SpellSchool.FEL
            else -> SpellSchool.NEUTRAL
        }
    }

    fun SetById(id:Int) : String{
        return when(id)
        {
            1691->"Murder at Castle Nathria"
            1658->"Voyage to the Sunken City"
            1637->"Core"
            1635->"Legacy"
            3->"Legacy"
            4->"Legacy"
            1626->"Fractured in Alterac Valley"
            1578->"United in Stormwind"
            1525->"Forged in the Barrens"
            1646->"Classic Cards"
            1466->"Madness at the Darkmoon Faire"
            1443->"Scholomance Academy"
            1463->"Demon Hunter Initiate"
            1414->"Ashes of Outland"
            1403->"Galakrond’s Awakening"
            1347->"Descent of Dragons"
            1158->"Saviors of Uldum"
            1130->"Rise of Shadows"
            1129->"Rastakhan’s Rumble"
            1127->"The Boomsday Project"
            1125->"The Witchwood"
            1004->"Kobolds and Catacombs"
            1001->"Knights of the Frozen Throne"
            27->"Journey to Un’Goro"
            25->"Mean Streets of Gadgetzan"
            23->"One Night in Karazhan"
            21->"Whispers of the Old Gods"
            20->"League of Explorers"
            15->"The Grand Tournament"
            14->"Blackrock Mountain"
            13->"Goblins vs Gnomes"
            12->"Curse of Naxxramas"
            else -> "Neutral"
        }
    }
    fun KeywordByID(id:Int): Keyword
    {
        return when(id)
        {
            1-> Keyword.TAUNT
            2-> Keyword.SPELL_DAMAGE
            3-> Keyword.DIVINE_SHIELD
            4-> Keyword.CHARGE
            5-> Keyword.SECRET
            6-> Keyword.STEALTH
            8-> Keyword.BATTLECRY
            10-> Keyword.FREEZE
            11-> Keyword.WINDFURY
            12-> Keyword.DEATHRATTLE
            13-> Keyword.COMBO
            14-> Keyword.OVERLOAD
            15-> Keyword.SILENCE
            16-> Keyword.COUNTER
            17-> Keyword.IMMUNE
            19-> Keyword.SPARE_PARTS
            20-> Keyword.INSPIRE
            21-> Keyword.DISCOVER
            22-> Keyword.CTHUN_OLD_GOD
            31-> Keyword.QUEST
            32-> Keyword.POISONOUS
            34-> Keyword.ADAPT
            38-> Keyword.LIFESTEAL
            39-> Keyword.RECRUIT
            52-> Keyword.ECHO
            53-> Keyword.RUSH
            61-> Keyword.OVERKILL
            64-> Keyword.START_OF_GAME
            66-> Keyword.MAGNETIC
            71-> Keyword.LACKEY
            76-> Keyword.TWINSPELL
            77-> Keyword.MEGA_WINDFURY
            78-> Keyword.REBORN
            79-> Keyword.INVOKE
            86-> Keyword.OUTCAST
            88-> Keyword.SPELLBURST
            89-> Keyword.SIDEQUEST
            91-> Keyword.CORRUPT
            92-> Keyword.START_OF_COMBAT
            96-> Keyword.QUESTLINE
            97-> Keyword.TRADEABLE
            99-> Keyword.FRENZY
            100-> Keyword.HONORABLE_KILL
            104-> Keyword.NATURE_SPELL_DAMAGE
            109-> Keyword.BLOOD_GEM
            198-> Keyword.AVENGE
            231-> Keyword.COLOSSAL
            232-> Keyword.DREDGE
            234-> Keyword.SPELLCRAFT
            235-> Keyword.ALLIED
            238-> Keyword.INFUSE
            else -> Keyword.EMPTY
        }
    }

    const val MAX_MANA=10
    const val MAX_ATK=10
    const val MAX_HEALTH=10
    const val MAX_RARITY=5

}