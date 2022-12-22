package com.mrickar.hearthstonedeckhelper.presentation.splash_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mrickar.hearthstonedeckhelper.R
import com.mrickar.hearthstonedeckhelper.presentation.Screen
import com.mrickar.hearthstonedeckhelper.presentation.theme.LegendaryColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
){
    LaunchedEffect(key1 = true)
    {
        delay(2500)
        navController.navigate(Screen.SignInScreen.route)
        {
            popUpTo(0)
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = LegendaryColor),
        contentAlignment = Alignment.Center
    ){
        Icon(
            painter = painterResource(id = R.drawable.ic_cards),
            contentDescription =null,
            modifier = Modifier.height(96.dp),
            tint = Color.White
        )
    }
}

@Preview(showBackground=true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(rememberNavController())
}
