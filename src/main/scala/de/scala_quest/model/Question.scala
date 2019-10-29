package de.scala_quest.model

trait Question {
  val id: Int
  val text: String
  val points: Int
  val answers: List[Answer]
  val correctAnswer: Int
  val time: Int
}
