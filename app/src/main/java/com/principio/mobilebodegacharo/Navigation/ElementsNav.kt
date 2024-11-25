package com.principio.mobilebodegacharo.Navigation

import com.principio.mobilebodegacharo.ModelNavigation.RoutesNav

sealed class ElementsNav(
    var routes: String
) {
    object Inicio: ElementsNav(RoutesNav.Inicio.name)
    object Pedido: ElementsNav(RoutesNav.Pedido.name)
    object Ayuda: ElementsNav(RoutesNav.Ayuda.name)
    object Categoria: ElementsNav(RoutesNav.Categoria.name)
    object Carrito: ElementsNav(RoutesNav.Carrito.name)
}