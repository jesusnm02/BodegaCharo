package com.principio.mobilebodegacharo.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.principio.mobilebodegacharo.ComponentUI.cargandoCirculo
import com.principio.mobilebodegacharo.DTO.DTOPedidos
import com.principio.mobilebodegacharo.DTO.DTOProducto
import com.principio.mobilebodegacharo.ui.theme.IconPurple
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@Composable
fun PaginaPedido(
    obtenerPedidos: (String) -> Flow<List<DTOPedidos>>,
    obtenerDetallePedido: (Int) -> Flow<DTOProducto>
) {
    var dni by remember { mutableStateOf("") }
    var pedidos by remember { mutableStateOf<List<DTOPedidos>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = dni,
            onValueChange = { dni = it },
            label = { Text("Ingrese el DNI") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    if (dni.isNotEmpty()) {
                        loading = true
                        obtenerPedidos(dni).collect { pedidosList ->
                            pedidos = pedidosList
                            loading = false
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Obtener Pedidos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (loading) {
            cargandoCirculo()
        } else {
            if (pedidos.isEmpty()) {
                Text("No se encontraron pedidos para este DNI.")
            } else {
                LazyColumn {
                    items(pedidos) { pedido ->
                        PedidoCard(
                            pedido = pedido,
                            obtenerDetallePedido = obtenerDetallePedido
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PedidoCard(
    pedido: DTOPedidos,
    obtenerDetallePedido: (Int) -> Flow<DTOProducto>
) {
    var showDetails by remember { mutableStateOf(false) }
    var detallesProductos by remember { mutableStateOf<List<DTOProducto>>(emptyList()) }
    var isLoadingDetalles by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(text = "Fecha de inicio: ${pedido.FechaInicio ?: "Sin fecha"}")
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "TIENES 1 PEDIDO",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Monto Total: S/ ${pedido.PrecioTotal ?: 0.0}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Ver detalles >",
            color = Color.Blue,
            modifier = Modifier.clickable {
                coroutineScope.launch {
                    isLoadingDetalles = true
                    detallesProductos = emptyList() // Reinicia los detalles de productos.

                    // Cargar los detalles de productos asociados al pedido
                    val nuevosDetalles = mutableListOf<DTOProducto>()
                    pedido.Productos?.forEach { producto ->
                        val detalle = obtenerDetallePedido(producto.ProductoId ?: 0).firstOrNull()
                        if (detalle != null) {
                            nuevosDetalles.add(detalle)
                        }
                    }

                    detallesProductos = nuevosDetalles
                    isLoadingDetalles = false
                    showDetails = true // Cambia a true para mostrar el diálogo.
                }
            }
        )
    }

    // Mostrar el diálogo solo si showDetails es true
    if (showDetails) {
        AlertDialog(
            onDismissRequest = { showDetails = false },
            title = {
                Text(
                    text = "Productos del Pedido",
                    modifier = Modifier.padding(8.dp)
                )
            },
            text = {
                if (isLoadingDetalles) {
                    cargandoCirculo() // Muestra un círculo de carga mientras se obtienen los detalles.
                } else if (detallesProductos.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(detallesProductos) { producto ->
                            // Cada producto se mostrará en una fila estilizada
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                                    .border(1.dp, IconPurple, shape = RoundedCornerShape(8.dp))
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Imagen del producto
                                Image(
                                    painter = rememberAsyncImagePainter(model = producto.url),
                                    contentDescription = "Imagen del Producto",
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.LightGray)
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                // Información del producto
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Producto: ${producto.NomProducto ?: "Desconocido"}",
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = "Cantidad: ${pedido.Productos?.find { it.ProductoId == producto.ProductoId }?.Cantidad ?: 0}")
                                    Text(text = "Precio Unitario: S/ ${producto.Precio ?: 0.0}")
                                    Text(
                                        text = "Precio Total: S/ ${
                                            pedido.Productos?.find { it.ProductoId == producto.ProductoId }?.Cantidad?.times(
                                                producto.Precio!!
                                            ) ?: 0.0
                                        }"
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        text = "No se encontraron detalles para este pedido.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            },
            confirmButton = {
                Button(onClick = { showDetails = false }) {
                    Text("Cerrar")
                }
            },
            modifier = Modifier.padding(8.dp)
        )
    }

}