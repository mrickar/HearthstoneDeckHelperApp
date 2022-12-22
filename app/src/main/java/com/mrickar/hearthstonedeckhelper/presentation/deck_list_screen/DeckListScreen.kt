package com.mrickar.hearthstonedeckhelper.presentation.deck_list_screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mrickar.hearthstonedeckhelper.R
import com.mrickar.hearthstonedeckhelper.presentation.Screen
import com.mrickar.hearthstonedeckhelper.presentation.deck_list_screen.components.CreateDeckDialog
import com.mrickar.hearthstonedeckhelper.presentation.deck_list_screen.components.DeckListItem
import com.mrickar.hearthstonedeckhelper.presentation.theme.LegendaryColor

@Composable
fun DeckListScreen(
    navController: NavController,
    viewModel: DeckListViewModel = hiltViewModel(),
){
    val state=viewModel.state
//    val state= remember {mutableStateOf(DeckListState(DeckList(decks = listOf(Deck(id = "1"), Deck(name = "deck2"),).toMutableStateList())))}
    val focusManager = LocalFocusManager.current
    val isDialogOpened=remember{ mutableStateOf(false)}
    Scaffold(
        floatingActionButton = {
                               FloatingActionButton(
                                   onClick = {
                                         isDialogOpened.value=true
                                             },
                                   backgroundColor = LegendaryColor
                               ) {
                                   Icon(
                                       painter = painterResource(id = R.drawable.ic_add),
                                       contentDescription = null
                                   )
                               }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures {
                focusManager.clearFocus()
            }
        }
    ) {
        if(isDialogOpened.value)
        {
            CreateDeckDialog(
                openDialog = isDialogOpened,
                onSaveClicked = { name,classType ->
                    viewModel.createNewDeck(name = name, classType = classType)
                }
            )
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            Column(modifier = Modifier
                .fillMaxWidth(),
            ) {
                Text(
                    text = "Decks",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style= MaterialTheme.typography.h6
                )
                if(state.value.deckList?.decks?.size == 0)
                {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "You don't have any decks yet."
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Let's create one."
                    )
                }
                LazyColumn(modifier = Modifier
                    .fillMaxWidth(),){
                    items(state.value.deckList?.decks?: listOf()){ deck->
                        DeckListItem(
                            deck = deck,
                            onItemClick = {
                                navController.navigate(Screen.DeckDetailsScreen.route+"/${it.id}")
                            },
                            focusManager = focusManager,
                            onEditClick = {deck,newName ->
                                viewModel.editDeckName(deck=deck, newName = newName)
                            },
                            onDeleteClick={
                                viewModel.deleteDeck(it)
                            }
                        )
                    }
                }
            }

            if(state.value.isLoading)
            {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else if (state.value.error.isNotBlank())
            {
                Text(
                    text = state.value.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }



}

@Preview(showBackground=true)
@Composable
fun DeckListScreenPreview() {
    DeckListScreen(navController = rememberNavController())
}
