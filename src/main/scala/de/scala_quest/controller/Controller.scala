package de.scala_quest.controller

trait Controller {

  /** Starts a new game. */
  def newGame()

  /** Quits the game. */
  def onQuit()

  /** Creates a new player with a given name.
   *
   * @param name the player's name
   */
  def createPlayer(name: String)

  /** Returns a list of all players.
   *
   * @return a list containing a string representation of the players
   */
  def getPlayerList(): List[String]

  def onAnswerChosen(answerId: Int)

}
