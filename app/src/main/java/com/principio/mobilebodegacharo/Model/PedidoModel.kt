package com.principio.mobilebodegacharo.Model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.principio.mobilebodegacharo.DTO.DTOPedidos
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class PedidoModel {
    private val dbPedidos: DatabaseReference = FirebaseDatabase.getInstance().getReference("Pedidos")

    // Función para guardar un pedido con clave generada automáticamente
    fun guardarPedido(pedido: DTOPedidos): Flow<Boolean> = flow {
        try {
            // Generar una clave única para el pedido
            val key = dbPedidos.push().key ?: throw Exception("Error al generar ID único para el pedido")

            // Llamar a la función suspendida para guardar en Firebase
            val resultado = guardarEnFirebase(pedido, key)
            emit(resultado) // Emitir el resultado
        } catch (e: Exception) {
            emit(false) // Emitir false si ocurre una excepción
        }
    }

    // Función suspendida para guardar en Firebase
    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun guardarEnFirebase(pedido: DTOPedidos, key: String): Boolean {
        return suspendCancellableCoroutine { continuation ->
            dbPedidos.child(key).setValue(pedido)
                .addOnSuccessListener {
                    continuation.resume(true) // Continuar con true si tiene éxito
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception) // Continuar con excepción si falla
                }
        }
    }

    fun obtenerPedidosPorDni(dni: String): Flow<List<DTOPedidos>> = callbackFlow {
        val query = dbPedidos.orderByChild("dni").equalTo(dni)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val pedidos = snapshot.children.mapNotNull { it.getValue(DTOPedidos::class.java) }
                Log.d("FirebasePedidos", "Pedidos obtenidos: $pedidos") // Registro de la lista de pedidos
                trySend(pedidos).isSuccess // Enviar lista de pedidos al flujo
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebasePedidos", "Error al obtener pedidos: ${error.message}") // Registrar el error
                close(error.toException()) // Cerrar el flujo si ocurre un error en Firebase
            }
        }

        // Registrar el listener en la consulta
        query.addListenerForSingleValueEvent(listener)

        // Limpiar el listener cuando se cierre el flujo
        awaitClose { query.removeEventListener(listener) }
    }
}