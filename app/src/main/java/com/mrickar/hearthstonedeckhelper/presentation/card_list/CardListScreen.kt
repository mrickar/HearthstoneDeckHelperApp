package com.mrickar.hearthstonedeckhelper.presentation.card_list.components


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.CardInfo
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.Rarity
import com.mrickar.hearthstonedeckhelper.presentation.Screen
import com.mrickar.hearthstonedeckhelper.presentation.card_list.CardListViewModel
import com.mrickar.hearthstonedeckhelper.presentation.common.AddCardToDeckDialog

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardListScreen(
    navController: NavController,
    viewModel: CardListViewModel = hiltViewModel()
)
{
    val state=viewModel.state.value
//    val state=CardListState()

    val isDialogOpened= remember{ mutableStateOf(false) }
    val cardToAdd= remember{ mutableStateOf<CardInfo?>(null) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
//        floatingActionButton = {
//            FloatingActionButton(onClick = {
//                navController.navigate(Screen.SearchScreen.route)
//            }) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_search),
//                    contentDescription=null
//                )
//            }},
//            floatingActionButtonPosition = FabPosition.End
            ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            )
            {
                if(isDialogOpened.value)
                {
                    AddCardToDeckDialog(
                        openDialog = isDialogOpened,
                        onSaveClicked = { deck,num ->
                            viewModel.addCardToDeck(deck = deck, cardCount = num, card = cardToAdd.value!!)
                        },
                        deckList = viewModel.getDeckList(),
                        isLegendary = cardToAdd.value!!.rarity== Rarity.LEGENDARY,
                        cardClassName = cardToAdd.value!!.classType.className
                    )
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    if(state.cardSearchResult?.pageCount!! > 1)
                    {
                        PageNavigation(
                            modifier = Modifier.fillMaxWidth(),
                            curPage = state.cardSearchResult.curPage,
                            pageCount = state.cardSearchResult.pageCount,
                            isNextButtonClickable = state.isNextPageAvailable,
                            isPreviousButtonClickable = state.isPreviousPageAvailable,
                            onButtonOnClick = {
                                viewModel.loadNewPage(it)
                            })
                    }
                    LazyVerticalGrid(
                        modifier=Modifier.fillMaxWidth(),
                        columns = GridCells.Adaptive(96.dp) ,
                        contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp)
                    )
                    {
                        items(items= state.cardSearchResult.cards ?: listOf()){ card->
                            CardListItem(
                                cardInfo = card,
                                onItemClick = {
                                    navController.navigate(Screen.CardDetailsScreen.route + "/${it.idName}")
                                },
                                openDialog = isDialogOpened,
                                clickedCard = cardToAdd
                            )
                        }

                    }
                }
                if(state.isLoading)
                {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else if (state.error.isNotBlank())
                {
                    Text(
                        text = state.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
}
@Preview
@Composable
fun CardListScreenPreview()
{
    CardListScreen(navController = rememberNavController())
}