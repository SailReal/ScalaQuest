package de.scala_quest

import de.scala_quest.UpdateAction.UpdateAction
import de.scala_quest.model.Game

case class GameState(var action: UpdateAction, var game: Game)
