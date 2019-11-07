package de.scala_quest.controller

import de.scala_quest.model.Player

trait Controller {

  /** Starts a new game. */
  def newGame()

  /** Quits the game. */
  def onQuit()

  /** Adds a new player with the given name to the game.
   *
   * @param name the player's name
   */
  def addNewPlayerToGame(name: String)

  /** Returns a list of all players.
   *
   * @return a list containing a string representation of the players
   */
  def getPlayerList(): List[String]

  /** Returns the current player whose turn it is.
   *
   * @return the player whose turn it is
   */
  def getCurrentPlayer(): Player

  /** Processes the keyboard input received from the user while answering a question.
   *
   * @param input a numerical representation of the keyboard input corresponding to the selected answer
   */
  def processAnswer(input: Int): Unit

}
