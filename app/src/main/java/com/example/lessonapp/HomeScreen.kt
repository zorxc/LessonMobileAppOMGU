package com.example.lessonapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle


import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

val interFontFamily = FontFamily(
    Font(R.font.inter_extrabold),
    Font(R.font.inter_regular),
    Font(R.font.inter_bold)
)

@Composable
fun HomeScreen(navController: NavController) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().paint(
            painter = painterResource(R.drawable.ic_main_background),
            contentScale = ContentScale.FillBounds
        )
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 40.dp)
        )

        Text(
            text = "Choose your hero",
            style = TextStyle(
                fontFamily = interFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                color = Color.White
            ),
            modifier = Modifier.padding(top = 16.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                horizontal = 20.dp, vertical = 40.dp
            )
        ) {
            items(HeroesList().heroList) { hero ->
                HeroCard(
                    hero = hero,
                    onClickHero = {
                        navController.navigate(Screens.HeroScreen.name + "/${hero.name}")
                    })
            }
        }
    }
}