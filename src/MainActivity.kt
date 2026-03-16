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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skdassoc.tsumegolet.model.KifuData
import com.skdassoc.tsumegolet.scene.EditScene
import com.skdassoc.tsumegolet.scene.ListScene
import com.skdassoc.tsumegolet.scene.QuestionScene

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MaterialTheme(colorScheme = appColorScheme) { Content() } }
    }
}

private val appColorScheme =
    lightColorScheme(
        primary = Color.Black,
        onPrimary = Color.White,
        primaryContainer = Color.Black,
        onPrimaryContainer = Color.White,
        background = Color.White,
        onBackground = Color.Black,
        surface = Color.White,
        onSurface = Color.Black,
    )

@Composable
private fun Content() {
    // TODO: 記録されたデータを参照する
    var kifuList by remember { mutableStateOf(emptyList<KifuData>()) }
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
            composable("list") {
                ListScene(
                    kifuList,
                    onSelect = { index -> navController.navigate("question/$index") },
                    onAdd = { navController.navigate("edit/new") },
                )
            }
            composable("question/{index}") { backStackEntry ->
                val index = backStackEntry.arguments?.getString("index")?.toIntOrNull()
                val kifu = index?.let { kifuList.getOrNull(it) }
                if (kifu != null) {
                    QuestionScene(kifu, onEdit = { navController.navigate("edit/$index") })
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Not Found")
                    }
                }
            }
            composable("edit/new") {
                EditScene(KifuData(title = "")) { updated ->
                    kifuList = kifuList.toMutableList().also { it.add(updated) }
                    navController.popBackStack()
                }
            }
            composable("edit/{index}") { backStackEntry ->
                val index = backStackEntry.arguments?.getString("index")?.toIntOrNull()
                val kifu = index?.let { kifuList.getOrNull(it) }
                if (kifu != null) {
                    EditScene(kifu) { updated ->
                        kifuList = kifuList.toMutableList().also { it[index!!] = updated }
                        navController.popBackStack()
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Not Found")
                    }
                }
            }
        }
    }
}
