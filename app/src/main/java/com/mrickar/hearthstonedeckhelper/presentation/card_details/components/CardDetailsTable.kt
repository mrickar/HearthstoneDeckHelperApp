package com.mrickar.hearthstonedeckhelper.presentation.card_details.components

import android.os.Build
import android.text.Html
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrickar.hearthstonedeckhelper.R
import com.mrickar.hearthstonedeckhelper.common.IsMock
import com.mrickar.hearthstonedeckhelper.common.MetaData
import com.mrickar.hearthstonedeckhelper.data.remote.dto.toCardInfo
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.CardInfo


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardDDetailsTable(
    cardInfo: CardInfo
)
{
    val context=LocalContext.current
    val tableColor=Color.LightGray
    val tableModifier= Modifier
        .fillMaxWidth()
        .border(
            border = BorderStroke(
                width = 2.dp,
                color = tableColor
            ),
            shape = RoundedCornerShape(
                size = 4.dp
            )
        )
        .padding(8.dp)
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp))
    {
        //FIRST TABLE
        Column(
            modifier = tableModifier
        ) {
            val tableItemModifier:Modifier= Modifier
                .fillMaxWidth()
                .padding(4.dp)

            CardDetailsTableItem(modifier = tableItemModifier,tag="Name", cardStat = cardInfo.name)

            CardDetailsTableItem(modifier = tableItemModifier,tag="Rarity", cardStat = cardInfo.rarity.rarity, postImageId = cardInfo.rarity.imageId)

            CardDetailsTableItem(modifier = tableItemModifier,tag="Card Type", cardStat = cardInfo.cardType)

            CardDetailsTableItem(modifier = tableItemModifier,tag="Mana", cardStat = cardInfo.manaCost, postImageId = R.drawable.ic_mana)

            if(cardInfo.cardType=="Minion")
            {
                CardDetailsTableItem(modifier = tableItemModifier,tag="Attack", cardStat = cardInfo.attack, postImageId = R.drawable.ic_attack)

                CardDetailsTableItem(modifier = tableItemModifier,tag="Health", cardStat = cardInfo.attack, postImageId = R.drawable.ic_health)

                CardDetailsTableItem(modifier = tableItemModifier,tag="Minion Type", cardStat = cardInfo.minionType)
            }
            if(cardInfo.cardType=="Spell")
            {
                CardDetailsTableItem(modifier = tableItemModifier,tag="Spell School", cardStat = cardInfo.spellSchool.type)
            }
            CardDetailsTableItem(modifier = tableItemModifier,tag="Class", cardStat = cardInfo.classType.className, postImageId = cardInfo.classType.classImageId)

            CardDetailsTableItem(modifier = tableItemModifier,tag="Card Set", cardStat = cardInfo.cardSet)

            CardDetailsTableItem(modifier = tableItemModifier,tag="Collectible", cardStat = if (cardInfo.collectible) "True" else "False")
        }
        //CARD TEXT
        Column(modifier = tableModifier)
        {
            Text(
                text="Card Text:",
                style = MaterialTheme.typography.body2
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Text(
                    text = Html.fromHtml(cardInfo.text, Html.FROM_HTML_MODE_COMPACT).toString()
                )
            }
            else
            {
                Text(
                    text = cardInfo.text
                )
            }
        }

//        TAGS
        Column(modifier = tableModifier.height(120.dp)) {
            Text(
                text="Tags:",
                style = MaterialTheme.typography.body2
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                itemsIndexed(items=cardInfo.keywordIds)
                {   index,id->
                    val keyword=MetaData.KeywordByID(id)
                    val interactionSource = remember { MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()
                    Box(modifier = Modifier
                        .padding(8.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = LocalIndication.current
                        ) {})
                    {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 2.dp,
                                    shape = RoundedCornerShape(30),
                                    color = tableColor
                                ),
                                contentAlignment = Alignment.Center
                            ) {
                                Column{
                                    Text(
                                        modifier = Modifier.padding(2.dp),
                                        text = keyword.keyword,
                                    )
                                    Box{
                                        DropdownMenu(
                                            expanded = isPressed,
                                            onDismissRequest = {  }) {
                                            DropdownMenuItem(onClick = {  }) {
                                                Text(text = keyword.description, modifier = Modifier)
                                            }
                                        }
                                    }
                                }

                            }

                    }
                }
            }
        }
    }

}

@Composable
fun CardDetailsTableItem(
    modifier: Modifier,
    tag:String,
    cardStat:Any,
    postImageId:Int?=null
)
{
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically)
    {
        Text(
            text = "$tag : $cardStat",
        )
        if(postImageId!=null)
        {
            Image(painter = painterResource(id = postImageId), contentDescription =null, contentScale = ContentScale.FillHeight, modifier = Modifier
                .padding(4.dp)
                .height(16.dp))
        }

    }

    Divider(
        color = Color.LightGray,
        thickness = 1.dp
    )
}

@Preview(showBackground = true)
@Composable
fun CardDDetailsTablePreview()
{
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        CardDDetailsTable(IsMock.CARD_DTO_EXAMPLE.toCardInfo())
    }
}