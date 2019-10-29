package de.scala_quest.model.defaultImpl

import java.security.InvalidParameterException

import de.scala_quest.model.{Player => PlayerTrait, Question => QuestionTrait}

case class Player(
  name: String,
  var points: Int = 0,
  var correctAnswers: List[QuestionTrait] = List(),
  var wrongAnswers: List[QuestionTrait] = List()
) extends PlayerTrait {
  if  (name.isEmpty) {
    throw new InvalidParameterException("Player name cannot be empty")
  }

  override def toString: String = name
}


