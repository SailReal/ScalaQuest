package de.scala_quest.model

import de.scala_quest.model.defaultImpl.{Answer, Question}

trait Game {
  var players: List[Player]
  var currentPlayer: Player
  var currentPlayerIndex: Int
  var maxRoundNr: Int
  var currentRoundNr: Int
  var questionList: List[Question]

  /** Adds a new player with the given name to the list of players.
   *
   * @param player the player object to be added
   */
  def addNewPlayer(player: Player): Unit

  def removePlayer(player: Player): Game

  /** Updates the game's state. */
  def updateState(): Unit

  /** Returns the number of players currently in the game. */
  def playerCount(): Int

  /** Creates the question list upon new game. */
  def createQuestionList(): Unit

}
