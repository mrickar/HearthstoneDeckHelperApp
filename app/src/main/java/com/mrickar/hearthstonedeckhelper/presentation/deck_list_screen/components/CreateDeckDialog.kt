package com.mrickar.hearthstonedeckhelper.presentation.deck_list_screen.components

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
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.AllClassTypes
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.ClassType

@Composable
fun CreateDeckDialog(
    openDialog:MutableState<Boolean>,
    onSaveClicked:((String,String)->Unit)?
){
    val deckName = remember{ mutableStateOf("")}
    val isClassMenuExpanded = remember{ mutableStateOf(false)}
    val selectedClass = remember{ mutableStateOf<String?>(null)}
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(text = "Create New Deck")
        },
        text = {
            Column {
                TextField(
                    modifier = Modifier.border(
                        width = 1.dp,
                        shape = RectangleShape,
                        color = Color.LightGray
                    ),
                    value = deckName.value,
                    onValueChange = {
                        deckName.value = it
                    },
                    label = {
                            Text(text="New Deck")
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),

                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier
                    .clickable(
                        onClick = {
                            isClassMenuExpanded.value = true
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
                        text = selectedClass.value?:"Class",
                    )
                    Icon(modifier = Modifier.align(Alignment.CenterEnd), painter = painterResource(id = if(!isClassMenuExpanded.value) R.drawable.ic_expand_more else R.drawable.ic_expand_less), contentDescription = null)
                    DropdownMenu(
                        expanded = isClassMenuExpanded.value,
                        onDismissRequest = { isClassMenuExpanded.value = false }) {
                        for (classType in AllClassTypes.classesList) {
                            if (classType != ClassType.NEUTRAL) {
                                DropdownMenuItem(onClick = {
                                    selectedClass.value = classType.name
                                    isClassMenuExpanded.value = false
                                }) {
                                    Text(text = classType.name)
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if(selectedClass.value!=null)
                    {
                        if (onSaveClicked != null) {
                            onSaveClicked(deckName.value, selectedClass.value!!)
                        }
                        openDialog.value = false
                    }
                }) {
                Text("Save")
            }
        }
    )
}

@Preview(showBackground=true)
@Composable
fun CreateDeckDialogPreview() {
    CreateDeckDialog( openDialog = remember {mutableStateOf(false)},null)
}
