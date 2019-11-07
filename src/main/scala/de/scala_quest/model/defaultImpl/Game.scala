package de.scala_quest.model.defaultImpl

import de.scala_quest.model.{Game => GameTrait, Player => PlayerTrait}

case class Game (
  var players: List[PlayerTrait] = List()
) extends GameTrait {

  override def addPlayer(name: String): Unit = {
    // addPlayer parameter = name: String
    // create questionList for player
    val answer = Answer(1, "Bar")
    val question = Question(1, "After Foo comes?", 215, List(answer), 1, 21)
    val p = Player(name, 23, List(question), List.empty)
    players = players :+ p
  }

  override def removePlayer(player: PlayerTrait): Game = {
    Game(players.filter(_ != player))
  }

  override def playerCount(): Int = players.size

}
