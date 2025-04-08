package com.gammadesv.promos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gammadesv.promos.data.repos.FirebaseRepo
import com.gammadesv.promos.data.models.Promocion
import com.gammadesv.promos.ui.theme.PromosTheme
import com.gammadesv.promos.ui.viewmodels.DetalleViewModel
import com.gammadesv.promos.ui.viewmodels.DetalleViewModelFactory
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallePromoScreen(
    promoId: String,
    navController: NavController? = null,
    viewModel: DetalleViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = DetalleViewModelFactory(
            promoId = promoId,
            repo = FirebaseRepo()
        )
    )
) {
    val promo by viewModel.promo.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalle Promoción") },
                navigationIcon = {
                    if (navController != null) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                promo == null -> Text(
                    "Promoción no encontrada",
                    modifier = Modifier.align(Alignment.Center)
                )
                else -> PromoDetalleContent(promo!!)
            }
        }
    }
}

@Composable
private fun PromoDetalleContent(promo: Promocion) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        if (promo.imagenUrl.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(promo.imagenUrl)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = "Imagen promoción",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = promo.tipo,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        PromoDetailItem("Restaurante", promo.comida)
        PromoDetailItem("Día", promo.dia)
        PromoDetailItem("Horario", promo.horario)
        PromoDetailItem("Ubicación", promo.lugar)

        if (promo.alcohol) {
            PromoDetailItem("Incluye alcohol", "Sí", Color.Green)
        }
    }
}

@Composable
private fun PromoDetailItem(
    label: String,
    value: String,
    color: Color = Color.Unspecified
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = color.takeIf { it != Color.Unspecified }
                ?: MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetallePromoScreenPreview() {
    PromosTheme {
        DetallePromoScreen(
            promoId = "123",
            navController = null
        )
    }
}