package com.skdassoc.tsumegolet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skdassoc.tsumegolet.model.KifuData
import com.skdassoc.tsumegolet.model.transformKifu
import com.skdassoc.tsumegolet.scene.EditScene
import com.skdassoc.tsumegolet.scene.ListScene
import com.skdassoc.tsumegolet.scene.QuestionScene
import com.skdassoc.tsumegolet.scene.QuizSettingScene
import com.skdassoc.tsumegolet.storage.loadKifuList
import com.skdassoc.tsumegolet.storage.saveKifuList

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
private fun NotFound() =
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Not Found")
    }

@Composable
private fun Content() {
    val context = LocalContext.current
    var kifuList by remember { mutableStateOf(loadKifuList(context)) }
    var quizOrder by remember { mutableStateOf<List<Int>>(emptyList()) }
    var quizRotations by remember { mutableStateOf<List<Int>>(emptyList()) }
    var quizSwaps by remember { mutableStateOf<List<Boolean>>(emptyList()) }
    val navController = rememberNavController()

    fun updateKifuList(newList: List<KifuData>) {
        kifuList = newList
        saveKifuList(context, newList)
    }

    Surface(
        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars),
        color = Color.White,
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Button(onClick = { navController.navigate("list") }) { Text("一覧") }
                        Button(
                            onClick = {
                                quizOrder = kifuList.indices.shuffled()
                                navController.navigate("quiz-setting")
                            }
                        ) {
                            Text("問題")
                        }
                    }
                }
            }
            composable("list") {
                ListScene(
                    kifuList,
                    onSelect = { index -> navController.navigate("question/$index") },
                    onAdd = { navController.navigate("edit/new") },
                )
            }
            composable("quiz-setting") {
                QuizSettingScene(
                    onDecided = { rotation, swap ->
                        quizRotations =
                            if (rotation) List(kifuList.size) { (0..3).random() }
                            else List(kifuList.size) { 0 }
                        quizSwaps =
                            if (swap) List(kifuList.size) { listOf(true, false).random() }
                            else List(kifuList.size) { false }
                        navController.navigate("quiz/0")
                    }
                )
            }
            composable("question/{index}") { backStackEntry ->
                val index = backStackEntry.arguments?.getString("index")?.toIntOrNull()
                val kifu = index?.let { kifuList.getOrNull(it) }
                if (kifu != null) {
                    QuestionScene(
                        kifu,
                        onEdit = { navController.navigate("edit/$index") },
                        onDelete = {
                            updateKifuList(kifuList.toMutableList().also { it.removeAt(index!!) })
                            navController.popBackStack()
                        },
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Not Found")
                    }
                }
            }
            composable("quiz/{position}") { backStackEntry ->
                val position =
                    backStackEntry.arguments?.getString("position")?.toIntOrNull() ?: Int.MAX_VALUE
                // 終了
                if (position >= quizOrder.size || position < 0) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            Button(
                                onClick = { navController.popBackStack("home", inclusive = false) }
                            ) {
                                Text("終了")
                            }
                        }
                    }
                }
                // 問題
                else {
                    val kifu =
                        kifuList.getOrNull(quizOrder[position])?.let {
                            transformKifu(
                                it,
                                quizRotations.getOrElse(position) { 0 },
                                quizSwaps.getOrElse(position) { false },
                            )
                        }
                    if (kifu != null) {
                        QuestionScene(
                            kifu,
                            onNext = { navController.navigate("quiz/${position + 1}") },
                            onFinish = { navController.popBackStack("home", inclusive = false) },
                        )
                    } else {
                        NotFound()
                    }
                }
            }
            composable("edit/new") {
                EditScene(KifuData(title = "")) { updated ->
                    updateKifuList(kifuList + updated)
                    navController.popBackStack()
                }
            }
            composable("edit/{index}") { backStackEntry ->
                val index = backStackEntry.arguments?.getString("index")?.toIntOrNull()
                val kifu = index?.let { kifuList.getOrNull(it) }
                if (kifu != null) {
                    EditScene(kifu) { updated ->
                        updateKifuList(kifuList.toMutableList().also { it[index!!] = updated })
                        navController.popBackStack()
                    }
                } else {
                    NotFound()
                }
            }
        }
    }
}
