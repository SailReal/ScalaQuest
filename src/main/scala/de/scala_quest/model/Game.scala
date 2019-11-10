package de.scala_quest.model

import de.scala_quest.model.defaultImpl.Question

trait Game {
  /** A list of players currently in the game. */
  var players: List[Player]
  /** The player whose turn it currently is. */
  var currentPlayer: Player
  /** The index of the current player within the list. */
  var currentPlayerIndex: Int
  /** The maximum number of rounds to play. */
  var maxRoundNr: Int
  /** The current round. */
  var currentRoundNr: Int
  /** A list of questions available in the game. */
  var questionList: List[Question]

  /** Adds a new player to the list of players.
   *
   * @param player the player object to be added
   */
  def addNewPlayer(player: Player): Unit

  /** Removes a player from the game.
   *
   * @param player the player to be removed
   * @return
   */
  def removePlayer(player: Player): Game

  /** Updates the game's state. */
  def updateState(): Unit

  /** Returns the number of players currently in the game. */
  def playerCount(): Int

  /** Creates the question list upon new game. */
  def createQuestionList(): Unit
}
