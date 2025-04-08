package com.gammadesv.promos.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gammadesv.promos.data.repos.FirebaseRepo  // Importación exacta

class DetalleViewModelFactory(
    private val promoId: String,
    private val repo: FirebaseRepo
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(DetalleViewModel::class.java)) {
            "Unknown ViewModel class: ${modelClass.name}"
        }
        return DetalleViewModel(promoId, repo) as T
    }
}