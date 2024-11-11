package com.principio.mobilebodegacharo.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.principio.mobilebodegacharo.ComponentUI.BotonesGenericos
import com.principio.mobilebodegacharo.ComponentUI.IconosGenericosConTexto
import com.principio.mobilebodegacharo.ComponentUI.ListaProductos
import com.principio.mobilebodegacharo.ComponentUI.TextFieldGenerico
import com.principio.mobilebodegacharo.ui.theme.Border
import com.principio.mobilebodegacharo.ui.theme.IconPurple
import com.principio.mobilebodegacharo.ui.theme.White

val textoInput: String = "buscar"

@Composable
fun PaginaCategoria() {
    var productoSearch by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, end = 20.dp, start = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 20.dp, alignment = Alignment.Top)
    ) {
        TextFieldGenerico(
            valor = productoSearch,
            label = textoInput.uppercase(),
            durationAnimation = 5
        ) {
            productoSearch = it
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(space = 7.dp, alignment = Alignment.CenterHorizontally)
        ) {
            BotonesGenericos(
                label = "Ver todo",
                colortext = Color.White,
                colorFondo = IconPurple,
                redondeadoDeBordes = 20.dp
            ) {

            }
            BotonesGenericos(
                label = "Golosinas",
                colorDeBordes = Border,
                colorFondo = White,
                redondeadoDeBordes = 20.dp
            ) {

            }
            BotonesGenericos(
                label = "Caramelos",
                colorDeBordes = Border,
                colorFondo = White,
                redondeadoDeBordes = 20.dp
            ) {

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Todos los productos",
                fontSize = 17.5.sp,
                textAlign = TextAlign.Start)

            IconosGenericosConTexto(
                label = "Ordenar",
                tamanoLabel = 15,
                icono = Icons.Default.FilterAlt,
                colorIcono = IconPurple
            ) {

            }
        }
        ListaProductos()
    }
}

@Preview(showSystemUi = true)
@Composable
fun sho5() {
    PaginaCategoria()
}