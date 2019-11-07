package de.scala_quest.model

trait Player {
  val name: String
  var points: Int
  var questionIndex: Int
  var questions: List[Question]
  var correctAnswers: List[Question]
  var wrongAnswers: List[Question]
  var currentQuestion: Question

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
