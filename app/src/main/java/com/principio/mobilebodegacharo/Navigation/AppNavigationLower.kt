package com.principio.mobilebodegacharo.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.principio.mobilebodegacharo.Controller.CarruselController
import com.principio.mobilebodegacharo.Controller.CategorialController
import com.principio.mobilebodegacharo.Controller.PedidosController
import com.principio.mobilebodegacharo.Controller.ProductoController
import com.principio.mobilebodegacharo.View.HomeScreen
import com.principio.mobilebodegacharo.View.PaginaAyuda
import com.principio.mobilebodegacharo.View.PaginaCarrito
import com.principio.mobilebodegacharo.View.PaginaCategoria
import com.principio.mobilebodegacharo.View.PaginaPedido

@Composable
fun AppNavigationLower(navcontroller: NavHostController, pedidosController: PedidosController) {
    NavHost(
        navController = navcontroller,
        startDestination = ElementsNav.Inicio.routes
    ) {
        composable(route = ElementsNav.Inicio.routes) {
            // UI Inicio
            val categoriaController = CategorialController()
            val productoController = ProductoController()
            val carruselController = CarruselController()
            HomeScreen(
                categorias = categoriaController.categorias,
                carrusel = carruselController.carrusel,
                productos = productoController.producto,
                navController = navcontroller
            ) { precio, listaProducto ->
                pedidosController.agregarPrecioTotal(precio, listaProducto)
            }
        }

        composable(route = "${ElementsNav.Categoria.routes}/{categoriaId}") { backStackEntry ->
            val categoriaId = backStackEntry.arguments?.getString("categoriaId")?.toInt() ?: 0
            val productoController = ProductoController(categoriaId)
            val categoriaController = CategorialController()
            PaginaCategoria(
                productos = productoController.producto,
                categorias = categoriaController.categorias,
                navController = navcontroller
            ) { precio, listaProducto ->
                pedidosController.agregarPrecioTotal(precio, listaProducto)
            }
        }

        composable(route = ElementsNav.Pedido.routes) {
            // UI Pedido
            PaginaPedido(
                obtenerPedidos = { dni -> pedidosController.obtenerPedidosByDNI(dni) },
                obtenerDetallePedido = { ProductoId -> pedidosController.obtenerProductosDelPedido(ProductoId) }
            )
        }

        composable(route = ElementsNav.Ayuda.routes) {
            // UI Ayuda
            PaginaAyuda()
        }

        composable(route = ElementsNav.Categoria.routes) {
            // UI Categoria
            val productoController = ProductoController()
            val categoriaController = CategorialController()
            PaginaCategoria(
                productos = productoController.producto,
                categorias = categoriaController.categorias,
                navController = navcontroller
            ) { precio, listaProducto ->
                pedidosController.agregarPrecioTotal(precio, listaProducto)
            }
        }
        composable(route = ElementsNav.Carrito.routes) {
            // Observa los flujos de datos directamente
            val productosCarrito by pedidosController.productosCarrito.collectAsState(initial = emptyList())
            val productosFlow = pedidosController.obtenerDatosProductos()
            val productos by productosFlow.collectAsState(initial = emptyList())

            // Llamada a la función de la página del carrito
            PaginaCarrito(
                pedido = pedidosController.pedido,
                productos = productos, // Lista de productos disponibles
                productosCarritoFlow = productosCarrito,
                navController = navcontroller,// Flujo del carrito

                onSumar = { precio, productoId -> pedidosController.sumarCantidadProducto(precio, productoId) },
                onRestar = { precio, productoId -> pedidosController.restarCantidadProducto(precio, productoId) },
                onDeleteProducto = { productoId, precio -> pedidosController.eliminarProductoDelCarrito(productoId, precio) },
                crearUsuario = { dtocliente -> pedidosController.crearNuevoCliente(dtocliente) },
                IniciarSesion = { dtocliente -> pedidosController.crearNuevoPedido(dtocliente) }
            )
        }
    }
}
