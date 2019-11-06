package de.scala_quest.controller.defaultImpl

import com.google.inject.Inject
import de.scala_quest.controller.{Controller => ControllerTrait}
import de.scala_quest.model.Game

class Controller @Inject()(game: Game) extends ControllerTrait {

  override def onQuit(): Unit = ???

  override def onAnswerChosen(answerId: Int): Unit = ???

}
