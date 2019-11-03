package de.scala_quest.model.defaultImpl

import de.scala_quest.model.{ Game => GameTrait}

case class Game(
                 players: List[Player] = List(),
                 questions: List[Question] = List())
  extends GameTrait {}
