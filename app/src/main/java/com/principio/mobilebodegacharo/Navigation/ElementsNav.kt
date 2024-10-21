package com.principio.mobilebodegacharo.Navigation

import com.principio.mobilebodegacharo.ModelNavigation.RoutesNav

sealed class ElementsNav(
    var routes: String
) {
    object Inicio: ElementsNav(RoutesNav.Inicio.name)
    object Pedido: ElementsNav(RoutesNav.Pedido.name)
    object Pago: ElementsNav(RoutesNav.Pago.name)
    object Ayuda: ElementsNav(RoutesNav.Ayuda.name)
    object Categoria: ElementsNav(RoutesNav.Categoria.name)
}