package com.principio.mobilebodegacharo.Controller

import com.principio.mobilebodegacharo.DTO.DTOCategoria
import com.principio.mobilebodegacharo.Model.CategoriaModel
import kotlinx.coroutines.flow.Flow

class CategorialController {
    private val categoriaModel = CategoriaModel()
    var categorias: Flow<List<DTOCategoria>>? = null

    init {
        obtenerCategorias()
    }

    fun obtenerCategorias() {
        categorias = categoriaModel.obtenerCategorias()
    }
}