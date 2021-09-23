package com.example.stateflowtest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stateflowtest.Article
import com.example.stateflowtest.Tag

@Composable
fun Radio(
    article: Article,
    onClick: (Article, Tag) -> Unit
) {
    Column  {
        article.tags.map() { tag ->
            Row() {
                RadioButton(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(20.dp),
                    onClick = { onClick(article, tag) },
                    enabled = true,
                    selected = tag.isSelected,
                    colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
                )
                Text(
                    text = tag.label,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}