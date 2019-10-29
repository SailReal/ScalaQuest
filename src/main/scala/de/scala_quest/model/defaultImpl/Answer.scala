package de.scala_quest.model.defaultImpl

import de.scala_quest.model.{Answer => AnswerTrait}

case class Answer(id: Int, text: String) extends AnswerTrait {}