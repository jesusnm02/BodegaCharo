package com.principio.mobilebodegacharo.ModelNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardTravel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Payment
import androidx.compose.ui.graphics.vector.ImageVector
import com.principio.mobilebodegacharo.Navigation.ElementsNav

sealed class ItemsBarLower(
    val icon: ImageVector,
    val title: String,
    val route: String
) {
    object ItemInicio: ItemsBarLower(
        Icons.Default.Home,
        "Inicio", ElementsNav.Inicio.routes)

    object ItemPedido: ItemsBarLower(
        Icons.Default.CardTravel,
        "Pedidos", ElementsNav.Pedido.routes)

    object ItemAyuda: ItemsBarLower(
        Icons.Default.Info,
        "Ayuda", ElementsNav.Ayuda.routes)

    object ItemCategorias: ItemsBarLower(
        Icons.Default.CardTravel,
        "Categoria", ElementsNav.Categoria.routes)
}