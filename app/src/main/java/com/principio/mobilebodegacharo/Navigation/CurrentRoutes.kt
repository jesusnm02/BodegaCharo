package com.principio.mobilebodegacharo.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun RoutesActuallyNav(navcontroller: NavHostController): String? =
    navcontroller.currentBackStackEntryAsState()
        .value?.destination?.route