package com.mrickar.hearthstonedeckhelper.presentation.deck_list_screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrickar.hearthstonedeckhelper.R
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.Deck

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DeckListItem(
    deck:Deck,
    onItemClick:((Deck)->Unit)?,
    onEditClick:((Deck,String) ->Unit)?,
    onDeleteClick:((Deck) ->Unit)?,
    focusManager: FocusManager
){
    val isExpanded = remember { mutableStateOf(false)}
    val isEditClicked = remember { mutableStateOf(false)}

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .combinedClickable(
            onClick = {
                if (onItemClick != null) {
                    onItemClick(deck)
                }
            },
            onLongClick = {
                isExpanded.value = true
            }
        ),
        shape = RoundedCornerShape(percent = 25),
        elevation = 8.dp
    ){
        Column {
            if(!isEditClicked.value){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ){
                    Text(modifier = Modifier.padding(8.dp), text=deck.name)
                    Image(
                        painter = painterResource(id = deck.classType.heroImageId),
                        contentDescription = null,
                        alpha = 0.5f,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )


                }
                Box {
                    DropdownMenu(
                        expanded = isExpanded.value,
                        onDismissRequest = { isExpanded.value=false },
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                isEditClicked.value=true
                                isExpanded.value=false
                            }
                        ) {
                            Text("Edit")
                        }
                        DropdownMenuItem(
                            onClick = {
                                if (onDeleteClick != null) {
                                    onDeleteClick(deck)
                                }
                                isExpanded.value=false
                            }
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
            else{
                val newName = remember{ mutableStateOf<String>(deck.name)}
                Row{
                    TextField(
                        value = newName.value,
                        onValueChange = {
                            newName.value = it
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        )
                    )
                    IconButton(
                        modifier = Modifier.weight(1f),
                        onClick={
                            if(newName.value!="")
                            {
                                isEditClicked.value=false
                                if (onEditClick != null) {
                                    onEditClick(deck,newName.value)
                                }
                            }
                        },
                    ){
                        Icon(
                            painter = painterResource(R.drawable.ic_save),
                            contentDescription=null
                        )
                    }
                }

            }

        }
    }
}

@Preview(showBackground=true)
@Composable
fun DeckListItemPreview() {
    DeckListItem(deck = Deck(),null, focusManager = LocalFocusManager.current, onEditClick = null, onDeleteClick = null)
}
