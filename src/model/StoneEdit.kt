package com.skdassoc.tsumegolet.model

fun putOrRemoveStone(
    stones: List<Stone>,
    turn: StoneColor,
    coord: BoardCoord,
): Pair<List<Stone>, StoneColor> {
    val index = stones.indexOfFirst { it.coord == coord }
    return if (index >= 0) {
        Pair(stones.toMutableList().also { it.removeAt(index) }, turn)
    } else {
        Pair(stones.toMutableList().also { it.add(Stone(coord, turn)) }, turn.flipped)
    }
}
