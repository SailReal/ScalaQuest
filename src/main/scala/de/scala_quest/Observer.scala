package de.scala_quest

object UpdateAction extends Enumeration {
    type UpdateAction = Value
    val BEGIN, CLOSE_APPLICATION, PLAYER_UPDATE, SHOW_HELP,
        SHOW_GAME, SHOW_RESULT, TIMER_UPDATE = Value
}

trait Observer {
    def update(updateData: GameState): Unit
}
