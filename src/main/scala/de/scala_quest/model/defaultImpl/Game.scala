package de.scala_quest.model.defaultImpl

import de.scala_quest.model.{Game => GameTrait, Player => PlayerTrait}

case class Game (
  var players: List[PlayerTrait] = List(),
  var currentPlayerIndex: Int = 0,
  var maxRoundNumber: Int = 0,
  var currentRoundNumber: Int = 0,
) extends GameTrait {

  override def addNewPlayer(newPlayer: PlayerTrait): Unit = {
    // addPlayer parameter = name: String
    // create questionList for player
    players = players :+ newPlayer
  }

  override def removePlayer(player: PlayerTrait): Game = {
    Game(players.filter(_ != player))
  }

  /*override def currentPlayer(): PlayerTrait = {
    players.lift(0).get
  }*/

  override def playerCount(): Int = players.size

  override def updateState() : Unit = {
    currentPlayerIndex += 1
    if (currentPlayerIndex < players.size) {
      currentPlayer = players.lift(currentPlayerIndex).get
    } else {
      currentPlayerIndex = 0
      currentRoundNr += 1
      currentPlayer = players.lift(currentPlayerIndex).get
    }

  }

  override def results(): String = {
    var retVal = ""
    players.foreach(player => {
      retVal = retVal + player.resultString + "\n"
    })
    retVal
  }

  override var currentPlayer: PlayerTrait = null
  override var maxRoundNr: Int = 0
  override var currentRoundNr: Int = 0
}
