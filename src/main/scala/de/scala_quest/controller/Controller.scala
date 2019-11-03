package de.scala_quest.controller

trait Controller {

  def onQuit()
  def onAnswerChosen(answerId: Int)

}
