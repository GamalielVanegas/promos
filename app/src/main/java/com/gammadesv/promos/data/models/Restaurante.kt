package com.gammadesv.promos.data.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class Restaurante(
    @DocumentId
    val id: String = "",

    @get:PropertyName("nombre")
    val nombre: String = "",

    @get:PropertyName("ubicacion")
    val ubicacion: String = "",

    @get:PropertyName("coordenadas")
    val coordenadas: Map<String, Double> = emptyMap(),

    @get:PropertyName("tipo_cocina")
    val tipoCocina: String = "General",

    @get:PropertyName("promociones_activas")
    val promociones: List<String> = emptyList(),

    @get:PropertyName("horario")
    val horario: String = "L-D: 8:00 AM - 10:00 PM"
)