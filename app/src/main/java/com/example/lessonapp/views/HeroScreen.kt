package com.example.lessonapp.views

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.lessonapp.R
import com.example.lessonapp.VMs.HeroScreenVM
import com.example.lessonapp.VMs.HeroState
import com.example.lessonapp.database.AppDatabase

class HeroScreenVMFactory(private val heroId: Long, private val db: AppDatabase) :
    ViewModelProvider.NewInstanceFactory() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun <T : ViewModel> create(modelClass: Class<T>): T = HeroScreenVM(heroId, db) as T
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun HeroScreen(heroId: Long, navController: NavController, vm: HeroScreenVM = viewModel(factory = HeroScreenVMFactory(heroId,
    AppDatabase.getDatabase(LocalContext.current)))) {
    val state by vm.state.collectAsState()

    Box(

    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.Black
            )
        }

        when (val currentState = state) {
            is HeroState.Loading -> LoadingView()
            is HeroState.Success ->
                Column(
                    modifier = Modifier.fillMaxSize().paint(
                        painter = rememberAsyncImagePainter(
                            model = currentState.hero?.thumbnail?.fullPath()
                                ?: R.drawable.ic_main_background
                        ),
                        contentScale = ContentScale.FillBounds
                    ),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    currentState.hero?.name?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(all = 15.dp),
                            style = TextStyle(
                                fontFamily = interFontFamily,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 28.sp,
                                color = Color.Black
                            )
                        )
                    }

                    currentState.hero?.description?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(all = 15.dp),
                            style = TextStyle(
                                fontFamily = interFontFamily,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 24.sp,
                                color = Color.Black
                            )
                        )
                    }
                }

            is HeroState.Error -> ErrorView(currentState.message, vm)
        }
    }
}
