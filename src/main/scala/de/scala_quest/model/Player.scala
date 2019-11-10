package de.scala_quest.model

trait Player {
  /** The name of the player. */
  val name: String
  /** The players accumulated point standings. */
  var points: Int
  /** A list of questions appointed to the player. */
  var questions: List[Question]
  /** The current question index within the player's question list. */
  var questionIndex: Int
  /** A list containing the player's correctly answered questions. */
  var correctAnswers: List[Question]
  /** A list containing the player's wrongly answered questions. */
  var wrongAnswers: List[Question]
  /** The player's current question. */
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

  def resultString: String

}
