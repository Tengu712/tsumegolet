package com.skdassoc.tsumegolet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skdassoc.tsumegolet.scene.ListScene

class MainActivity : ComponentActivity() {
    private val AppColorScheme =
        lightColorScheme(
            primary = Color.Black,
            onPrimary = Color.White,
            background = Color.White,
            onBackground = Color.Black,
            surface = Color.White,
            onSurface = Color.Black,
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MaterialTheme(colorScheme = AppColorScheme) { Content() } }
    }
}

@Composable
private fun Content() {
    // TODO: 記録されたデータを参照する
    val mockKifuList = List(20) { i -> KifuData("詰め碁 ${i + 1}") }
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars),
        color = Color.White,
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Button(onClick = { navController.navigate("list") }) { Text("一覧") }
                }
            }
            composable("list") { ListScene(mockKifuList) }
        }
    }
}
