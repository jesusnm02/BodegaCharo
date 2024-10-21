package com.principio.mobilebodegacharo.ComponentUI

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.principio.mobilebodegacharo.ui.theme.Border
import com.principio.mobilebodegacharo.ui.theme.IconPurple

@Composable
fun mostrarOpciones(textopagina: String) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp, alignment = Alignment.Top)
    ) {
        Text(text = textopagina,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .border(width = 2.dp, color = Border)
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp)
        )
        ItemContacto(
            onClick = { /*TODO*/ },
            icono = Icons.Default.Phone,
            subTitulo = "Llamanos",
            informacion = "+51 940 157 785"
        )
        ItemContacto(
            onClick = { /*TODO*/ },
            icono = Icons.Default.Email,
            subTitulo = "Correo Electronico",
            informacion = "bodegacharo@gmail.com"
        )
        ItemContacto(
            onClick = { /*TODO*/ },
            icono = Icons.Default.Warning,
            subTitulo = "Preguntas Frecuentes",
            informacion = "Tenemos las respuestas a tus dudas"
        )
    }
}

@Composable
fun ColumnScope.ItemContacto(
    onClick:() -> Unit,
    icono: ImageVector,
    subTitulo: String,
    informacion: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 30.dp, alignment = Alignment.Start),
        modifier = Modifier
            .background(Border, shape = RoundedCornerShape(16.dp))
            .border(width = 3.dp, color = Border, shape = RoundedCornerShape(16.dp))
            .padding(10.dp, 25.dp)
            .fillMaxWidth(.8f)
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            IconosGenericos(
                onClick = onClick,
                icono = icono,
                tint = IconPurple
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = subTitulo,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp)
            Text(text = informacion,
                fontSize = 18.sp)
        }
    }
}
