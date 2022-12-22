package com.mrickar.hearthstonedeckhelper.presentation.main_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mrickar.hearthstonedeckhelper.R
import com.mrickar.hearthstonedeckhelper.presentation.Navigation
import com.mrickar.hearthstonedeckhelper.presentation.Screen
import com.mrickar.hearthstonedeckhelper.presentation.main_screen.components.BottomNavItem
import com.mrickar.hearthstonedeckhelper.presentation.main_screen.components.BottomNavigationBar
import com.mrickar.hearthstonedeckhelper.presentation.theme.HearthstoneDeckHelperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HearthstoneDeckHelperTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
//                    color = LegendaryColor
                )
                {
                    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

                    val navController= rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    when (navBackStackEntry?.destination?.route){
                        Screen.SignInScreen.route ->{
                            bottomBarState.value=false
                        }
                        Screen.SignUpScreen.route ->{
                            bottomBarState.value=false
                        }
                        Screen.SplashScreen.route ->{
                            bottomBarState.value=false
                        }
                        else ->{
                            bottomBarState.value=true
                        }
                    }
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                modifier = Modifier,
                                items = listOf(
                                    BottomNavItem(
                                        name="Search",
                                        route = Screen.SearchScreen.route,
                                        iconId = R.drawable.ic_search
                                    ),
                                    BottomNavItem(
                                        name="Decks",
                                        route = Screen.DeckListScreen.route,
                                        iconId = R.drawable.ic_cards
                                    ),
                                    BottomNavItem(
                                        name="Profile",
                                        route = Screen.ProfileScreen.route,
                                        iconId = R.drawable.ic_profile
                                    )
                                ),
                                navController = navController,
                                onItemClick = {
                                    navController.navigate(it.route){
                                        popUpTo(navController.currentDestination!!.route!!)
                                        {
                                            inclusive=true
                                        }
                                    }
                                },
                                bottomBarState = bottomBarState
                            )
                        }
                    ) {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(it))
                        {
                            Navigation(navController = navController)
                        }

                    }
                }
            }
        }
    }
}


