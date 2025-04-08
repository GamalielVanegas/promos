package com.gammadesv.promos.data.models

import com.google.firebase.firestore.PropertyName

data class Promocion(
    val id: String = "",

    @get:PropertyName("dia")
    val dia: String = "Hoy",

    @get:PropertyName("tipo_promocion")
    val tipo: String = "",

    @get:PropertyName("tipo_comida")
    val comida: String = "",

    val horario: String = "",
    val ambiente: String = "",
    val alcohol: Boolean = false,

    @get:PropertyName("ubicacion")
    val lugar: String = "",

    @get:PropertyName("imagen_url")
    val imagenUrl: String = "",

    @get:PropertyName("fecha_creacion")
    val fechaCreacion: Long = System.currentTimeMillis()
) {
    fun esValida(): Boolean {
        return dia.isNotBlank() && tipo.isNotBlank() &&
                comida.isNotBlank() && lugar.isNotBlank()
    }

    companion object {
        val DIAS = listOf("Hoy", "Esta semana", "Siempre")
        val TIPOS = listOf("2x1", "All you can eat", "Descuento", "Para cumpleañeros")
        val COMIDAS = listOf("Mexicana", "Italiana", "Pizza", "Pollo", "Carne")
    }
}