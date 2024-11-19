package com.principio.mobilebodegacharo.Model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.principio.mobilebodegacharo.DTO.DTOCarrusel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class CarruselModel {
    private val dbCate = FirebaseDatabase.getInstance()
        .reference.child("CarruselImage")

    fun obtenerImagenes(): Flow<List<DTOCarrusel>> {
        val flujo = callbackFlow {
            val listener = dbCate.addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val lista = snapshot.children.mapNotNull { ele ->
                            ele.getValue(DTOCarrusel::class.java) // Deserializa directamente
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