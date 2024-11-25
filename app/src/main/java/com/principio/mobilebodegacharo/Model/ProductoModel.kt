package com.principio.mobilebodegacharo.Model

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.principio.mobilebodegacharo.DTO.DTOPedidos
import com.principio.mobilebodegacharo.DTO.DTOProducto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProductoModel {
    private val dbCate = FirebaseDatabase.getInstance()
        .reference.child("Productos")

    fun obtenerProductos(
        categoriaId: Int
    ): Flow<List<DTOProducto>> =
        if(categoriaId == 0) obtenerTodosProductos()
        else obtenerProductosByCategoriaId(categoriaId)

    fun obtenerTodosProductos(): Flow<List<DTOProducto>> {
        val flujo = callbackFlow {
            val listener = dbCate.addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val lista = snapshot.children.mapNotNull { ele ->
                            ele.getValue(DTOProducto::class.java)
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

    fun obtenerProductosByCategoriaId(categoriaId: Int): Flow<List<DTOProducto>> {
        val flujo = callbackFlow {
            val listener = dbCate.addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val lista = snapshot.children.mapNotNull { ele ->
                            ele.getValue(DTOProducto::class.java)
                                ?.takeIf { it.CategoriaId == categoriaId }
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

    fun obtenerDetallesProductos(pedido: DTOPedidos): Flow<List<DTOProducto>> {
        val productoIds = pedido.Productos?.map { it.ProductoId } ?: emptyList()

        val flujo = callbackFlow {
            val listener = dbCate.addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        // Filtrar los productos por IDs
                        val lista = snapshot.children.mapNotNull { elemento ->
                            elemento.getValue(DTOProducto::class.java)
                                ?.takeIf { it.ProductoId in productoIds }
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

    fun obtenerProductoPorId(productoId: Int): Flow<DTOProducto> = callbackFlow {
        val query = FirebaseDatabase.getInstance().reference
            .child("Productos")
            .orderByChild("ProductoId")
            .equalTo(productoId.toDouble())

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val producto = snapshot.children.firstOrNull()?.getValue(DTOProducto::class.java)
                if (producto != null) {
                    trySend(producto).isSuccess
                } else {
                    close(Exception("Producto no encontrado"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        query.addListenerForSingleValueEvent(listener)
        awaitClose { query.removeEventListener(listener) }
    }
}