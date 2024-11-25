package com.principio.mobilebodegacharo.View

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Text
import kotlinx.coroutines.flow.Flow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.principio.mobilebodegacharo.ComponentUI.IconosGenericosConTexto
import com.principio.mobilebodegacharo.ComponentUI.ListaProductos
import com.principio.mobilebodegacharo.ComponentUI.TextFieldGenerico
import com.principio.mobilebodegacharo.ComponentUI.cargandoCirculo
import com.principio.mobilebodegacharo.ComponentUI.filtradoProductos
import com.principio.mobilebodegacharo.ComponentUI.mostrarCategoriasButton
import com.principio.mobilebodegacharo.DTO.DTOCategoria
import com.principio.mobilebodegacharo.DTO.DTOListaProductos
import com.principio.mobilebodegacharo.DTO.DTOProducto
import com.principio.mobilebodegacharo.ui.theme.BorderDark
import com.principio.mobilebodegacharo.ui.theme.IconPurple

val textoInput: String = "buscar"

@Composable
fun PaginaCategoria(
    productos: Flow<List<DTOProducto>>,
    categorias: Flow<List<DTOCategoria>>,
    navController: NavHostController,
    onClickLista: (Double, DTOListaProductos) -> Unit
) {
    var productoSearch by remember { mutableStateOf("") }
    val listaProductos by productos.collectAsState(initial = emptyList())
    val listaCategorias by categorias.collectAsState(initial = emptyList())
    var productosFiltrados by remember { mutableStateOf(emptyList<DTOProducto>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, end = 20.dp, start = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top)
    ) {
        TextFieldGenerico(
            ancho = .8f,
            valor = productoSearch,
            label = textoInput.uppercase(),
            durationAnimation = 5
        ) { search ->
            productoSearch = search
            productosFiltrados = if (search.isNotEmpty()) {
                listaProductos.filter { it.NomProducto.contains(search, ignoreCase = true) }
            } else {
                emptyList()
            }
        }

        if (productoSearch.isNotEmpty() && productosFiltrados.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp)
                    .border(
                        width = 1.5.dp,
                        brush = SolidColor(BorderDark),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                items(productosFiltrados) { producto ->
                    filtradoProductos(producto = producto) { precio, listaProductos ->
                        onClickLista(precio, listaProductos)
                    }
                }
            }
        } else if (productoSearch.isNotEmpty() && productosFiltrados.isEmpty()) {
            Text(
                text = "No se encontraron productos",
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        mostrarCategoriasButton(
            listaCategorias = listaCategorias,
            navController = navController
        )

        // Encabezado de lista de productos
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Todos los productos",
                fontSize = 17.5.sp,
                textAlign = TextAlign.Start
            )

            IconosGenericosConTexto(
                label = "Ordenar",
                tamanoLabel = 15,
                icono = Icons.Default.FilterAlt,
                colorIcono = IconPurple
            ) { /* Acción del botón */ }
        }

        // Cargando o lista de productos
        if (listaProductos.isEmpty()) {
            cargandoCirculo()
        } else {
            ListaProductos(listaProductos) { precio, listaProductos ->
                onClickLista(precio, listaProductos)}
        }
    }
}

