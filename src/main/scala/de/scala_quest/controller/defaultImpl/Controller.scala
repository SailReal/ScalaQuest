package de.scala_quest.controller.defaultImpl

import de.scala_quest.{GameState, UpdateAction}
import de.scala_quest.controller.{Controller => ControllerTrait}
import de.scala_quest.model.{Game, Player => PlayerTrait}
import de.scala_quest.model.defaultImpl.Player
import scala.util.Random

case class Controller(private var gameState: GameState) extends ControllerTrait {

  // TODO remove ALL get's

  override def onQuit(): Game = {
    gameState = GameState(UpdateAction.CLOSE_APPLICATION, gameState.game)
    notifyObservers(gameState)
    gameState.game
  }

  override def newGame(): Game = {
    gameState = GameState(UpdateAction.NEW_GAME, gameState.game.createQuestionList)
    notifyObservers(gameState)
    gameState.game
  }

  override def startGame(): Game = {
    gameState = GameState(UpdateAction.SHOW_GAME, gameState.game.start)
    notifyObservers(gameState)
    gameState.game
  }

  override def addNewPlayerToGame(name: String): Game = {
    val questionList = gameState.game.questionList

    // shuffle the questionList for each new player
    val newPlayer = Player(name, 0, 0, Random.shuffle(questionList), List(), List(), Option.empty)

    gameState = GameState(UpdateAction.PLAYER_UPDATE, gameState.game.addNewPlayer(newPlayer))
    notifyObservers(gameState)
    gameState.game
  }

  /** Remove a player with the given name from the game.
   *
   * @param name the player's name
   */
  override def removePlayer(name: String): Game = {
    val newPlayer = Player(name, 0, 0, List(), List(), List(), Option.empty)
    gameState = GameState(UpdateAction.PLAYER_UPDATE, gameState.game.removePlayer(newPlayer))
    notifyObservers(gameState)
    gameState.game
  }

  override def getPlayerInfo() : (String, String) = {
    (gameState.game.currentPlayer.get.name, gameState.game.currentPlayer.get.points.toString)
  }

  def getCurrentPlayer(): Option[PlayerTrait] = gameState.game.currentPlayer

  override def getPlayerNames(): List[String] = gameState.game.players.map(player => player.name)

  override def getPlayersCurrentQuestion(): Option[String] = {
    if (gameState.game.currentPlayer.get.questionIndex >= gameState.game.currentPlayer.get.questions.length) {
      None
    } else {
      val game = gameState.game.nextQuestion(gameState.game.currentPlayer.get)
      gameState = GameState(UpdateAction.SHOW_GAME, game)
      Some(gameState.game.currentPlayer.get.currentQuestion.get.text)
    }
  }

  override def getPlayersCurrentAnswers(): List[String] = {
    // TODO remove gets
    gameState.game.currentPlayer.get.currentQuestion.get.answers.map(a => a.text)
  }

  override def getPlayerCount(): Int = gameState.game.playerCount()

  override def checkGameRoundStatus(): Boolean = {
    if (gameState.game.currentRoundNr <= gameState.game.maxRoundNr) {
      true
    } else {
      gameState = GameState(UpdateAction.SHOW_RESULT, gameState.game)
      notifyObservers(gameState)
      false
    }
  }

  /**
   * NB: Only used by the TUI. The GUI will need a different mechanism to process Answers.
   * @param input
   */
  def processAnswer(input: Int): Game = {
    val player = getCurrentPlayer()
    val currentQuestion = player.get.questions.lift(player.get.questionIndex).get
    val correctAnswer = currentQuestion.correctAnswer

    val updatedPlayer = if(input == correctAnswer) {
      player.get.correctAnswer(currentQuestion)
    } else {
      player.get.wrongAnswer(currentQuestion)
    }
    val game = gameState.game.updatePlayer(updatedPlayer).updateState()
    if (gameState.game.currentRoundNr == gameState.game.maxRoundNr + 1) {
      gameState = GameState(UpdateAction.SHOW_RESULT, game) // TODO delete
      println("SHOW_RESULT: " + gameState.game.currentRoundNr)
    } else {
      gameState = GameState(UpdateAction.SHOW_GAME, game) // TODO delete
      println("SHOW_GAME: " + gameState.game.currentRoundNr)
    }
    //gameState = GameState(UpdateAction.DO_NOTHING, game) // changed from show game to do nothing
    notifyObservers(gameState)
    gameState.game
  }

  override def getPlayers(): List[PlayerTrait] = gameState.game.players
  override def getRoundNr(): Int = gameState.game.currentRoundNr

  override def nextPlayerName(): Option[String] = {
    gameState.game.playerCount() match {
      case c if c < gameState.game.maxPlayerCount => Some("Player " + (gameState.game.playerCount + 1).toString)
      case _ => None
    }
  }

}
