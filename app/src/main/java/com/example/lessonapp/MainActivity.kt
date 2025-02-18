package com.example.lessonapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lessonapp.ui.theme.LessonAppTheme
import com.example.lessonapp.views.HeroScreen
import com.example.lessonapp.views.HomeScreen
import com.example.lessonapp.views.Screens

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConfigureNavigation()
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ConfigureNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.name
    ) {
        composable(
            Screens.HomeScreen.name,
        ) {
            HomeScreen(navController)
        }

        composable(
            route = Screens.HeroScreen.name + "/{heroId}",
            arguments = listOf(navArgument("heroId") { type = NavType.LongType })
        ) { backStackEntry ->
            val heroId = backStackEntry.arguments?.getLong("heroId") ?: -1
            HeroScreen(heroId, navController)
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LessonAppTheme {
        HomeScreen(rememberNavController())
    }
}