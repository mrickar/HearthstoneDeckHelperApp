package com.mrickar.hearthstonedeckhelper.presentation.deck_details_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mrickar.hearthstonedeckhelper.presentation.Screen
import com.mrickar.hearthstonedeckhelper.presentation.deck_details_screen.components.CardInDeckListItem

@Composable
fun DeckDetailsScreen(
    navController:NavController,
    viewModel: DeckDetailsViewModel = hiltViewModel()
){
    val state=viewModel.state.value
    var tmpCardCount=state.deck?.cardCount
    var cardCountInDeck by remember{ mutableStateOf(tmpCardCount!!)}
//    val state= DeckDetailsState(deck= Deck(cards = mutableListOf(IsMock.CARD_DTO_EXAMPLE.toCardInfo().toCardInDeck())))
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Row(){
                        Text(state.deck?.name?:"")
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "${cardCountInDeck?:""}/30",
                            textAlign = TextAlign.End
                        )
                    }
                        },
                backgroundColor = Color.Transparent,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                elevation = 0.dp
            )
        }
    ){
        Column(modifier = Modifier
            .fillMaxWidth(),
            ) {
            if (state.deck != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        modifier = Modifier.align(Alignment.Center),
                        painter = painterResource(id = state.deck!!.classType.heroImageId),
                        contentDescription = null
                    )
                }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                items(state.deck?.cards ?: listOf()) { cardInDeck ->
                    CardInDeckListItem(
                        cardInDeck = cardInDeck,
                        onItemClicked = {
                            navController.navigate(Screen.CardDetailsScreen.route + "/${it.idName}")
                        },
                        onDeleteClicked = {deletedNum ->
                            viewModel.deleteCardFromDeck(
                                deletedNum = deletedNum,
                                cardInDeck.idName,
                                state.deck
                            )
                            cardCountInDeck -= deletedNum
                        },
                        onAddClicked = {
                            viewModel.addCardToDeck(
                                cardInDeck = cardInDeck,
                                deck = state.deck
                            )
                            cardCountInDeck += 1
                        },
                        isDeckCompleted = state.deck.isCompleted
                    )
                }
            }
        }
        }

        if(state.deck?.cards?.isEmpty() == true)
        {
            Box(modifier = Modifier.fillMaxSize())
            {
                Text(modifier = Modifier.align(Alignment.Center),text = "Deck is Empty")
            }
        }
    }
}

@Preview(showBackground=true)
@Composable
fun DeckDetailsScreenPreview() {
    DeckDetailsScreen(navController = rememberNavController())
}
