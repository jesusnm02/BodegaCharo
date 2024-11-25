package com.principio.mobilebodegacharo.ComponentUI

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.principio.mobilebodegacharo.DTO.DTOCliente
import com.principio.mobilebodegacharo.DTO.DTOListaProductos
import com.principio.mobilebodegacharo.DTO.DTOProducto
import com.principio.mobilebodegacharo.ModelNavigation.ItemsBarLower
import com.principio.mobilebodegacharo.Navigation.ElementsNav
import com.principio.mobilebodegacharo.Navigation.RoutesActuallyNav
import com.principio.mobilebodegacharo.ui.theme.Border
import com.principio.mobilebodegacharo.ui.theme.BorderDark
import com.principio.mobilebodegacharo.ui.theme.ColorText
import com.principio.mobilebodegacharo.ui.theme.GrayLight
import com.principio.mobilebodegacharo.ui.theme.GreenPrecio
import com.principio.mobilebodegacharo.ui.theme.IconPurple
import com.principio.mobilebodegacharo.ui.theme.White
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

val menuItems = listOf(
    ItemsBarLower.ItemInicio,
    ItemsBarLower.ItemPedido,
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
fun TopBarra(
    titulo: String,
    colorBarra: Color,
    colortext: Color = ColorText,
    precioTotal: String,
    navController: NavHostController // Añadir NavHostController como parámetro
) {
    TopAppBar(
        title = { Text(text = titulo, color = colortext) },
        colors = TopAppBarDefaults.topAppBarColors(colorBarra),
        modifier = Modifier
            .border(width = 3.dp, color = Border),
        actions = {
            Text(text = "S/ ${precioTotal}", fontSize = 16.sp, color = Color.Gray)
            IconButton(
                onClick = {
                    // Navegar hacia la pantalla del pedido
                    navController.navigate(ElementsNav.Carrito.routes)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Carrito",
                    tint = Color(0xFF6A1B9A) // Color morado para el icono
                )
            }
        }
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

@Composable
fun cargandoCirculo() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp),
            strokeWidth = 2.dp,
            color = Color.Black
        )
    }
}

@Composable
fun filtradoProductos(
    producto: DTOProducto,
    onClickLista: (Double, DTOListaProductos) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = producto.NomProducto,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp
        )
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.LightGray)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = producto.url),
                contentDescription = "Imagen de ${producto.NomProducto}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            IconButton(
                onClick = {
                    val listaProducto = producto.ProductoId?.let {
                        DTOListaProductos(
                            ProductoId = it,
                            Cantidad = 1 // Cantidad inicial
                        )
                    }
                    producto.Precio?.let { precio ->
                        if (listaProducto != null) {
                            onClickLista(precio, listaProducto)
                        }
                    }
                },
                modifier = Modifier
                    .size(17.5.dp)
                    .align(Alignment.TopEnd)
                    .padding(.4.dp)
                    .background(Color(0xFF6200EE), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir al carrito",
                    tint = Color.White
                )
            }
        }
        Text(
            text = "S/ ${producto.Precio}",
            color = GreenPrecio,
            fontSize = 14.sp
        )
    }
}

@Composable
fun IniciarSesionCrearCuentaDialog(
    onDismiss: () -> Unit,
    crearUsuario: (DTOCliente) -> Flow<Boolean?>,
    IniciarSesion: (DTOCliente) -> Flow<Boolean?>
) {
    // Estados para alternar entre los diálogos
    val showIniciarSesion = remember { mutableStateOf(false) }
    val showCrearCuenta = remember { mutableStateOf(false) }

    if (!showIniciarSesion.value && !showCrearCuenta.value) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Elige una opción") },
            text = {
                Column {
                    // Opción Iniciar sesión
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showIniciarSesion.value = true } // Muestra el formulario de iniciar sesión
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Login, contentDescription = "Iniciar sesión", modifier = Modifier.padding(end = 16.dp))
                        Text(text = "Iniciar sesión")
                    }

                    // Opción Crear cuenta
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showCrearCuenta.value = true } // Muestra el formulario de crear cuenta
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.PersonAdd, contentDescription = "Crear cuenta", modifier = Modifier.padding(end = 16.dp))
                        Text(text = "Crear cuenta")
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar")
                }
            }
        )
    }

    // Mostrar el formulario de iniciar sesión
    if (showIniciarSesion.value) {
        IniciarSesionDialog(
            onDismiss = { showIniciarSesion.value = false },
            IniciarSesion = IniciarSesion
        )
    }

    // Mostrar el formulario de crear cuenta
    if (showCrearCuenta.value) {
        CrearCuentaDialog(
            onDismiss = { showCrearCuenta.value = false },
            crearUsuario = crearUsuario)
    }
}

