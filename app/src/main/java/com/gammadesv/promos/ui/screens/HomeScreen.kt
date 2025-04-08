package com.gammadesv.promos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment  // Importación añadida
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.gammadesv.promos.ui.components.PromoCard
import com.gammadesv.promos.ui.viewmodels.PromoViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: PromoViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                viewModel.filterPromos(query)
            },
            label = { Text("Filtrar promociones") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading -> {
                Box(
                    contentAlignment = Alignment.Center,  // Uso correcto de Alignment
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            state.error != null -> {
                Text("Error: ${state.error}")
            }
            state.filteredPromos.isEmpty() -> {
                Text("No hay promociones disponibles")
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.filteredPromos) { promo ->
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
}