package com.mrickar.hearthstonedeckhelper.common

import com.mrickar.hearthstonedeckhelper.data.remote.dto.CardInfoDto

object IsMock {
    const val isMock=false
    const val UID="uid"
    val CARD_DTO_EXAMPLE= CardInfoDto(
        id = 581,
        attack=8,
        cardSetId=3,
        cardTypeId=4,
        classId=12,
        collectible=1,
        cropImage= "https://d15f34w2p8l1cc.cloudfront.net/hearthstone/9761698fe3b982c9a5cc21d22f492ae29696feca09ac8854309931685fc85ef8.png",
        flavorText="Alexstrasza the Life-Binder brings life and hope to everyone. Except Deathwing. And Malygos. And Nekros.",
        health=8,
        image="https://d15f34w2p8l1cc.cloudfront.net/hearthstone/c2cc82bd2bcb3f706e299f31d5309f71d6b01e9d0b69b1f377307ed166ed8176.png",
        keywordIds = listOf(14,1,5,6,8,10,11,12,13),
        manaCost=9,
        minionTypeId=24,
        multiClassIds=listOf(),
        name="Alexstrasza",
        rarityId=5,
        text="<b>Battlecry:</b> Set a hero's remaining Health to 15.",
        slug = "581-alexstrasza"
    )
}