package com.gammadesv.promos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.gammadesv.promos.ui.components.PromoCard
import com.gammadesv.promos.ui.viewmodels.PromoViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: PromoViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        // Filtros
        OutlinedTextField(
            value = "",
            onValueChange = { /* TODO */ },
            label = { Text("Tipo de comida") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de promociones
        when {
            state.isLoading -> CircularProgressIndicator()
            state.promociones.isEmpty() -> Text("No hay promociones disponibles")
            else -> LazyColumn {
                items(state.promociones) { promo ->
                    PromoCard(
                        promo = promo,
                        onClick = { navController.navigate("detalle/${promo.id}") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}