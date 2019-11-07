package de.scala_quest.controller.defaultImpl

import de.scala_quest.controller.{Controller => ControllerTrait}
import de.scala_quest.model.Game
import com.google.inject.Inject
import de.scala_quest.model.defaultImpl.Player

/**
 *
 * @param game
 */
class Controller @Inject()(game: Game) extends ControllerTrait {

  override def onQuit(): Unit = {
    // notify observers
  }

  override def newGame() : Unit = {
    // notify observers
    // set game status to newGame
  }

  override def createPlayer(name: String): Unit = {
    game.addPlayer(name)
    // notify observers new player
  }

  override def onAnswerChosen(answerId: Int): Unit = ???

  override def getPlayerList(): List[String] = {
    game.players.map(player => player.name)
  }
}
