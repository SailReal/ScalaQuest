package de.scala_quest.model

trait Question {
  /** The question's unique identifier. */
  val id: Int
  /** The question in String-text form. */
  val text: String
  /** The number of points associated with answering the question correctly. */
  val points: Int
  /** A list of answers to the question. */
  val answers: List[Answer]
  /** The unique identifier of the correct answer. */
  val correctAnswer: Int
}
