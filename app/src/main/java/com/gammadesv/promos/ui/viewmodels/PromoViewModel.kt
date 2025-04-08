package com.gammadesv.promos.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gammadesv.promos.data.models.Promocion
import com.gammadesv.promos.data.repos.FirebaseRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PromoViewModel(
    private val repo: FirebaseRepo
) : ViewModel() {

    private val _state = MutableStateFlow(PromoState())
    val state: StateFlow<PromoState> = _state.asStateFlow()

    init {
        loadPromociones() // Carga inicial al crear el ViewModel
    }

    fun loadPromociones(
        dia: String? = null,
        tipoComida: String? = null,
        conAlcohol: Boolean? = null,
        ubicacion: String? = null
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val promos = repo.buscarPromociones(dia, tipoComida, conAlcohol, ubicacion)
                _state.update {
                    it.copy(
                        promociones = promos,
                        filteredPromos = promos, // Inicializa los filtrados
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Error al cargar: ${e.localizedMessage}",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun filterPromos(query: String) {
        _state.update { currentState ->
            if (query.isBlank()) {
                currentState.copy(filteredPromos = currentState.promociones)
            } else {
                currentState.copy(
                    filteredPromos = currentState.promociones.filter { promo ->
                        promo.tipo.contains(query, ignoreCase = true) ||
                                promo.lugar.contains(query, ignoreCase = true) ||
                                promo.comida.contains(query, ignoreCase = true)
                    }
                )
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}

data class PromoState(
    val promociones: List<Promocion> = emptyList(),
    val filteredPromos: List<Promocion> = emptyList(), // Lista filtrada
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false // Para pull-to-refresh
)