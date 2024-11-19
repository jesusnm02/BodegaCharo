package com.principio.mobilebodegacharo.Controller

import com.principio.mobilebodegacharo.DTO.DTOCarrusel
import com.principio.mobilebodegacharo.Model.CarruselModel
import kotlinx.coroutines.flow.Flow

class CarruselController {
    private val carruselModel = CarruselModel()
    val carrusel: Flow<List<DTOCarrusel>> = carruselModel.obtenerImagenes()
}