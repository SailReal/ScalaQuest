package de.scala_quest.model

trait Player {
  val name: String
  val points: Int
  val questionIndex: Int
  val questions: List[Question]
  val correctAnswers: List[Question]
  val wrongAnswers: List[Question]

  /**
   *
   * @return
   */
  def getNextQuestion(): Question

  /**
   *
   * @return
   */
  def toString: String
}
