package com.principio.mobilebodegacharo.Controller

import com.principio.mobilebodegacharo.DTO.DTOCategoria
import com.principio.mobilebodegacharo.Model.CategoriaModel
import kotlinx.coroutines.flow.Flow

class CategorialController {
    private val categoriaModel = CategoriaModel()
    val categorias: Flow<List<DTOCategoria>> = categoriaModel.obtenerCategorias()
}
