package com.mrickar.hearthstonedeckhelper.presentation.auth_screen.sign_in_screen.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.mrickar.hearthstonedeckhelper.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthTextField(
    modifier: Modifier,
    label:String,
    value:String,
    onValueChanged:((String)->Unit)?,
    keyboardType: KeyboardType,
    isPassword:Boolean=false
){
    val keyboardController = LocalSoftwareKeyboardController.current
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier,
        singleLine=true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions= KeyboardActions(
            onDone = {keyboardController?.hide()}
        ),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
        visualTransformation = if (passwordVisible || !isPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if(isPassword)
            {
                IconButton(
                    onClick={
                        passwordVisible=!passwordVisible
                    },
                ){
                    Icon(
                        painter = if(passwordVisible) painterResource(R.drawable.ic_pass_not_visible) else painterResource(R.drawable.ic_pass_visible),
                        contentDescription=null
                    )
                }
            }
        },
        label = {
                Text(text=label)
        },
        value = value,
        onValueChange = {
            if (onValueChanged != null) {
                onValueChanged(it)
            }
        }
    )
}

@Preview(showBackground=true)
@Composable
fun SignInTextFieldPreview() {
    AuthTextField(
        modifier = Modifier,
        value = "",
        onValueChanged = null,
        keyboardType = KeyboardType.Email,
        label = "email",
        isPassword = true,
    )
}
