package com.example.mywatch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.mywatch.ui.theme.MyWatchTheme
import kotlin.collections.listOf
import androidx.navigation.navArgument
import androidx.navigation.compose.composable
import com.example.mywatch.viewmodel.WatchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyWatchTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.tertiary) {

                    val watchViewModel:WatchViewModel = viewModel()
                    WatchApp(watchViewModel)
                }




                }

            }
        }
    }


@SuppressLint("SuspiciousIndentation")
@Composable
fun WatchApp(watchViewModel: WatchViewModel) {
    val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "home",
        ) {
            composable("home") {
                HomeScreen(
                    watchViewModel = watchViewModel,
                    navigateToDetail = { watchId ->
                        navController.navigate("detail/$watchId")
                    },
                    navigationToAbout = {
                        navController.navigate("about")
                    },
                    navigationToFavorite = {
                        navController.navigate("favorites")
                    }
                )
            }
            composable(
                route = "detail/{watchId}",
                arguments = listOf(navArgument("watchId") { type = NavType.IntType })
            ) { backStackEntry ->
                val watchId = backStackEntry.arguments?.getInt("watchId") ?: -1
                DetailScreen(watchViewModel = watchViewModel, watchId = watchId, onBackClick = {
                    navController.navigateUp()
                } )

            }
            composable("about") {
                AboutScreen(
                    name = "Rofik Adam Nugraha",
                    email = "rofikadamnugraha@gmail.com",
                )
            }
            composable("favorites") {
                FavoriteScreen(
                    watchViewModel = watchViewModel,
                    onBackClick = {navController.navigateUp()}

                )
            }

        }
    }



