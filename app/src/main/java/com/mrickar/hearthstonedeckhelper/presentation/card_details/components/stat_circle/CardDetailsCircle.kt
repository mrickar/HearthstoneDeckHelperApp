package com.mrickar.hearthstonedeckhelper.presentation.card_details.components.stat_circle

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrickar.hearthstonedeckhelper.common.IsMock
import com.mrickar.hearthstonedeckhelper.data.remote.dto.toCardInfo
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.CardInfo


@Composable
fun CardDetailsStats(
    cardInfo: CardInfo,
    diameter:Int
)
{
    val sweepAngle=90F
    val radius=diameter/2

    Box(
        modifier = Modifier
            .padding(20.dp)
            .padding(top = 10.dp)
            .width(diameter.dp)
            .height(diameter.dp),
        contentAlignment = Alignment.Center
    )
    {
        DrawStats( radius = radius, cardInfo = cardInfo)
        CircleWithTags(rarityColor = cardInfo.rarity.color, diameter = diameter, sweepAngle = sweepAngle)
//        LayoutOfLines(cardInfo.rarity.color,diameter)
    }
}
@Preview(showBackground = true)
@Composable
fun CardDetailsStatsPreview()
{
    val cardInfo=IsMock.CARD_DTO_EXAMPLE.toCardInfo()
    CardDetailsStats(cardInfo,180)


}
