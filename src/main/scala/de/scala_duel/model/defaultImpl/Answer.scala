package de.scala_duel.model.defaultImpl

import de.scala_duel.model.{Answer => AnswerTrait}

case class Answer(id: Int, text: String) extends AnswerTrait {}