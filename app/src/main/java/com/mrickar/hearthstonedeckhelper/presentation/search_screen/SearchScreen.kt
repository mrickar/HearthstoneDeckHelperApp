package com.mrickar.hearthstonedeckhelper.presentation.search_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mrickar.hearthstonedeckhelper.R
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.AllCardTypes
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.AllClassTypes
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.AllRarities
import com.mrickar.hearthstonedeckhelper.presentation.Screen

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchScreenViewModel= hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    var cardText by remember { viewModel.cardTextState }
    val isClickedManaButtons = remember { viewModel.isClickedManaButtons}
    val isClickedClassButtons = remember { viewModel.isClickedClassButtons}
    val isCheckedCardTypes = remember {viewModel.isCheckedCardTypes}
    val isCheckedRarity=remember{viewModel.isCheckedRarity}

//    var minAttackTF by remember { viewModel.minAttackTF }
//    var maxAttackTF by remember { viewModel.maxAttackTF }
//    var minHealthTF by remember { viewModel.minHealthTF }
//    var maxHealthTF by remember { viewModel.maxHealthTF }



    ConstraintLayout(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures {
                focusManager.clearFocus()
            }
        }
        .padding(8.dp)
    ) {
        val (nameSearch,manaButtons,cardTypeCheckboxes,statTextFields,rarityCheckboxes,classButtons,searchButton) = createRefs()

        //CARD NAME TEXT FIELD
        OutlinedTextField(
            modifier = Modifier.constrainAs(nameSearch)
            {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            },
            value = cardText,
            onValueChange = { newText ->
                cardText = newText
            },
            label = { Text(text = "Card Text") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
        )

        //MANA BUTTONS
        Column(modifier = Modifier
            .constrainAs(manaButtons)
            {
                top.linkTo(nameSearch.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(top = 8.dp),) {
            Text("Mana:")
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                for(i in 0..9)
                {
                    Box(modifier = Modifier.clickable {
                        isClickedManaButtons[i]=!isClickedManaButtons[i]
                    })
                    {
                        Image(
                            modifier= Modifier
                                .width(32.dp)
                                .align(Alignment.Center),
                            painter = painterResource(R.drawable.ic_mana),
                            contentDescription=null,
                            contentScale = ContentScale.FillWidth,
                            alpha = if(isClickedManaButtons[i]) 1f else 0.5f
                        )
                        Text(modifier = Modifier.align(Alignment.Center),text = if(i!=9) i.toString() else "$i+", color = Color.White)
                    }
                }
            }
        }

        //RARITY CHECKBOXES
        Column(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(rarityCheckboxes)
            {
                top.linkTo(manaButtons.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(top = 8.dp),
        ){
            Text("Rarity:")
            Row(modifier = Modifier
                .fillMaxWidth(),
            )
            {
                for(i in 0..2)
                {
                    Row(verticalAlignment = Alignment.CenterVertically)
                    {
                        Checkbox(
                            modifier = Modifier,
                            checked = isCheckedRarity[i],
                            onCheckedChange = {
                                isCheckedRarity[i]=it
                            }
                        )
                        Text(AllRarities.raritiesList[i].rarity)
                    }
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
            )
            {
                for(i in 3..4)
                {
                    Row(verticalAlignment = Alignment.CenterVertically)
                    {
                        Checkbox(
                            modifier = Modifier,
                            checked = isCheckedRarity[i],
                            onCheckedChange = {
                                isCheckedRarity[i]=it
                            }
                        )
                        Text(AllRarities.raritiesList[i].rarity)
                    }
                }
            }

        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(classButtons)
            {
                top.linkTo(rarityCheckboxes.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(top = 8.dp),) {
            Text("Class:")
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                for(i in 0..5)
                {
                    Box(modifier = Modifier.clickable {
                        isClickedClassButtons[i]=!isClickedClassButtons[i]
                    })
                    {
                        Image(
                            modifier= Modifier
                                .width(40.dp)
                                .align(Alignment.Center),
                            painter = painterResource(AllClassTypes.classesList[i].classImageId),
                            contentDescription=null,
                            contentScale = ContentScale.FillWidth,
                            alpha = if(isClickedClassButtons[i]) 1f else 0.5f
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                for(i in 6..10)
                {
                    Box(modifier = Modifier.clickable {
                        isClickedClassButtons[i]=!isClickedClassButtons[i]
                    })
                    {
                        Image(
                            modifier= Modifier
                                .width(40.dp)
                                .align(Alignment.Center),
                            painter = painterResource(AllClassTypes.classesList[i].classImageId),
                            contentDescription=null,
                            contentScale = ContentScale.FillWidth,
                            alpha = if(isClickedClassButtons[i]) 1f else 0.5f
                        )
                    }
                }
            }

        }

        //CARD TYPE CHECKBOXES
        Column(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(cardTypeCheckboxes)
            {
                top.linkTo(classButtons.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(top = 8.dp),
        ){
            Text("Card Type:")
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                for(i in AllCardTypes.cardTypesList.indices)
                {
                    Row(verticalAlignment = Alignment.CenterVertically)
                    {
                        Checkbox(
                            modifier = Modifier,
                            checked = isCheckedCardTypes[i],
                            onCheckedChange = {
                                isCheckedCardTypes[i]=it
                            }
                        )
                        Text(AllCardTypes.cardTypesList[i].type)
                    }
                }
            }
        }

        //ATTACK-HEALTH INTERVALS
//        if(isMinionTypeCheckBox)
//        {
//            Row(modifier = Modifier
//                .constrainAs(statTextFields){
//                top.linkTo(cardTypeCheckboxes.bottom)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            })
//            {
//                Row(modifier = Modifier.weight(1f),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                )
//                {
//                    Text(
//                        modifier= Modifier
//                            .padding(8.dp)
//                            .weight(0.33f),
//                        text=Constants.ATTACK
//                    )
//                    OutlinedTextField(
//                        modifier = Modifier.weight(0.33f),
//                        value = minAttackTF,
//                        onValueChange = {
//                            minAttackTF=it
//                        },
//                        label={Text("min")},
//                        keyboardOptions = KeyboardOptions(
//                            keyboardType=KeyboardType.Number,
//                            imeAction = ImeAction.Done
//                        )
//                    )
//                    Text(
//                        modifier=Modifier.padding(8.dp),
//                        text="-"
//                    )
//                    OutlinedTextField(
//                        modifier = Modifier.weight(0.33f),
//                        value = maxAttackTF,
//                        onValueChange = {
//                            maxAttackTF=it
//                        },
//                        label={Text("max")},
//                        keyboardOptions = KeyboardOptions(
//                            keyboardType=KeyboardType.Number,
//                            imeAction = ImeAction.Done
//                        )
//                    )
//                }
//                Spacer(modifier = Modifier.width(8.dp))
//                Row(modifier = Modifier.weight(1f),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Start
//                )
//                {
//                    Text(
//                        modifier= Modifier
//                            .padding(8.dp)
//                            .weight(0.33f),
//                        text=Constants.HEALTH
//                    )
//                    OutlinedTextField(
//                        modifier = Modifier.weight(0.33f),
//                        value = minHealthTF,
//                        onValueChange = {
//                            minHealthTF=it
//                        },
//                        label={Text("min")},
//                        keyboardOptions = KeyboardOptions(
//                            keyboardType=KeyboardType.Number,
//                            imeAction = ImeAction.Done
//                        )
//                    )
//                    Text(
//                        modifier=Modifier.padding(8.dp),
//                        text="-"
//                    )
//                    OutlinedTextField(
//                        modifier = Modifier.weight(0.33f),
//                        value = maxHealthTF,
//                        onValueChange = {
//                            maxHealthTF=it
//                        },
//                        label={Text("max")},
//                        keyboardOptions = KeyboardOptions(
//                            keyboardType=KeyboardType.Number,
//                            imeAction = ImeAction.Done
//                        )
//                    )
//                }
//            }
//        }

        Button(
            modifier=Modifier.constrainAs(searchButton){
//                if(isMinionTypeCheckBox) top.linkTo(statTextFields.bottom)
//                else top.linkTo(cardTypeCheckboxes.bottom)
                top.linkTo(cardTypeCheckboxes.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            onClick = {
                val path=viewModel.getQueryParametersPath()
                navController.navigate(Screen.CardListScreen.route+path){
                    popUpTo(Screen.CardListScreen.route+path)
                    {
                        inclusive=true
                    }
                }
            }
        ) {
            Text("Search")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SearchComponentPreview()
{
    SearchScreen(navController = rememberNavController())
}