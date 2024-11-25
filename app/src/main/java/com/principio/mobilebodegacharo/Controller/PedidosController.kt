package com.principio.mobilebodegacharo.Controller

import com.principio.mobilebodegacharo.DTO.DTOCliente
import com.principio.mobilebodegacharo.DTO.DTOListaProductos
import com.principio.mobilebodegacharo.DTO.DTOPedidos
import com.principio.mobilebodegacharo.DTO.DTOProducto
import com.principio.mobilebodegacharo.Model.CLienteModel
import com.principio.mobilebodegacharo.Model.PedidoModel
import com.principio.mobilebodegacharo.Model.ProductoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.text.SimpleDateFormat
import java.util.Date

class PedidosController {
    private val _productosCarrito =
        MutableStateFlow<List<DTOListaProductos>>(emptyList()) // Flujo de productos
    val productosCarrito: StateFlow<List<DTOListaProductos>> get() = _productosCarrito

    private val _precioTotal = MutableStateFlow(0.0) // Estado observable del precio total
    val precioTotal: StateFlow<Double> get() = _precioTotal

    val pedido = DTOPedidos()

    private val productoModel = ProductoModel()
    private val clienteModel = CLienteModel()
    private val pedidoModel = PedidoModel()

    // Función para agregar un producto o incrementar cantidad
    fun agregarPrecioTotal(precio: Double, listaProducto: DTOListaProductos) {
        if (pedido.Productos == null) {
            pedido.Productos = mutableListOf()
        }

        val productos = pedido.Productos as MutableList<DTOListaProductos>
        val productoExistente = productos.find { it.ProductoId == listaProducto.ProductoId }

        if (productoExistente != null) {
            productoExistente.Cantidad =
                productoExistente.Cantidad?.plus(1) // Incrementamos la cantidad
        } else {
            productos.add(listaProducto.copy(Cantidad = 1)) // Si no existe, lo agregamos con cantidad 1
        }

        _productosCarrito.value = productos.toList() // Emitimos el cambio de la lista
        actualizarPrecioTotal(precio)
    }

    // Función para restar cantidad de producto
    fun restarCantidadProducto(precio: Double, productoId: Int) {
        if (pedido.Productos.isNullOrEmpty()) return

        val productos = pedido.Productos as MutableList<DTOListaProductos>
        val productoExistente = productos.find { it.ProductoId == productoId }

        if (productoExistente != null) {
            productoExistente.Cantidad = productoExistente.Cantidad?.minus(1) // Restamos cantidad
            if (productoExistente.Cantidad!! <= 0) {
                productos.remove(productoExistente) // Si la cantidad llega a 0, lo eliminamos
            }
        }

        _productosCarrito.value = productos.toList() // Emitimos la lista actualizada
        actualizarPrecioTotal(-precio) // Restamos el precio correspondiente
    }

    // Función para sumar cantidad de producto
    fun sumarCantidadProducto(precio: Double, productoId: Int) {
        if (pedido.Productos.isNullOrEmpty()) return

        val productos = pedido.Productos as MutableList<DTOListaProductos>
        val productoExistente = productos.find { it.ProductoId == productoId }

        if (productoExistente != null) {
            productoExistente.Cantidad = productoExistente.Cantidad?.plus(1) // Sumamos cantidad
        }

        // Importante: Emite una copia nueva de la lista
        _productosCarrito.value = productos.toList()

        actualizarPrecioTotal(precio) // Sumamos el precio correspondiente
    }


    // Función para actualizar el precio total
    private fun actualizarPrecioTotal(cambio: Double) {
        val nuevoTotal = ((pedido.PrecioTotal ?: 0.0) + cambio).toBigDecimal()
            .setScale(2, java.math.RoundingMode.HALF_UP)
            .toDouble()

        pedido.PrecioTotal = nuevoTotal
        _precioTotal.value = nuevoTotal
    }

    // Función para obtener los detalles de los productos
    fun obtenerDatosProductos(): Flow<List<DTOProducto>> {
        return if (pedido.Productos.isNullOrEmpty()) {
            flowOf(emptyList())
        } else {
            productoModel.obtenerDetallesProductos(pedido)
        }
    }

    fun eliminarProductoDelCarrito(productoId: Int, precio: Double) {
        if (pedido.Productos.isNullOrEmpty()) return

        val productos = pedido.Productos as MutableList<DTOListaProductos>
        val productoAEliminar = productos.find { it.ProductoId == productoId }

        if (productoAEliminar != null) {
            val precioARestar =
                (productoAEliminar.Cantidad?.times(precio)) // Calcula el precio total a restar
            productos.remove(productoAEliminar) // Elimina el producto
            _productosCarrito.value = productos.toList() // Emitimos la lista actualizada
            actualizarPrecioTotal(-precioARestar!!) // Restamos el precio total correspondiente
        }
    }

    fun crearNuevoCliente(cliente: DTOCliente): Flow<Boolean?> = flow {
        clienteModel.verificarDniExistente(cliente.DNI).collect { existe ->
            if (existe) {
                emit(null) // DNI ya existe, no se puede crear el cliente
            } else {
                clienteModel.guardarCliente(cliente).collect { resultado ->
                    emit(resultado)
                    if (resultado) {
                        pedido.DNI = cliente.DNI
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                        val fechaActual = dateFormat.format(Date())

                        pedido.FechaInicio = fechaActual
                        pedido.Productos = _productosCarrito.value
                        pedidoModel.guardarPedido(pedido).collect { pedidoGuardado ->
                            if (pedidoGuardado) {
                                println("Pedido guardado exitosamente")
                                pedido.DNI = null
                                pedido.Productos = null
                                pedido.FechaInicio = null
                                pedido.PrecioTotal = null
                                _precioTotal.value = 0.00
                            } else {
                                println("Error al guardar el pedido")
                            }
                        }
                    }
                    // Emitir true si se creó correctamente, false si falló
                }
            }
        }
    }

    fun crearNuevoPedido(cliente: DTOCliente): Flow<Boolean?> = flow {
        clienteModel.verificarDniExistente(cliente.DNI).collect { dniExiste ->
            clienteModel.verificarEmailExistente(cliente.Email).collect { emailExiste ->
                if (dniExiste && emailExiste) {
                    emit(true)
                    pedido.DNI = cliente.DNI
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                    val fechaActual = dateFormat.format(Date())

                    pedido.FechaInicio = fechaActual
                    pedido.Productos = _productosCarrito.value
                    pedidoModel.guardarPedido(pedido).collect { pedidoGuardado ->
                        if (pedidoGuardado) {
                            println("Pedido guardado exitosamente")
                            pedido.DNI = null
                            pedido.Productos = null
                            pedido.FechaInicio = null
                            pedido.PrecioTotal = null
                            _precioTotal.value = 0.00
                        } else {
                            println("Error al guardar el pedido")
                        }
                    }
                } else {
                    emit(false)
                }
            }
        }
    }

    fun obtenerPedidosByDNI(dni: String): Flow<List<DTOPedidos>> {
        return if (dni.isEmpty()) {
            flowOf(emptyList()) // Si el DNI está vacío, retornamos una lista vacía
        } else {
            pedidoModel.obtenerPedidosPorDni(dni)
        }
    }

    fun obtenerProductosDelPedido(ProductoId: Int): Flow<DTOProducto> {
        return productoModel.obtenerProductoPorId(ProductoId)
    }
}