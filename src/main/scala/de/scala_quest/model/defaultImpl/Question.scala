package de.scala_quest.model.defaultImpl

import de.scala_quest.model.{Answer => AnswerTrait, Question => QuestionTrait}

case class Question(
  id: Int,
  text: String,
  points: Int,
  answers: List[AnswerTrait],
  correctAnswer: Int,
  time: Int
) extends QuestionTrait {}
