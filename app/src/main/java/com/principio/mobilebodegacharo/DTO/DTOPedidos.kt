package com.principio.mobilebodegacharo.DTO

data class DTOPedidos(
    var DNI: String? = null,
    var FechaInicio: String? = null,
    var PrecioTotal: Double? = null,
    var Productos: List<DTOListaProductos>? = null
)