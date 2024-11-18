package com.principio.mobilebodegacharo.Model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.principio.mobilebodegacharo.DTO.DTOCategoria
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.Flow

class CategoriaModel {
    private val dbCate = FirebaseDatabase.getInstance()
        .reference.child("Categoria")

    fun obtenerCategorias(): Flow<List<DTOCategoria>> {
        val flujo = callbackFlow {
            val listener = dbCate.addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val lista = snapshot.children.mapNotNull { ele ->
                            ele.getValue(DTOCategoria::class.java) // Deserializa directamente
                        }
                        Log.d("FirebaseData", "Categorias: $lista") // Debug
                        trySend(lista).isSuccess
                    }

                    override fun onCancelled(error: DatabaseError) {
                        close(error.toException())
                    }
                }
            )
            awaitClose { dbCate.removeEventListener(listener) }
        }
        return flujo
    }
}