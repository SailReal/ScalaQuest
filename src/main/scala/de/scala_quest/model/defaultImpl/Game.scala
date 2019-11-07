package de.scala_quest.model.defaultImpl

import de.scala_quest.model.{Game => GameTrait, Player => PlayerTrait}

case class Game (
  var players: List[PlayerTrait] = List()
) extends GameTrait {

  override def addNewPlayer(newPlayer: PlayerTrait): Unit = {
    // addPlayer parameter = name: String
    // create questionList for player
    players = players :+ newPlayer
  }

  override def removePlayer(player: PlayerTrait): Game = {
    Game(players.filter(_ != player))
  }

  override def currentPlayer(): PlayerTrait = {
    players.lift(0).get
  }

  override def playerCount(): Int = players.size

}
