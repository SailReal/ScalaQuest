package de.scala_quest.model

import de.scala_quest.model.defaultImpl.{Player, Question}

trait Game {
  val players: List[Player]
  val questions: List[Question]
}
