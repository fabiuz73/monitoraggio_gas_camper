package com.example.rilevatoregas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.example.rilevatoregas.ui.theme.RilevatoreGasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RilevatoreGasTheme {
                // Usare una superficie Material Design
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Contenuto principale dell'app
                    Greeting("Rilevatore Gas")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Ciao $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RilevatoreGasTheme {
        Greeting("Anteprima")
    }
}