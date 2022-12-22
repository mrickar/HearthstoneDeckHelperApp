package com.mrickar.hearthstonedeckhelper.presentation.main_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mrickar.hearthstonedeckhelper.R
import com.mrickar.hearthstonedeckhelper.presentation.Screen
import com.mrickar.hearthstonedeckhelper.presentation.theme.LegendaryColor

@Composable
fun BottomNavigationBar(
    modifier: Modifier,
    items:List<BottomNavItem>,
    navController: NavController,
    onItemClick:(BottomNavItem) ->Unit,
    bottomBarState: MutableState<Boolean>
){
    val backStackEntry= navController.currentBackStackEntryAsState()
    AnimatedVisibility(visible =bottomBarState.value ) {
        BottomNavigation(
            modifier = modifier,
            backgroundColor = LegendaryColor,
            elevation = 8.dp
        ) {
            items.forEach{ item ->
                backStackEntry.value?.destination?.hierarchy?.any{
                    item.route==it.route
                }
                val selected=backStackEntry.value?.destination?.route?.contains(item.route)?:false
                BottomNavigationItem(
                    selected = selected,
                    onClick = {
                        onItemClick(item)
                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Gray,
                    icon = {
                        Column(horizontalAlignment = CenterHorizontally) {
                            Icon(
                                painter = painterResource(id = item.iconId),
                                contentDescription = null
                            )
                            if(selected)
                            {
                                Text(text=item.name)
                            }
                        }
                    }
                )
            }
        }
    }

}

@Preview(showBackground=true)
@Composable
fun BottomNavigationBarPreview() {
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
        navController = rememberNavController(),
        onItemClick = { println("ads")},
        bottomBarState = mutableStateOf(true)
    )
}
