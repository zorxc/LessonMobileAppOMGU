package com.example.lessonapp.views

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle


import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lessonapp.R
import com.example.lessonapp.VMs.HeroScreenVM
import com.example.lessonapp.VMs.HeroesState
import com.example.lessonapp.VMs.HomeScreenVM
import com.example.lessonapp.VMs.IViewModel
import com.example.lessonapp.database.AppDatabase

val interFontFamily = FontFamily(
    Font(R.font.inter_extrabold),
    Font(R.font.inter_regular),
    Font(R.font.inter_bold)
)

class HomeScreenVMFactory(private val db: AppDatabase) :
    ViewModelProvider.NewInstanceFactory() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun <T : ViewModel> create(modelClass: Class<T>): T = HomeScreenVM(db) as T
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun HomeScreen(navController: NavController, vm: HomeScreenVM = viewModel(factory = HomeScreenVMFactory(AppDatabase.getDatabase(
    LocalContext.current)))) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val state by vm.state.collectAsState()

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

        when (val currentState = state) {
            is HeroesState.Loading -> LoadingView()
            is HeroesState.Success ->
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(
                        horizontal = 20.dp, vertical = 40.dp
                    ),
                ) {
                    items(currentState.heroes) { hero ->
                        HeroCard(
                            hero = hero,
                            onClickHero = {
                                navController.navigate(Screens.HeroScreen.name + "/${hero.id}")
                            })
                        }
                }
            is HeroesState.Error -> ErrorView(currentState.message, vm)
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ErrorView(message: String, vm: IViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "⚠️ $message", style = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            color = Color.Black
        ))
        IconButton(
            onClick = {
                vm.load()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}