package de.scala_quest.controller.defaultImpl

import de.scala_quest.controller.{Controller => ControllerTrait}

class Controller() extends ControllerTrait {

  override def onQuit(): Unit = ???

  override def onAnswerChosen(answerId: Int): Unit = ???

}
