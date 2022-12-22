package com.mrickar.hearthstonedeckhelper.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mrickar.hearthstonedeckhelper.common.Constants
import com.mrickar.hearthstonedeckhelper.common.QueryParameters
import com.mrickar.hearthstonedeckhelper.presentation.auth_screen.sign_in_screen.SignInScreen
import com.mrickar.hearthstonedeckhelper.presentation.auth_screen.sign_up_screen.SignUpScreen
import com.mrickar.hearthstonedeckhelper.presentation.card_details.CardDetailsScreen
import com.mrickar.hearthstonedeckhelper.presentation.card_list.components.CardListScreen
import com.mrickar.hearthstonedeckhelper.presentation.deck_details_screen.DeckDetailsScreen
import com.mrickar.hearthstonedeckhelper.presentation.deck_list_screen.DeckListScreen
import com.mrickar.hearthstonedeckhelper.presentation.profile_screen.ProfileScreen
import com.mrickar.hearthstonedeckhelper.presentation.search_screen.SearchScreen
import com.mrickar.hearthstonedeckhelper.presentation.splash_screen.SplashScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route)
    {
        composable(
            route = Screen.CardListScreen.route+"?${QueryParameters.TEXT_FILTER}={${QueryParameters.TEXT_FILTER}}"+"&${QueryParameters.MANA_COST}={${QueryParameters.MANA_COST}}"+"&${QueryParameters.RARITY}={${QueryParameters.RARITY}}"+"&${QueryParameters.CLASS}={${QueryParameters.CLASS}}"+"&${QueryParameters.CARD_TYPE}={${QueryParameters.CARD_TYPE}}",
            arguments = listOf(
                navArgument(QueryParameters.TEXT_FILTER){
                    nullable=true
                },
                navArgument(QueryParameters.MANA_COST){
                    nullable=true
                },
                navArgument(QueryParameters.RARITY){
                    nullable=true
                },
                navArgument(QueryParameters.CLASS){
                    nullable=true
                },
                navArgument(QueryParameters.CARD_TYPE){
                    defaultValue = "minion,spell,weapon"
                },
            )
        )
        {
            CardListScreen(navController)
        }
        composable(
            route= Screen.CardDetailsScreen.route+"/{${Constants.ID_NAME}}"
        ){
            CardDetailsScreen(navController)
        }
        composable(
            route=Screen.SearchScreen.route
        ){
            SearchScreen(navController)
        }
        composable(
            route=Screen.DeckListScreen.route
        ){
            DeckListScreen(navController)
        }
        composable(
            route=Screen.ProfileScreen.route
        ){
            ProfileScreen(navController = navController)
        }
        composable(
            route=Screen.DeckDetailsScreen.route+"/{${Constants.DECK_ID}}"
        ){
            DeckDetailsScreen(navController)
        }
        composable(
            route=Screen.SignInScreen.route
        ){
            SignInScreen(navController)
        }
        composable(
            route=Screen.SignUpScreen.route
        ){
            SignUpScreen(navController)
        }
        composable(
            route=Screen.SplashScreen.route
        ){
            SplashScreen(navController)
        }
    }
}
