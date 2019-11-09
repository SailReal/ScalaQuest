package de.scala_quest.model.defaultImpl

import java.security.InvalidParameterException

import de.scala_quest.model
import de.scala_quest.model.{Player => PlayerTrait, Question => QuestionTrait}

case class Player(
  name: String,
  var points: Int = 0,
  var questionIndex: Int = 0,
  var questions: List[QuestionTrait] = List(),
  var correctAnswers: List[QuestionTrait] = List(),
  var wrongAnswers: List[QuestionTrait] = List(),
  var currentQuestion: QuestionTrait = null
) extends PlayerTrait {

  if  (name.isEmpty) {
    throw new InvalidParameterException("Player name cannot be empty")
  }

  override def getNextQuestion(): QuestionTrait = {
    if (questionIndex < questions.length) {
      Some(questions.lift(questionIndex).get)
    } else {
      println("NO MORE QUESTIONS LEFT")
    }
    questions.lift(questionIndex).get
  }

  override def toString: String = name

  override def resultString: String = {
    val retVal = new StringBuilder
    retVal ++= s"Name: $name\n"
    retVal ++= s"Points: $points\n"
    retVal ++= s"Correctly answered questions: " + correctAnswers.size + "\n"
    retVal ++= s"Wrongly answered questions: " + wrongAnswers.size + "\n"

    retVal.toString()
  }
}


