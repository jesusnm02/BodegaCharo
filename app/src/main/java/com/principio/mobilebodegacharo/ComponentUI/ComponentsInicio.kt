package com.principio.mobilebodegacharo.ComponentUI

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar()
        SearchBar()
        Carousel()
        CategoriesSection()
    }
}

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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFEFEFEF), shape = MaterialTheme.shapes.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = "",
            onValueChange = { /* Acción de búsqueda */ },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            singleLine = true,
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp)
        )
        IconButton(onClick = { /* Acción de búsqueda */ }) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar"
            )
        }
    }
}

@Composable
fun Carousel() {
    val listState = rememberLazyListState()
    val itemCount = 5 // Número de elementos en el carrusel

    // Desplazamiento automático
    LaunchedEffect(Unit) {
        while (true) {
            yield() // Permite que otros procesos se ejecuten
            delay(2000) // Cambia de ítem cada 2 segundos
            val targetIndex = (listState.firstVisibleItemIndex + 1) % itemCount
            listState.animateScrollToItem(targetIndex)
        }
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(8.dp))
        // Lista con íconos
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(itemCount) { index ->
                Icon(
                    imageVector = Icons.Default.Category,
                    contentDescription = "Promoción $index",
                    modifier = Modifier.size(60.dp),
                    tint = Color(0xFF6A1B9A) // Color morado
                )
            }
        }
    }
}

@Composable
fun CategoriesSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Nuevas Categorías", fontSize = 20.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))

        // Disposición de las categorías en filas de 3 elementos
        val categories = listOf("Abarrotes", "Desayuno", "Repostería", "Golosinas", "Limpieza", "Cuidado")

        Box(modifier = Modifier.fillMaxWidth()) {
            Column {
                categories.chunked(3).forEach { rowCategories ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        rowCategories.forEach { category ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Category,
                                    contentDescription = category,
                                    modifier = Modifier.size(60.dp),
                                    tint = Color(0xFF6A1B9A) // Color morado
                                )
                                Text(text = category, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun show() {
    HomeScreen()
}