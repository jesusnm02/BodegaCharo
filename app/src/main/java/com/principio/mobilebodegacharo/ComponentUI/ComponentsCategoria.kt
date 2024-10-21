package com.principio.mobilebodegacharo.ComponentUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.principio.mobilebodegacharo.ui.theme.IconRed

@Composable
fun ItemProducto() {
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
}

@Composable
fun ListaProductos() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        ItemProducto()
        ItemProducto()
    }
}