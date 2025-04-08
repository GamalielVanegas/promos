package com.gammadesv.promos.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gammadesv.promos.data.models.Promocion

@Composable
fun PromoCard(promo: Promocion, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(promo.tipo, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text("${promo.comida} · ${promo.dia}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(promo.lugar)
            if (promo.alcohol) {
                Text("Incluye alcohol", color = Color.Green)
            }
        }
    }
}