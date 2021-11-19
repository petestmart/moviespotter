package com.petestmart.moviespotter.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        color = if(isSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(horizontal = 4.dp)
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
                color = if(isSelected) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}