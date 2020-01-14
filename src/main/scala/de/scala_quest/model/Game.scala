package de.scala_quest.model

trait Game {

  val maxPlayerCount = 2

  /** A list of players currently in the game. */
  val players: List[Player]
  /** The player whose turn it currently is. */
  val currentPlayer: Option[Player]
  /** The index of the current player within the list. */
  val currentPlayerIndex: Int
  /** The maximum number of rounds to play. */
  val maxRoundNr: Int
  /** The current round. */
  val currentRoundNr: Int
  /** A list of questions available in the game. */
  val questionList: List[Question]

  /** Adds a new player to the list of players.
   *
   * @param player the player object to be added
   */
  def addNewPlayer(player: Player): Game

  /** Removes a player from the game.
   *
   * @param player the player to be removed
   * @return
   */
  def removePlayer(player: Player): Game

  /** Updates the game's state. */
  def updateState(): Game

  /** Returns the number of players currently in the game. */
  def playerCount(): Int

  /** Creates the question list upon new game. */
  def createQuestionList: Game

  def start: Game

  def updatePlayer(player: Player): Game

  def nextQuestion(player: Player): Game

  def updateRoundNr: Game

}
