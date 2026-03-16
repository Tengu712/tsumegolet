package com.skdassoc.tsumegolet.model

import kotlinx.serialization.Serializable

@Serializable data class Stone(val coord: BoardCoord, val color: StoneColor)
