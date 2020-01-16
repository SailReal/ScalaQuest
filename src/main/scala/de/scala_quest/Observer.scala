package de.scala_quest

object UpdateAction extends Enumeration {
    type UpdateAction = Value
    val NEW_GAME, CLOSE_APPLICATION, PLAYER_UPDATE, SHOW_HELP,
        SHOW_GAME, SHOW_RESULT, TIMER_UPDATE, DO_NOTHING = Value
}

trait Observer {
    def update(updateData: GameState) : Unit
}