@Composable
fun IniciarSesionDialog(
    onDismiss: () -> Unit,
    IniciarSesion: (DTOCliente) -> Flow<Boolean?>
) {
    // Estados para email y DNI
    val email = remember { mutableStateOf("") }
    val dni = remember { mutableStateOf("") }

    // Estado para manejar el resultado de la validación
    val mostrarResultado = remember { mutableStateOf<Pair<Boolean, Boolean?>?>(null) }
    val cerrarDialogos = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Primer dialogo para ingresar los datos
    if (!cerrarDialogos.value) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Iniciar sesión") },
            text = {
                Column {
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = { Text("Correo electrónico") },
                        isError = !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = dni.value,
                        onValueChange = {
                            if (it.length <= 8 && it.all { char -> char.isDigit() }) dni.value = it
                        },
                        label = { Text("DNI") },
                        isError = dni.value.length != 8,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (dni.value.length == 8 && android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
                            val cliente = DTOCliente(DNI = dni.value, Email = email.value)
                            scope.launch {
                                IniciarSesion(cliente).collect { resultado ->
                                    mostrarResultado.value = Pair(true, resultado) // Mostrar el resultado
                                }
                            }
                        }
                    },
                    enabled = dni.value.length == 8 && android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
                ) {
                    Text("Iniciar sesión")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Segundo dialogo para mostrar el resultado
    mostrarResultado.value?.let { (mostrar, resultado) ->
        if (mostrar) {
            AlertDialog(
                onDismissRequest = {
                    mostrarResultado.value = null
                    cerrarDialogos.value = true
                },
                title = { Text("Resultado") },
                text = {
                    Text(
                        when (resultado) {
                            true -> "Pedido guardado con éxito"
                            false -> "Correo o DNI incorrectos"
                            null -> "El cliente con el DNI ${dni.value} no existe"
                        }
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            mostrarResultado.value = null
                            cerrarDialogos.value = true
                        }
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }

    // Ejecutar la función `onDismiss` al cerrar completamente ambos diálogos
    if (cerrarDialogos.value) {
        LaunchedEffect(Unit) {
            onDismiss()
        }
    }
}


@Composable
fun CrearCuentaDialog(
    onDismiss: () -> Unit,
    crearUsuario: (DTOCliente) -> Flow<Boolean?>
) {
    // Estados para los datos de la cuenta
    val nombres = remember { mutableStateOf("") }
    val apellidoPaterno = remember { mutableStateOf("") }
    val dni = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }

    // Estado para manejar el resultado de la creación del usuario
    val mostrarResultado = remember { mutableStateOf<Pair<Boolean, Boolean?>?>(null) }
    val cerrarDialogos = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    if (!cerrarDialogos.value) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Crear cuenta") },
            text = {
                Column {
                    OutlinedTextField(
                        value = nombres.value,
                        onValueChange = { nombres.value = it },
                        label = { Text("Nombres") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = apellidoPaterno.value,
                        onValueChange = { apellidoPaterno.value = it },
                        label = { Text("Apellido paterno") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = dni.value,
                        onValueChange = {
                            if (it.length <= 8 && it.all { char -> char.isDigit() }) dni.value = it
                        },
                        label = { Text("DNI") },
                        isError = dni.value.length != 8,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = { Text("Correo electrónico (Gmail)") },
                        isError = !email.value.endsWith("@gmail.com") ||
                                !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = telefono.value,
                        onValueChange = {
                            if (it.length <= 9 && it.all { char -> char.isDigit() }) telefono.value = it
                        },
                        label = { Text("Teléfono") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (dni.value.length == 8 &&
                            email.value.endsWith("@gmail.com") &&
                            android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
                        ) {
                            val cliente = DTOCliente(
                                ApePa = apellidoPaterno.value,
                                DNI = dni.value,
                                Email = email.value,
                                Nombres = nombres.value,
                                telefono = telefono.value
                            )
                            scope.launch {
                                crearUsuario(cliente).collect { resultado ->
                                    mostrarResultado.value = Pair(true, resultado) // Mostrar resultado
                                }
                            }
                        }
                    },
                    enabled = dni.value.length == 8 &&
                            email.value.endsWith("@gmail.com") &&
                            android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
                ) {
                    Text("Crear cuenta")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Mostrar la ventana emergente del resultado
    mostrarResultado.value?.let { (mostrar, resultado) ->
        if (mostrar) {
            AlertDialog(
                onDismissRequest = {
                    mostrarResultado.value = null
                    cerrarDialogos.value = true
                },
                title = { Text("Resultado") },
                text = {
                    Text(
                        when (resultado) {
                            true -> "Cuenta creada con éxito"
                            false -> "Ocurrió un error al crear la cuenta"
                            null -> "El usuario con el DNI ${dni.value} ya existe"
                        }
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            mostrarResultado.value = null
                            cerrarDialogos.value = true
                        }
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }

    // Ejecutar la función `onDismiss` al cerrar completamente ambos diálogos
    if (cerrarDialogos.value) {
        LaunchedEffect(Unit) {
            onDismiss()
        }
    }
}