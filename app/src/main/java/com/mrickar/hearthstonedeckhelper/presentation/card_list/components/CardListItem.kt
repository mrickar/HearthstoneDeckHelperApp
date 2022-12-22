package com.mrickar.hearthstonedeckhelper.presentation.card_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mrickar.hearthstonedeckhelper.R
import com.mrickar.hearthstonedeckhelper.common.IsMock
import com.mrickar.hearthstonedeckhelper.data.remote.dto.toCardInfo
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.CardInfo

@Composable
fun CardListItem(
    cardInfo: CardInfo,
    onItemClick:((CardInfo)->Unit)?,
    clickedCard:MutableState<CardInfo?>,
    openDialog: MutableState<Boolean>,
)
{
    Box()
    {
        AsyncImage(
            modifier=Modifier.clickable {
                if (onItemClick != null) {
                    onItemClick(cardInfo)
                }
            },
            model = cardInfo.image,
            contentDescription = null,
            placeholder = painterResource(R.drawable.hs_card_back),
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center
            )
        FloatingActionButton(
            onClick = {
                clickedCard.value=cardInfo
                openDialog.value=true
            },
            backgroundColor = Color.LightGray,
            modifier = Modifier.align(
                Alignment.TopEnd
            )
                .padding(8.dp)
                .size(24.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = null, tint = Color.White)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CardListItemPreview()
{
    val cardInfo= IsMock.CARD_DTO_EXAMPLE.toCardInfo()
    CardListItem(cardInfo = cardInfo, onItemClick = null, openDialog =  remember{mutableStateOf(false)}, clickedCard =  remember{mutableStateOf(null)})
}