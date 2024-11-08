package com.principio.mobilebodegacharo.Model

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.principio.mobilebodegacharo.DTO.DTOCategoria
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.Flow

class ModelCategoria {
    private val dbCate = FirebaseDatabase.getInstance()
        .reference.child("Categorias")

    fun obtenerCategorias(): Flow<List<DTOCategoria>> {
        val flujo = callbackFlow {
            val listener = dbCate.addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val lista = snapshot.children.mapNotNull { ele->
                            val categorias = ele.getValue(DTOCategoria::class.java)
                            ele.key?.let { categorias?.copy(CategoriaId = it) }
                        }
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