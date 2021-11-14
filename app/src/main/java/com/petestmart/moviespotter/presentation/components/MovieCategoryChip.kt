package com.petestmart.moviespotter.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.reflect.KFunction1

@Composable
fun MovieCategoryChip(
    category: String,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: (String) -> Unit,
    newCategorySearch: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .padding(end = 8.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        color = if(isSelected) Color.LightGray else MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier
                .toggleable(
                    value = isSelected,
                    onValueChange = {
                        onSelectedCategoryChanged(category)
                        newCategorySearch()
                    }
                )
        ) {
            Text(
                text = category,
                style = typography.body2,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}