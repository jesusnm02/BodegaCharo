package com.principio.mobilebodegacharo.ComponentUI

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.principio.mobilebodegacharo.DTO.DTOCarrusel
import com.principio.mobilebodegacharo.DTO.DTOCategoria
import com.principio.mobilebodegacharo.View.textoInput
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(text = "Menu")
        },
        actions = {
            Text(text = "S/ 0.00", fontSize = 16.sp, color = Color.Gray)
            IconButton(onClick = { /* Acción del carrito */ }) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Carrito",
                    tint = Color(0xFF6A1B9A) // Color morado para el icono
                )
            }
        },
    )
}

@Composable
fun SearchBar() {
    var productoSearch: String by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TextFieldGenerico(
            ancho = 1f,
            valor = "",
            label = textoInput.uppercase(),
            durationAnimation = 5
        ) {
            productoSearch = it
        }
    }
}


@Composable
fun Carousel(imagenesCarrusel: List<DTOCarrusel>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(8.dp))
        // Carrusel de imágenes
        if(imagenesCarrusel.isEmpty()) {
            cargandoCirculo()
        } else {
            val listState = rememberLazyListState()
            val itemCount = imagenesCarrusel.size // Basado en el tamaño de la lista proporcionada

            // Desplazamiento automático
            LaunchedEffect(Unit) {
                while (true) {
                    yield() // Permite que otros procesos se ejecuten
                    delay(2000) // Cambia de ítem cada 2 segundos
                    val targetIndex = (listState.firstVisibleItemIndex + 1) % itemCount
                    listState.animateScrollToItem(targetIndex)
                }
            }

            LazyRow(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(itemCount) { index ->
                    val imagePainter: Painter = rememberAsyncImagePainter(
                        model = imagenesCarrusel[index].url // URL de la imagen
                    )
                    Image(
                        painter = imagePainter,
                        contentDescription = "",
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoriesSection(
    categorias: List<DTOCategoria>,
    onCategoryClick: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Nuestras Categorías",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (categorias.isEmpty()) {
            cargandoCirculo()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categorias.chunked(3)) { rowCategories -> // Agrupa las categorías en filas de 3
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        rowCategories.forEach { category ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f)
                            ) {
                                val imagePainter: Painter = rememberAsyncImagePainter(
                                    model = category.url
                                )

                                Box(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clickable {
                                            category.CategoriaId?.let { onCategoryClick(it) }
                                        }
                                ) {
                                    Image(
                                        painter = imagePainter,
                                        contentDescription = category.NomCategoria,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Text(
                                    text = category.NomCategoria,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                        // Espaciado si hay menos de 3 elementos en la fila
                        repeat(3 - rowCategories.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}