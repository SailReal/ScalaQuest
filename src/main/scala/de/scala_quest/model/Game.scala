package de.scala_quest.model

trait Game {
  var players: List[Player]
  var currentPlayer: Player
  var currentPlayerIndex: Int
  var maxRoundNr: Int
  var currentRoundNr: Int

  /** Adds a new player with the given name to the list of players.
   *
   * @param player the player object to be added
   */
  def addNewPlayer(player: Player): Unit

  def removePlayer(player: Player): Game

  //def currentPlayer(): Player

  def updateState(): Unit

  /** Returns the number of players currently in the game. */
  def playerCount(): Int

  def results(): String
}
