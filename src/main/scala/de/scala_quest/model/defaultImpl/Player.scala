package de.scala_quest.model.defaultImpl

import java.security.InvalidParameterException

import de.scala_quest.model.{Player => PlayerTrait, Question => QuestionTrait}

case class Player(
  name: String,
  points: Int = 0,
  questionIndex: Int = 0,
  questions: List[QuestionTrait] = List(),
  correctAnswers: List[QuestionTrait] = List(),
  wrongAnswers: List[QuestionTrait] = List(),
  currentQuestion: Option[QuestionTrait] = Option.empty
) extends PlayerTrait {

  if  (name.isEmpty) {
    throw new InvalidParameterException("Player name cannot be empty")
  }

  override def toString: String = name

  override def correctAnswer(question: QuestionTrait): PlayerTrait =
    copy(points = points + question.points, correctAnswers = correctAnswers :+ question, questionIndex = questionIndex + 1)

  override def wrongAnswer(question: QuestionTrait): PlayerTrait =
    copy(wrongAnswers = wrongAnswers :+ question, questionIndex = questionIndex + 1)

  override def nextQuestion(): PlayerTrait = copy(currentQuestion = questions.lift(questionIndex))
}