package com.petestmart.moviespotter.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LeftDrawer() {
    Column(
        modifier = Modifier.padding(12.dp)
    ) {
        Text("You found the hidden drawer")
    }
}