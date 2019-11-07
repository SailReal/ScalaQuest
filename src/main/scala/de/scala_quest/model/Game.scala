package de.scala_quest.model

trait Game {
  var players: List[Player]

  /** Adds a player with the given name to the list of players.
   *
   * @param name the name of the player to be added
   */
  def addPlayer(name: String): Unit

  def removePlayer(player: Player): Game

  /** Returns the number of players currently in the game. */
  def playerCount(): Int
}
