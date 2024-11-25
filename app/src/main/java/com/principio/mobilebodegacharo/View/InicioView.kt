package com.principio.mobilebodegacharo.View

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.principio.mobilebodegacharo.ComponentUI.Carousel
import com.principio.mobilebodegacharo.ComponentUI.CategoriesSection
import com.principio.mobilebodegacharo.ComponentUI.TextFieldGenerico
import com.principio.mobilebodegacharo.ComponentUI.filtradoProductos
import com.principio.mobilebodegacharo.DTO.DTOCategoria
import kotlinx.coroutines.flow.Flow
import com.principio.mobilebodegacharo.DTO.DTOCarrusel
import com.principio.mobilebodegacharo.DTO.DTOListaProductos
import com.principio.mobilebodegacharo.DTO.DTOProducto
import com.principio.mobilebodegacharo.Navigation.ElementsNav
import com.principio.mobilebodegacharo.ui.theme.BorderDark

@Composable
fun HomeScreen(
    categorias: Flow<List<DTOCategoria>>,
    carrusel: Flow<List<DTOCarrusel>>,
    productos: Flow<List<DTOProducto>>,
    navController: NavHostController,
    onClickLista: (Double, DTOListaProductos) -> Unit
) {
    var productoSearch by remember { mutableStateOf("") }
    val listaCategorias by categorias.collectAsState(initial = emptyList())
    val listaCarrusel by carrusel.collectAsState(initial = emptyList())
    val listaProductos by productos.collectAsState(initial = emptyList())
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
        Carousel(listaCarrusel)
        CategoriesSection(listaCategorias) {categoriaId ->
            navController.navigate("${ElementsNav.Categoria.routes}/$categoriaId")
        }
    }
}