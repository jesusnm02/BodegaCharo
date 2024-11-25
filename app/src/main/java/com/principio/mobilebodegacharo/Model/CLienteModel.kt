package com.principio.mobilebodegacharo.Model

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.principio.mobilebodegacharo.DTO.DTOCliente
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CLienteModel {
    private val dbClientes = FirebaseDatabase.getInstance().reference.child("Clientes")

    fun guardarCliente(cliente: DTOCliente): Flow<Boolean> = flow {
        try {
            // Generar una clave única para el cliente
            val key = dbClientes.push().key ?: throw Exception("Error al generar ID único")

            // Guardar el cliente en la base de datos usando una función suspendida
            val resultado = guardarEnFirebase(cliente, key)

            // Emitir el resultado
            emit(resultado)
        } catch (e: Exception) {
            emit(false) // Emitir false si ocurre una excepción
        }
    }

    private suspend fun guardarEnFirebase(cliente: DTOCliente, key: String): Boolean {
        return suspendCancellableCoroutine { continuation ->
            dbClientes.child(key).setValue(cliente)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(true) // Operación exitosa
                    } else {
                        continuation.resume(false) // Operación fallida
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception) // Manejo de excepciones
                }
        }
    }

    fun verificarDniExistente(dni: String): Flow<Boolean> = callbackFlow {
        val query = dbClientes.orderByChild("dni").equalTo(dni) // Crear la consulta
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot.exists()).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException()) // Cerrar flujo en caso de error
            }
        }
        query.addListenerForSingleValueEvent(listener) // Agregar el listener a la consulta
        awaitClose { query.removeEventListener(listener) } // Eliminar el listener al cerrar el flujo
    }

    fun verificarEmailExistente(email: String): Flow<Boolean> = callbackFlow {
        val query = dbClientes.orderByChild("email").equalTo(email) // Crear la consulta
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot.exists()).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException()) // Cerrar flujo en caso de error
            }
        }
        query.addListenerForSingleValueEvent(listener) // Agregar el listener a la consulta
        awaitClose { query.removeEventListener(listener) } // Eliminar el listener al cerrar el flujo
    }
}
