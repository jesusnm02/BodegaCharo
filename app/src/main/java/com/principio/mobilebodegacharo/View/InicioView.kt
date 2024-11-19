package com.principio.mobilebodegacharo.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.principio.mobilebodegacharo.ComponentUI.Carousel
import com.principio.mobilebodegacharo.ComponentUI.CategoriesSection
import com.principio.mobilebodegacharo.ComponentUI.SearchBar
import com.principio.mobilebodegacharo.ComponentUI.TopBar
import com.principio.mobilebodegacharo.DTO.DTOCategoria
import kotlinx.coroutines.flow.Flow
import com.principio.mobilebodegacharo.DTO.DTOCarrusel

@Composable
fun HomeScreen(
    categorias: Flow<List<DTOCategoria>>,
    carrusel: Flow<List<DTOCarrusel>>
) {
    val listaCategorias by categorias.collectAsState(initial = emptyList())
    val listaCarrusel by carrusel.collectAsState(initial = emptyList())
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar()
        SearchBar()
        Carousel(listaCarrusel)
        CategoriesSection(listaCategorias) {

        }
    }
}