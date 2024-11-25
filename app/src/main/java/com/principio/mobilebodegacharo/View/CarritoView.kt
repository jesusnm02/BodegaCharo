package com.principio.mobilebodegacharo.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.principio.mobilebodegacharo.ComponentUI.IniciarSesionCrearCuentaDialog
import com.principio.mobilebodegacharo.DTO.DTOCliente
import com.principio.mobilebodegacharo.DTO.DTOListaProductos
import com.principio.mobilebodegacharo.DTO.DTOPedidos
import com.principio.mobilebodegacharo.DTO.DTOProducto
import com.principio.mobilebodegacharo.Navigation.ElementsNav
import kotlinx.coroutines.flow.Flow

@Composable
fun PaginaCarrito(
    pedido: DTOPedidos,
    productos: List<DTOProducto>,
    productosCarritoFlow: List<DTOListaProductos>,
    navController: NavHostController,
    onSumar: (Double, Int) -> Unit,
    onRestar: (Double, Int) -> Unit,
    onDeleteProducto: (Int, Double) -> Unit,
    crearUsuario: (DTOCliente) -> Flow<Boolean?>,
    IniciarSesion: (DTOCliente) -> Flow<Boolean?>
) {
    val showDialog = remember { mutableStateOf(false) }

    if (productosCarritoFlow.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No hay pedidos",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp) // Espaciado entre los elementos
        ) {
            Text(
                text = "Lista de Productos",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f) // Esto permitirá que los otros elementos tengan espacio
            ) {
                items(productosCarritoFlow) { itemPedido ->
                    val producto = productos.find { it.ProductoId == itemPedido.ProductoId }
                    if (producto != null) {
                        itemPedido.Cantidad?.let {
                            ProductoCarritoItem(
                                producto = producto,
                                cantidad = it,
                                navController = navController,
                                onSumar = { itemPedido.ProductoId?.let { it1 ->
                                    onSumar(producto.Precio ?: 0.0,
                                        it1
                                    )
                                } },
                                onRestar = { itemPedido.ProductoId?.let { it1 ->
                                    onRestar(producto.Precio ?: 0.0,
                                        it1
                                    )
                                } },
                                onDeleteProducto = { producto.ProductoId?.let { onDeleteProducto(it, producto.Precio ?: 0.0) } }
                            )
                        }
                    }
                }
            }

            // Mostrar el precio total
            Text(
                text = "Precio Total: S/ ${pedido.PrecioTotal?.let { "%.2f".format(it) } ?: "0.00"}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )

            // Botón para navegar
            Button(
                onClick = { showDialog.value = true },  // Mostrar el diálogo al hacer click
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(
                    text = "CONTINUAR",
                    color = Color.White
                )
            }
        }

        // Mostrar el diálogo si showDialog es verdadero
        if (showDialog.value) {
            IniciarSesionCrearCuentaDialog(
                onDismiss = { showDialog.value = false },
                crearUsuario = crearUsuario,
                IniciarSesion = IniciarSesion
            )
        }
    }
}

@Composable
fun ProductoCarritoItem(
    producto: DTOProducto,
    cantidad: Int,
    navController: NavHostController,
    onSumar: () -> Unit,
    onRestar: () -> Unit,
    onDeleteProducto: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Mostrar imagen del producto
        Image(
            painter = rememberAsyncImagePainter(producto.url),
            contentDescription = "Imagen de ${producto.NomProducto}",
            modifier = Modifier
                .size(64.dp)
                .padding(end = 16.dp),
            contentScale = ContentScale.Crop
        )

        // Detalles del producto
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = producto.NomProducto,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Text(
                text = "Cantidad: $cantidad",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = "Precio Unitario: S/ ${producto.Precio?.let { "%.2f".format(it) }}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = "Precio Total: S/ ${"%.2f".format((producto.Precio ?: 0.0) * cantidad)}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        // Botones para sumar y restar
        Row {
            IconButton(onClick = {
                onRestar()
                navController.navigate(ElementsNav.Carrito.routes)
            }) {
                Icon(Icons.Default.Remove, contentDescription = "Restar")
            }
            IconButton(onClick = {
                onSumar()
                navController.navigate(ElementsNav.Carrito.routes)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Sumar")
            }
            IconButton(onClick = { onDeleteProducto() }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}