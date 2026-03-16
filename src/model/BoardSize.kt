package com.skdassoc.tsumegolet.model

import kotlinx.serialization.Serializable

@Serializable
enum class BoardSize(val value: Int) {
    Nine(9),
    Thirteen(13),
    Nineteen(19),
}
