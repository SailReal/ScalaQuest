package de.scala_quest.controller

import de.scala_quest.model.{Player, Question}

trait Controller {

  /** Starts a new game. */
  def newGame()

  /** Initializes the game. */
  def startGame()

  /** Quits the game. */
  def onQuit()

  /** Adds a new player with the given name to the game.
   *
   * @param name the player's name
   */
  def addNewPlayerToGame(name: String)

  /** Returns a list containing all player names.
   *
   * @return a list containing a string representation of the player's names
   */
  def getPlayerNames(): List[String]

  /** Returns the name of the current player whose turn it is.
   *
   * @return the player's name
   */
  def getCurrentPlayersName(): String

  /** Returns a tuple
   *
   * @return
   */
  def getPlayerInfo(): (String, String, String)

  def getCurrentPlayer(): Player

  def getPlayersCurrentQuestion(): Option[String]

  def getPlayersCurrentAnswers(): List[String]

  def getRoundNr(): Int

  def getGameResults(): String

  /** Processes the keyboard input received from the user while answering a question.
   *
   * @param input a numerical representation of the keyboard input corresponding to the selected answer
   */
  def processAnswer(input: Int): Unit

}
