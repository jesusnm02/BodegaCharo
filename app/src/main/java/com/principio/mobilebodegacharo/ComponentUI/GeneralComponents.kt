package com.principio.mobilebodegacharo.ComponentUI

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.principio.mobilebodegacharo.ModelNavigation.ItemsBarLower
import com.principio.mobilebodegacharo.Navigation.RoutesActuallyNav
import com.principio.mobilebodegacharo.ui.theme.Border
import com.principio.mobilebodegacharo.ui.theme.BorderDark
import com.principio.mobilebodegacharo.ui.theme.ColorText
import com.principio.mobilebodegacharo.ui.theme.GrayLight
import com.principio.mobilebodegacharo.ui.theme.IconPurple
import com.principio.mobilebodegacharo.ui.theme.White

val menuItems = listOf(
    ItemsBarLower.ItemInicio,
    ItemsBarLower.ItemPedido,
    ItemsBarLower.ItemPago,
    ItemsBarLower.ItemAyuda,
    ItemsBarLower.ItemCategorias)

@Composable
fun NavigationLower(navcontroller: NavHostController) {
    BottomAppBar(
        modifier = Modifier
            .background(color = White)
            .border(3.dp, color = Border)
    ) {
        NavigationBar {
            menuItems.forEach { element ->
                val routerActually = RoutesActuallyNav(navcontroller = navcontroller) == element.route
                NavigationBarItem(selected = routerActually,
                    onClick = { navcontroller.navigate(element.route) },
                    icon = { Icon(imageVector = element.icon,
                        contentDescription = null,
                        tint = IconPurple) },
                    label = { Text(text = element.title) })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarra(titulo: String, colorBarra: Color, colortext: Color = ColorText) {
    TopAppBar(
        title = { Text(text = titulo,
            color = colortext) },
        colors = TopAppBarDefaults.topAppBarColors(colorBarra),
        modifier = Modifier
            .border(width = 3.dp, color = Border)
    )
}

@Composable
fun IconosGenericos(onClick:() -> Unit, icono: ImageVector, descripcion: String = "", tint: Color) {
    IconButton(onClick = onClick) {
        Icon(imageVector = icono, contentDescription = descripcion,
            tint = tint, modifier = Modifier
                .size(50.dp))
    }
}

@Composable
fun IconosGenericosConTexto(
    label: String,
    tamanoLabel: Int,
    colorLetra:Color = Color.Black,
    colorFondo: Color = White,
    colorBorde: Color = White,
    redondeadoDeBordes: Dp = 0.dp,
    icono: ImageVector,
    colorIcono: Color,
    onClick: () -> Unit
) {
    Surface(
        border = BorderStroke(width = redondeadoDeBordes, color = colorBorde),
        color = colorFondo,
        onClick = onClick
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icono , contentDescription = "",
                tint = colorIcono)
            Text(text = label,
                fontSize = tamanoLabel.sp,
                color = colorLetra,
                textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun BotonesGenericos(
    label: String,
    colortext: Color = Color.Black,
    tamanoLabel: Int = 15,
    colorFondo: Color = White,
    redondeadoDeBordes: Dp = 0.dp,
    colorDeBordes: Color = White,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .background(color = colorFondo, shape = RoundedCornerShape(redondeadoDeBordes))
            .border(
                width = 2.dp,
                color = colorDeBordes,
                shape = RoundedCornerShape(redondeadoDeBordes)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorFondo
        ),
        onClick = onClick
    ) {
        Text(text = label,
            fontSize = tamanoLabel.sp,
            color = colortext)
    }
}

@Composable
fun TextFieldGenerico(
    ancho: Float,
    valor: String,
    label: String,
    durationAnimation: Int,
    onClickEscribir: (String) -> Unit
    ) {
    var recargar by remember { mutableStateOf(false) }
    TextField(
        modifier = Modifier
            .border(width = 1.5.dp, color = BorderDark, shape = RoundedCornerShape(10.dp))
            .background(color = GrayLight)
            .fillMaxWidth(ancho)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = durationAnimation,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = White,    // Color de fondo cuando está enfocado
            unfocusedContainerColor = GrayLight, // Color de fondo cuando no está enfocado
            //disabledContainerColor = Color.Gray,     // Color de fondo cuando está deshabilitado
            errorContainerColor = Color.Red,       // Color de fondo cuando hay un error

            //Esta es para deshabilitar las lineas de abajo
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        //FUNCIONALIDADES----------------
        value = valor,
        onValueChange = onClickEscribir,
        label = { Text(text = label, color = Color.Black) },
        singleLine = true,
        trailingIcon = {
            if(recargar) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp),
                    strokeWidth = 2.dp,
                    color = Color.Black
                )
            } else {
                IconosGenericos(
                    onClick = { recargar = !recargar },
                    icono = Icons.Default.Search,
                    tint = ColorText
                )
            }
        },
        keyboardActions = KeyboardActions(
            //Realizara una funcion cuando el usuario
            //de click en buscar, en el teclado de su movil
            onSearch = {

            }
        )
    )
}