package com.mrickar.hearthstonedeckhelper.presentation.deck_details_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.mrickar.hearthstonedeckhelper.common.IsMock
import com.mrickar.hearthstonedeckhelper.data.remote.dto.toCardInfo
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.Rarity
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.toCardInDeck
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.CardInDeck
import com.mrickar.hearthstonedeckhelper.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardInDeckListItem(
    cardInDeck: CardInDeck,
    isDeckCompleted:Boolean,
    onItemClicked:((CardInDeck)->Unit)?,
    onDeleteClicked:((Int)->Unit)?,
    onAddClicked:(()->Unit)?,
){
    val isExpanded = remember { mutableStateOf(false) }
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(40.dp)
        .combinedClickable(
            onClick = {
                if (onItemClicked != null) {
                    onItemClicked(cardInDeck)
                }
            },
            onLongClick = {
                isExpanded.value = true
            }
        ),
        shape = AbsoluteCutCornerShape(percent = 40),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
        ){
        Column {
            Box{
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "${cardInDeck.name} x${cardInDeck.cardNum}",
                        color = cardInDeck.rarity.color
                    )
                }
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End) {
                    Text(text = cardInDeck.manaCost.toString())
                    Image(
                        modifier = Modifier.height(12.dp),
                        painter = painterResource(id = R.drawable.ic_mana),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight
                    )
                    AsyncImage(
                        modifier = Modifier.fillMaxHeight().width(96.dp),
                        model = cardInDeck.cropImage,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        alignment = Alignment.CenterEnd,
                    )
                }
            }
            Box {
                DropdownMenu(
                    expanded = isExpanded.value,
                    onDismissRequest = { isExpanded.value=false },
                ) {
                    if(cardInDeck.cardNum!=2 && cardInDeck.rarity!=Rarity.LEGENDARY && !isDeckCompleted)
                    {
                        DropdownMenuItem(
                            onClick = {
                                isExpanded.value=false
                                if (onAddClicked != null) {
                                    println("***** CardInDeckListItem *****")
                                    onAddClicked()
                                    println("----- CardInDeckListItem -----")
                                }
                            }
                        ) {
                            Text("Add")
                        }
                    }
                    if(cardInDeck.cardNum!=1)
                    {
                        DropdownMenuItem(
                            onClick = {
                                isExpanded.value=false
                                if (onDeleteClicked != null) {
                                    onDeleteClicked(1)
                                }
                            }
                        ) {
                            Text("Delete one")
                        }
                    }
                    DropdownMenuItem(
                        onClick = {
                            isExpanded.value=false
                            if (onDeleteClicked != null) {
                                onDeleteClicked(cardInDeck.cardNum)
                            }
                        }
                    ) {
                        Text("Delete all")
                    }
                }
            }
        }


    }
}

@Preview(showBackground=true)
@Composable
fun CardItemPreview() {
    CardInDeckListItem(IsMock.CARD_DTO_EXAMPLE.toCardInfo().toCardInDeck(),onItemClicked = null, onDeleteClicked = null,onAddClicked = null, isDeckCompleted = false)
}
