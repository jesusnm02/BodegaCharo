package com.principio.mobilebodegacharo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.principio.mobilebodegacharo.View.Welcome
import com.principio.mobilebodegacharo.ui.theme.MobileBodegaCharoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileBodegaCharoTheme {
                Welcome()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    MobileBodegaCharoTheme {
        Welcome()
    }
}