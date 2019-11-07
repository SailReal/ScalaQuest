package de.scala_quest.model.defaultImpl

import java.security.InvalidParameterException

import de.scala_quest.model.{Player => PlayerTrait, Question => QuestionTrait}

case class Player(
  name: String,
  points: Int = 0,
  questions: List[QuestionTrait] = List(),
  correctAnswers: List[QuestionTrait] = List(),
  wrongAnswers: List[QuestionTrait] = List()
) extends PlayerTrait {

  if  (name.isEmpty) {
    throw new InvalidParameterException("Player name cannot be empty")
  }

  override def toString: String = name
}


