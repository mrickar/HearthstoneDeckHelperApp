package com.mrickar.hearthstonedeckhelper.presentation

sealed class Screen(val route:String) {
    object CardListScreen:Screen("search_screen/card_list_screen")
    object CardDetailsScreen:Screen("card_details_screen")
    object SearchScreen:Screen("search_screen")
    object DeckListScreen:Screen("deck_list_screen")
    object ProfileScreen:Screen("profile_screen")
    object DeckDetailsScreen:Screen("deck_details_screen")
    object AuthScreen:Screen("auth_screen")
    object SignInScreen:Screen("auth_screen/sign_in_screen")
    object SignUpScreen:Screen("auth_screen/sign_up_screen")
    object SplashScreen:Screen("splash_screen")
}