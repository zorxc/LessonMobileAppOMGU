package com.example.lessonapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroScreen(heroName: String?, navController: NavController) {
    val hero: Hero? = HeroesList().heroList.find { it.name == heroName }

    Box(
        modifier = Modifier.fillMaxSize().paint(
            painter = rememberAsyncImagePainter(model = hero?.imageUrl),
            contentScale = ContentScale.FillBounds
        )
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            hero?.name?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(all = 15.dp),
                    style = TextStyle(
                        fontFamily = interFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 28.sp,
                        color = Color.White
                    )
                )
            }

            hero?.description?.let {
                Text(
                    text = it,
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
}