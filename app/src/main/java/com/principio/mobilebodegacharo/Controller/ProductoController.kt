package com.principio.mobilebodegacharo.Controller

import com.principio.mobilebodegacharo.DTO.DTOProducto
import com.principio.mobilebodegacharo.Model.ProductoModel
import kotlinx.coroutines.flow.Flow

class ProductoController(
    categoriaId: Int = 0
) {
    private val productoController = ProductoModel()
    val producto: Flow<List<DTOProducto>> = productoController.obtenerProductos(categoriaId)
}