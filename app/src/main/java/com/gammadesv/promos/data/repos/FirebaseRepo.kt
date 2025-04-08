package com.gammadesv.promos.data.repos

import android.net.Uri
import com.gammadesv.promos.data.models.Promocion
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FirebaseRepo {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    suspend fun buscarPromociones(
        dia: String? = null,
        tipoComida: String? = null,
        conAlcohol: Boolean? = null,
        ubicacion: String? = null
    ): List<Promocion> = try {
        // Cambiamos el tipo a Query
        val query: Query = db.collection("promociones").let { baseQuery ->
            var currentQuery: Query = baseQuery

            // Aplicamos filtros encadenados
            dia?.let { currentQuery = currentQuery.whereEqualTo("dia", it) }
            tipoComida?.let { currentQuery = currentQuery.whereEqualTo("comida", it) }
            conAlcohol?.let { currentQuery = currentQuery.whereEqualTo("alcohol", it) }
            ubicacion?.let { currentQuery = currentQuery.whereEqualTo("lugar", it) }

            currentQuery
        }

        query.get().await().toObjects(Promocion::class.java)
    } catch (e: Exception) {
        emptyList()
    }

    suspend fun getPromoById(promoId: String): Promocion? {
        return try {
            db.collection("promociones")
                .document(promoId)
                .get()
                .await()
                .toObject(Promocion::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun subirImagenPromocion(imagenUri: Uri): String {
        val ref = storage.reference.child("promos/${UUID.randomUUID()}")
        ref.putFile(imagenUri).await()
        return ref.downloadUrl.await().toString()
    }
}