package de.scala_quest.model.defaultImpl

import de.scala_quest.model.{Game => GameTrait, Player => PlayerTrait}

case class Game(players: List[PlayerTrait] = List())
  extends GameTrait {

  override def addPlayer(player: PlayerTrait): Game = {
    Game(players :+ player)
  }

  override def removePlayer(player: PlayerTrait): Game = {
    Game(players.filter(_ != player))
  }

  override def playerCount(): Int = players.size

}
