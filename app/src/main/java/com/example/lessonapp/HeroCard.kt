package com.example.lessonapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.compose.rememberAsyncImagePainter

@Composable
fun HeroCard(hero: Hero, onClickHero: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxSize(),
        elevation = CardDefaults.cardElevation(8.dp),
        onClick = onClickHero
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = rememberAsyncImagePainter(model = hero.imageUrl)
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