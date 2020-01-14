package de.scala_quest.model

trait Player {
  /** The name of the player. */
  val name: String
  /** The players accumulated point standings. */
  val points: Int
  /** A list of questions appointed to the player. */
  val questions: List[Question]
  /** The current question index within the player's question list. */
  val questionIndex: Int
  /** A list containing the player's correctly answered questions. */
  val correctAnswers: List[Question]
  /** A list containing the player's wrongly answered questions. */
  val wrongAnswers: List[Question]
  /** The player's current question. */
  val currentQuestion: Option[Question]

  /** Returns the name of the player.
   *
   * @return the player's name
   */
  def toString: String

  def correctAnswer(question: Question): Player

  def wrongAnswer(question: Question): Player

  def nextQuestion() : Player

}
