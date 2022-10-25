package com.jhoangamarra.emovie.home.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.jhoangamarra.emovie.home.ui.HomeViewModel

@Composable
fun FilterOptionCard(onFilterMovies: (String, Boolean) -> Unit, option: HomeViewModel.FilterOption) {

    var isSelected by remember {
        mutableStateOf(false)
    }
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.wrapContentSize(),
        backgroundColor = if (isSelected) {
            Color.White
        } else {
            Color.DarkGray
        },
        onClick = {
            isSelected = !isSelected
            onFilterMovies(option.key, isSelected)
        }
    ) {
        Text(
            text = option.value,
            Modifier.padding(
                vertical = 8.dp,
                horizontal = 10.dp
            ),
            style = TextStyle(
                color = if (isSelected) {
                    Color.Black
                } else {
                    Color.White
                }
            )
        )
    }
}
