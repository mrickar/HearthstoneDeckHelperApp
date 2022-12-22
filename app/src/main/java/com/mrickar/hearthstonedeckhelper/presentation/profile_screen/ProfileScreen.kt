package com.mrickar.hearthstonedeckhelper.presentation.profile_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mrickar.hearthstonedeckhelper.presentation.Screen
import com.mrickar.hearthstonedeckhelper.presentation.profile_screen.components.ProfileInfoItems
import com.mrickar.hearthstonedeckhelper.presentation.theme.LegendaryColor

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel= hiltViewModel(),
    navController: NavController
){
    val state=remember{viewModel.state}
//    val state=remember{ mutableStateOf(ProfileScreenState()) }
    if(state.value.isSignedOut)
    {
        LaunchedEffect(Unit)
        {
            navController.navigate(Screen.SignInScreen.route)
            {
                popUpTo(Screen.ProfileScreen.route)
                {
                    inclusive=true
                }
            }
        }

    }
    if(state.value.user!=null)
    {
        val tagList= listOf("Email")
        val infoList= listOf(state.value.user!!.email!!)
        Column(modifier = Modifier
            .fillMaxSize()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Profile",
                textAlign = TextAlign.Center,
                style= MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(24.dp))
            ProfileInfoItems(tagList = tagList, infoList =infoList , modifier = Modifier)
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = true)
            )
            {
                Button(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = {
                        viewModel.signOut()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = LegendaryColor)
                ) {
                    Text(text="Sign Out")
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize())
    {
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

@Preview(showBackground=true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}
