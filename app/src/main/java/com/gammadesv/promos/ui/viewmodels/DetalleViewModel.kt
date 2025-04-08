package com.gammadesv.promos.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gammadesv.promos.data.repos.FirebaseRepo // Importación añadida
import com.gammadesv.promos.data.models.Promocion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetalleViewModel(
    private val promoId: String,
    private val repo: FirebaseRepo
) : ViewModel() {
    private val _promo = MutableStateFlow<Promocion?>(null)
    val promo: StateFlow<Promocion?> = _promo

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadPromo()
    }

    private fun loadPromo() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val promoDoc = repo.getPromoById(promoId)
                _promo.value = promoDoc
            } finally {
                _isLoading.value = false
            }
        }
    }
}