package com.gammadesv.promos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gammadesv.promos.ui.screens.DetallePromoScreen
import com.gammadesv.promos.ui.screens.HomeScreen
import com.gammadesv.promos.ui.theme.PromosTheme
import com.google.firebase.Firebase
import com.google.firebase.initialize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)

        setContent {
            PromosTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") { HomeScreen(navController) }
                    composable("detalle/{promoId}") { backStackEntry ->
                        val promoId = backStackEntry.arguments?.getString("promoId") ?: ""
                        DetallePromoScreen(promoId)
                    }
                }
            }
        }
    }
}