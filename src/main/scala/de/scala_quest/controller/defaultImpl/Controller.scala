package de.scala_quest.controller.defaultImpl

import de.scala_quest.controller.{Controller => ControllerTrait}
import de.scala_quest.model.Game

class Controller(game: Game) extends ControllerTrait {

  override def onQuit(): Unit = ???

  override def onAnswerChosen(answerId: Int): Unit = ???

}
