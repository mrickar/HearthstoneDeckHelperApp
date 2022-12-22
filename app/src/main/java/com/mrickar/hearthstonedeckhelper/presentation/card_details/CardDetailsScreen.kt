package com.mrickar.hearthstonedeckhelper.presentation.card_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.mrickar.hearthstonedeckhelper.R
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.Rarity
import com.mrickar.hearthstonedeckhelper.presentation.common.AddCardToDeckDialog
import com.mrickar.hearthstonedeckhelper.presentation.card_details.components.CardDDetailsTable
import com.mrickar.hearthstonedeckhelper.presentation.card_details.components.stat_circle.CardDetailsStats

@Composable
fun CardDetailsScreen(
     navController:NavController,
     viewModel: CardDetailsViewModel = hiltViewModel(),
)
{
     val state=viewModel.state.value
     val isDialogOpened=remember{ mutableStateOf(false)}
//     val state= CardDetailsState(IsMock.CARD_DTO_EXAMPLE.toCardInfo())
     Scaffold(
          topBar = {
               TopAppBar(
                    modifier = Modifier,
                    title = {
                    Row{
                         if(state.card!=null)
                         {
                              Text(state.card?.name?:"")
                              Box(modifier = Modifier.weight(1f)){
                                   Button(
                                        modifier = Modifier.align(Alignment.CenterEnd),
                                        onClick = {
                                             isDialogOpened.value=true
                                        },
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                                   ) {
                                        Text(text = "ADD")
                                   }
                              }
                         }
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
     ) {
          if(isDialogOpened.value)
          {
               AddCardToDeckDialog(
                    openDialog = isDialogOpened,
                    onSaveClicked = { deck,num ->
                         viewModel.addCardToDeck(deck = deck, cardCount = num, card = state.card!!)
                                    },
                    deckList = viewModel.getDeckList(),
                    isLegendary = state.card!!.rarity==Rarity.LEGENDARY,
                    cardClassName=state.card.classType.className
               )
          }
          Box(modifier = Modifier.fillMaxWidth())
          {
               if(state.card!=null)
               {
                    Column (
                         modifier = Modifier
                              .fillMaxWidth()
                              .padding(20.dp)
                              .verticalScroll(rememberScrollState()),
                         horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                         Row(modifier = Modifier.fillMaxWidth(),
                              verticalAlignment = Alignment.CenterVertically,
                              horizontalArrangement = Arrangement.SpaceEvenly
                         )
                         {
                              AsyncImage(
                                   modifier = Modifier,
                                   model = state.card.image,
                                   placeholder = painterResource(R.drawable.hs_card_back),
                                   contentScale = ContentScale.Fit,
                                   alignment = Alignment.Center,
                                   contentDescription = null)
                              if(state.card.cardType=="Minion")CardDetailsStats(cardInfo = state.card, diameter = 112)
                         }

                         CardDDetailsTable(cardInfo = state.card)
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

@Preview(showBackground = true)
@Composable
fun CardDetailsScreenPreview()
{
     CardDetailsScreen(rememberNavController())
}