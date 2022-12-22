package com.mrickar.hearthstonedeckhelper.presentation.card_details.components.stat_circle

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrickar.hearthstonedeckhelper.common.Constants
import com.mrickar.hearthstonedeckhelper.common.IsMock
import com.mrickar.hearthstonedeckhelper.data.remote.dto.toCardInfo
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.CardInfo
import com.mrickar.hearthstonedeckhelper.presentation.card_details.utils.statRadiusCalculator


@Composable
fun DrawStats(
    radius:Int,
    cardInfo: CardInfo
)
{
    val rarityRadius= (statRadiusCalculator(tag=Constants.RARITY, stat = cardInfo.rarity.value, radius = radius)).dp
    val atkRadius=(statRadiusCalculator(tag=Constants.ATTACK,stat=cardInfo.attack, radius = radius)).dp
    val manaRadius=(statRadiusCalculator(tag=Constants.MANA,stat=cardInfo.manaCost, radius = radius)).dp
    val healthRadius=(statRadiusCalculator(tag=Constants.HEALTH,stat=cardInfo.health, radius = radius)).dp

    Canvas(
        modifier = Modifier
            .width((radius*2).dp)
            .height((radius*2).dp)
            .fillMaxWidth(),
    )
    {
        val path: Path = Path().apply {
            val statRadiusPx=radius.dp.toPx()
            moveTo(statRadiusPx,statRadiusPx-rarityRadius.toPx())
            lineTo(statRadiusPx-atkRadius.toPx(),statRadiusPx)

            lineTo(statRadiusPx,statRadiusPx+manaRadius.toPx())

            lineTo(statRadiusPx+healthRadius.toPx(),statRadiusPx)
            lineTo(statRadiusPx,statRadiusPx-rarityRadius.toPx())
        }
        drawPath(
            path = path,
            brush= Brush.radialGradient(
                colors= listOf(Color.LightGray,cardInfo.rarity.color),
            ),
        )
    }
}

@Preview
@Composable
fun DrawStatsPreview()
{
    val cardInfo=IsMock.CARD_DTO_EXAMPLE.toCardInfo()
    DrawStats(96,cardInfo)
}