package com.mrickar.hearthstonedeckhelper.presentation.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrickar.hearthstonedeckhelper.R
import com.mrickar.hearthstonedeckhelper.common.Constants
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.ClassType
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.Deck
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.DeckList

@Composable
fun AddCardToDeckDialog(
    openDialog:MutableState<Boolean>,
    onSaveClicked:((Deck, Int)->String)?,
    deckList: DeckList,
    isLegendary:Boolean,
    cardClassName:String
){
    val isDeckMenuExpanded = remember{ mutableStateOf(false)}
    val isCardNumMenuExpanded = remember{ mutableStateOf(false)}
    val selectedDeck = remember{ mutableStateOf<Deck?>(null)}
    val addedCardNum = remember{ mutableStateOf<Int?>(if(isLegendary) 1 else null)}
    val error= remember { mutableStateOf<String?>(null)}
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(text = "Add to a Deck")
        },
        text = {
            Column {
                Box(modifier = Modifier
                    .clickable(
                        onClick = {
                            isDeckMenuExpanded.value = true
                        })
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        shape = RectangleShape,
                        color = Color.LightGray
                    )
                    .padding(8.dp),
                ) {
                    Text(
                        text = selectedDeck.value?.name?:"Choose Deck",
                    )
                    Icon(modifier = Modifier.align(Alignment.CenterEnd), painter = painterResource(id = if(!isDeckMenuExpanded.value) R.drawable.ic_expand_more else R.drawable.ic_expand_less), contentDescription = null)
                    DropdownMenu(
                        expanded = isDeckMenuExpanded.value,
                        onDismissRequest = { isDeckMenuExpanded.value = false }) {
                        for(deck in deckList.decks)
                        {
                            if(!deck.isCompleted && (cardClassName ==ClassType.NEUTRAL.className || cardClassName == deck.classType.className))
                            {
                                DropdownMenuItem(onClick = {
                                    selectedDeck.value=deck
                                    isDeckMenuExpanded.value=false
                                }) {
                                    Row{
                                        Text(text= "${ deck.name }")
                                        Text(text = "(${deck.classType.className})", color = Color.LightGray)
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                if(!isLegendary)
                {
                    Box(modifier = Modifier
                        .clickable(
                            onClick = {
                                isCardNumMenuExpanded.value = true
                            })
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            shape = RectangleShape,
                            color = Color.LightGray
                        )
                        .padding(8.dp),
                    ) {
                        Text(
                            text = if(addedCardNum.value!=null) addedCardNum.value.toString() else "How many?",
                        )
                        Icon(modifier = Modifier.align(Alignment.CenterEnd), painter = painterResource(id = if(!isCardNumMenuExpanded.value) R.drawable.ic_expand_more else R.drawable.ic_expand_less), contentDescription = null)
                        DropdownMenu(
                            expanded = isCardNumMenuExpanded.value,
                            onDismissRequest = { isCardNumMenuExpanded.value = false }) {
                            for(i in 1..2)
                            {
                                DropdownMenuItem(onClick = {
                                    addedCardNum.value=i
                                    isCardNumMenuExpanded.value=false
                                }) {
                                    Text(text=i.toString())
                                }
                            }
                        }
                    }
                }

                if(error.value!=null)
                {
                    Text(text=error.value!!,color=Color.Red)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if(selectedDeck.value!=null && addedCardNum.value!=null)
                    {
                        if (onSaveClicked != null) {
                            val saveClickedResult = onSaveClicked(
                                selectedDeck.value!!, addedCardNum.value!!)
                                if (saveClickedResult == Constants.CARD_ADDED_SUCCESS
                                ) {
                                    openDialog.value = false
                                } else {
                                    error.value =saveClickedResult
                                }
                        }
                    }
                }) {
                Text("Save")
            }
        }
    )
}

@Preview(showBackground=true)
@Composable
fun AddToDeckDialogPreview() {
    AddCardToDeckDialog( openDialog = remember {mutableStateOf(false)},null, DeckList(),true,"Neutral")
}
