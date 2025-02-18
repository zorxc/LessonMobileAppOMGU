package com.example.lessonapp.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.example.lessonapp.models.Character

@Composable
fun HeroCard(hero: Character, onClickHero: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxSize(),
        elevation = CardDefaults.cardElevation(8.dp),
        onClick = onClickHero
    ) {
        Box(
            modifier = Modifier.fillMaxSize().paint(
                painter = rememberAsyncImagePainter(model = hero.thumbnail.fullPath()),
                contentScale = ContentScale.FillBounds
            ),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = hero.name,
                modifier = Modifier.padding(all = 15.dp),
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp,
                    color = Color.White
                )
            )
        }
    }
}