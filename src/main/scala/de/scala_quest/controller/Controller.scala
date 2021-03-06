package de.scala_quest.controller

import de.scala_quest.Observable
import de.scala_quest.model.{Game, Player}

trait Controller extends Observable {

  /** Starts a new game. */
  def newGame(): Game

  /** Initializes the game. */
  def startGame(): Game

  /** Quits the game. */
  def onQuit(): Game

  /** Adds a new player with the given name to the game.
   *
   * @param name the player's name
   */
  def addNewPlayerToGame(name: String): Game

  /** Remove a player with the given name from the game.
   *
   * @param name the player's name
   */
  def removePlayer(name: String): Game

  /** Returns a list containing all player names.
   *
   * @return a list containing a string representation of the player's names
   */
  def getPlayerNames(): List[String]

  def checkGameRoundStatus(): Boolean

  /** Returns a tuple
   *
   * @return
   */
  def getPlayerInfo(): (String, String)

  /** Returns the players current question.
   *
   * @return the question to be asked
   */
  def getPlayersCurrentQuestion(): Option[String]

  /** Returns the players current question answers.
   *
   * @return the answers to the current question
   */
  def getPlayersCurrentAnswers(): List[String]

  /** Gets the current game's round number.
   *
   * @return the current round number
   */
  def getRoundNr(): Int

  /** Returns a list of all players.
   *
   * @return the list of players
   */
  def getPlayers(): List[Player]

  /** Gets the number of players within the game. */
  def getPlayerCount(): Int

  /** Processes the keyboard input received from the user while answering a question.
   *
   * @param input a numerical representation of the keyboard input corresponding to the selected answer
   */
  def processAnswer(input: Int): Game

  def nextPlayerName(): Option[String]
}
