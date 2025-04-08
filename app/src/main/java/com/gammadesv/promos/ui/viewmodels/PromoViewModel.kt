package com.gammadesv.promos.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gammadesv.promos.data.repos.FirebaseRepo // Importación corregida
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PromoViewModel(private val repo: FirebaseRepo) : ViewModel() {
    private val _state = MutableStateFlow(PromoState())
    val state: StateFlow<PromoState> = _state

    fun loadPromociones(
        dia: String? = null,
        tipoComida: String? = null,
        conAlcohol: Boolean? = null,
        ubicacion: String? = null
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val promos = repo.buscarPromociones(dia, tipoComida, conAlcohol, ubicacion)
                _state.value = _state.value.copy(
                    promociones = promos,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
}

data class PromoState(
    val promociones: List<Promocion> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)