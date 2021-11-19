package com.petestmart.moviespotter.presentation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavBar() {
    BottomNavigation(
        elevation = 12.dp
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(DARK_THEME_ICON),
                    contentDescription = "Dark Mode Icon"
                )
            },
            selected = false,
            onClick = { /*TODO*/ }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(LIGHT_THEME_ICON),
                    contentDescription = "Light Mode Icon"
                )
            },
            selected = false,
            onClick = { /*TODO*/ }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Dark Mode Icon"
                )
            },
            selected = true,
            onClick = { /*TODO*/ }
        )
    }
}