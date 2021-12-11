package com.petestmart.moviespotter.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.petestmart.moviespotter.R
import kotlinx.coroutines.NonDisposableHandle.parent

const val DARK_THEME_ICON = R.drawable.ic_baseline_dark_mode_24
const val LIGHT_THEME_ICON = R.drawable.baseline_light_mode_24

@Composable
fun ThemeIconButton(
    onToggleTheme: () -> Unit,
    modifier: Modifier,
){
    IconButton(
        onClick = onToggleTheme,
    ) {
        if(MaterialTheme.colors.isLight){
            Icon(painter = painterResource(DARK_THEME_ICON),
                contentDescription = "Dark Mode Icon")
        } else {
            Icon(painter = painterResource(LIGHT_THEME_ICON),
                contentDescription = "Light Mode Icon")
        }
    }
}
