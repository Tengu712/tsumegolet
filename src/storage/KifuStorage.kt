package com.skdassoc.tsumegolet.storage

import android.content.Context
import com.skdassoc.tsumegolet.model.KifuData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val FILE_NAME = "kifu-list.json"

fun saveKifuList(context: Context, kifuList: List<KifuData>) {
    context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
        it.write(Json.encodeToString(kifuList).toByteArray())
    }
}

fun loadKifuList(context: Context): List<KifuData> {
    val file = context.getFileStreamPath(FILE_NAME)
    if (!file.exists()) return emptyList()
    return try {
        Json.decodeFromString(context.openFileInput(FILE_NAME).bufferedReader().readText())
    } catch (_: Exception) {
        emptyList()
    }
}
