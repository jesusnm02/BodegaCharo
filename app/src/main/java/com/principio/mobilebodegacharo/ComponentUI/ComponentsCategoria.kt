package com.principio.mobilebodegacharo.ComponentUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.principio.mobilebodegacharo.DTO.DTOCategoria
import com.principio.mobilebodegacharo.DTO.DTOListaProductos
import com.principio.mobilebodegacharo.DTO.DTOProducto
import com.principio.mobilebodegacharo.Navigation.ElementsNav

@Composable
fun ListaProductos(
    productos: List<DTOProducto>,
    onClickLista: (Double, DTOListaProductos) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Dos columnas
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(productos) { producto ->
            ItemProducto(producto) {precio, listaProductos ->
                onClickLista(precio, listaProductos)}
        }
    }
}

@Composable
fun ItemProducto(producto: DTOProducto, onClickLista: (Double, DTOListaProductos) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Contenedor de la imagen y el ícono
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
        ) {
            // Imagen del producto
            Image(
                painter = rememberAsyncImagePainter(model = producto.url),
                contentDescription = "Imagen de ${producto.NomProducto}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Ícono de sumar encima de la imagen
            IconButton(
                onClick = {
                    val listaProducto = producto.ProductoId?.let {
                        DTOListaProductos(
                            ProductoId = it,
                            Cantidad = 1 // Cantidad inicial
                        )
                    }
                    producto.Precio?.let { precio ->
                        if (listaProducto != null) {
                            onClickLista(precio, listaProducto)
                        }
                    }
                },
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.TopEnd) // Alinear en la esquina superior derecha
                    .padding(4.dp)
                    .background(Color(0xFF6200EE), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir al carrito",
                    tint = Color.White
                )
            }
        }

        // Nombre del producto
        Text(
            text = producto.NomProducto,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Precio del producto
        Text(
            text = "S/ ${producto.Precio}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        // Descripción con ícono
        IconosGenericosConTexto(
            label = producto.Descripcion ?: "Sin descripción",
            tamanoLabel = 12,
            icono = Icons.Default.Warning,
            colorIcono = Color.Red
        ) {
            // Acción al hacer clic en el ícono de advertencia
        }
    }
}



@Composable
fun mostrarCategoriasButton(
    listaCategorias: List<DTOCategoria>,
    navController: NavHostController
) {
    if(listaCategorias.isEmpty()) {
        cargandoCirculo()
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentPadding = PaddingValues(2.5.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(listaCategorias) { categoria ->
                Button(
                    onClick = {
                        navController.navigate("${ElementsNav.Categoria.routes}/${categoria.CategoriaId}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = categoria.NomCategoria,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

/*@Composable
fun ListaProductos(
    productos: List<DTOProducto>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        ItemProducto()
    }
}

@Composable
fun ItemProducto(producto: DTOProducto) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        /*Image(
            painter = rememberImagePainter(data = "https://mir-s3-cdn-cf.behance.net/project_modules/disp/5c0dff12471165.5626a15eabb86.jpg",),
            contentDescription = "Letras",
            modifier = Modifier
                .fillMaxSize()
        )*/
        Text(text = "Nombre de Producto")
        Text(text = "Precio")
        IconosGenericosConTexto(
            label = "100 por cada bolsa",
            tamanoLabel = 12,
            icono = Icons.Default.Warning,
            colorIcono = IconRed
        ) {

        }
    }
}*/