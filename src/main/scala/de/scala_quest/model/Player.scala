package de.scala_quest.model

trait Player {
  val name: String
  val points: Int
  val questions: List[Question]
  val correctAnswers: List[Question]
  val wrongAnswers: List[Question]
  def toString: String
}
