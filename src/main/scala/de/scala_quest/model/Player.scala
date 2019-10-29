package de.scala_quest.model

trait Player {
  val name: String
  var points: Int
  var correctAnswers: List[Question]
  var wrongAnswers: List[Question]
  def toString: String
}
