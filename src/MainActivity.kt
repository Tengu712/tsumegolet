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
import com.skdassoc.tsumegolet.model.BoardSize
import com.skdassoc.tsumegolet.model.KifuData
import com.skdassoc.tsumegolet.model.Stone
import com.skdassoc.tsumegolet.model.StoneColor
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
        background = Color.White,
        onBackground = Color.Black,
        surface = Color.White,
        onSurface = Color.Black,
    )

@Composable
private fun Content() {
    // TODO: 記録されたデータを参照する
    val mockKifuList =
        listOf(
            KifuData(
                "詰め碁 1",
                boardSize = BoardSize.Nineteen,
                offsetCol = 0,
                offsetRow = 0,
                extentCols = 7,
                extentRows = 7,
                stones =
                    listOf(
                        Stone(0, 6, StoneColor.Black),
                        Stone(1, 6, StoneColor.Black),
                        Stone(2, 6, StoneColor.Black),
                        Stone(0, 5, StoneColor.White),
                        Stone(1, 5, StoneColor.White),
                        Stone(2, 5, StoneColor.Black),
                        Stone(0, 4, StoneColor.White),
                        Stone(1, 4, StoneColor.Black),
                    ),
                answerCol = 0,
                answerRow = 3,
                answerTurn = StoneColor.Black,
            ),
            KifuData(
                "詰め碁 2",
                boardSize = BoardSize.Nineteen,
                offsetCol = 12,
                offsetRow = 12,
                extentCols = 7,
                extentRows = 7,
                stones =
                    listOf(
                        Stone(18, 18, StoneColor.Black),
                        Stone(17, 18, StoneColor.Black),
                        Stone(18, 17, StoneColor.White),
                        Stone(17, 17, StoneColor.Black),
                        Stone(16, 17, StoneColor.White),
                        Stone(18, 16, StoneColor.White),
                    ),
                answerCol = 17,
                answerRow = 16,
                answerTurn = StoneColor.White,
            ),
            KifuData(
                "詰め碁 3",
                boardSize = BoardSize.Nineteen,
                offsetCol = 5,
                offsetRow = 3,
                extentCols = 9,
                extentRows = 6,
                stones =
                    listOf(
                        Stone(7, 8, StoneColor.Black),
                        Stone(8, 8, StoneColor.Black),
                        Stone(9, 8, StoneColor.White),
                        Stone(7, 7, StoneColor.White),
                        Stone(8, 7, StoneColor.Black),
                        Stone(9, 7, StoneColor.White),
                        Stone(7, 6, StoneColor.Black),
                        Stone(8, 6, StoneColor.White),
                    ),
                answerCol = 6,
                answerRow = 8,
                answerTurn = StoneColor.White,
            ),
        )
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
                ListScene(mockKifuList) { index -> navController.navigate("question/$index") }
            }
            composable("question/{index}") { backStackEntry ->
                val kifu =
                    backStackEntry.arguments?.getString("index")?.toIntOrNull()?.let {
                        mockKifuList.getOrNull(it)
                    }
                if (kifu != null) {
                    QuestionScene(kifu)
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Not Found")
                    }
                }
            }
        }
    }
}
