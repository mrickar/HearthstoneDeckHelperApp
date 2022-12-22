package com.mrickar.hearthstonedeckhelper.presentation.auth_screen.sign_in_screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mrickar.hearthstonedeckhelper.R
import com.mrickar.hearthstonedeckhelper.presentation.Screen
import com.mrickar.hearthstonedeckhelper.presentation.auth_screen.sign_in_screen.components.AuthTextField
import com.mrickar.hearthstonedeckhelper.presentation.theme.LegendaryColor

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel= hiltViewModel()
){
    val state=remember{viewModel.state}
//    val state=remember{ mutableStateOf(SignInState())}
    val textFieldModifier=Modifier.padding(8.dp)
    val focusManager= LocalFocusManager.current


    Column(modifier= Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures {
                focusManager.clearFocus()
            }
        }
        .background(color = LegendaryColor), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(modifier = Modifier.height(224.dp), painter = painterResource(id = R.drawable.hearthstone_logo), contentDescription = null)
        Text(text="Welcome to", style = MaterialTheme.typography.h6, color = Color.White)
        Text(text="HS Deck Builder", style = MaterialTheme.typography.h6, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text="Login", style = MaterialTheme.typography.body1, color = Color.White, fontStyle = FontStyle.Italic)
        AuthTextField(
            modifier=textFieldModifier,
            value = state.value.email,
            onValueChanged = {
                viewModel.setEmail(it)
            },
            keyboardType = KeyboardType.Email,
            label = "Email",
        )
        AuthTextField(
            modifier=textFieldModifier,
            value = state.value.password,
            onValueChanged = {
                viewModel.setPassword(it)
            },
            keyboardType = KeyboardType.Password,
            label = "Password",
            isPassword = true
        )
        Button(onClick = {
            viewModel.signIn()
        }) {
            Text(text="Sign in", textAlign = TextAlign.Center)
        }
        Row{
            Text(
                text = "Need an account, ",
                style = MaterialTheme.typography.body1,
                color = Color.White,
            )
            Text(
                modifier = Modifier.clickable {
                    navController.navigate(Screen.SignUpScreen.route)
                    {
                        popUpTo(Screen.SignInScreen.route)
                        {
                            inclusive=true
                        }
                    }
                },
                text = "Sign Up!",
                style = MaterialTheme.typography.body1,
                color = Color.LightGray,
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline
            )
        }

    }
    Box(modifier = Modifier.fillMaxSize())
    {
        if(state.value.isUserAuthenticated)
        {
            LaunchedEffect(Unit)
            {
                navController.navigate(Screen.DeckListScreen.route)
                {
                    popUpTo(Screen.SignInScreen.route)
                    {
                        inclusive=true
                    }
                }
            }
        }
        else if(state.value.isLoading)
        {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        else if (state.value.error.isNotBlank())
        {
            Toast.makeText(LocalContext.current,state.value.error,Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview(showBackground=true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(rememberNavController())
}
