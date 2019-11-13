package de.scala_quest.controller.defaultImpl

import de.scala_quest.{GameState, UpdateAction}
import de.scala_quest.controller.{Controller => ControllerTrait}
import de.scala_quest.model.{Player => PlayerTrait}
import de.scala_quest.model.defaultImpl.Player

import scala.util.Random

case class Controller(var gameState: GameState) extends ControllerTrait {

  // TODO remove ALL get's

  override def onQuit(): Unit = {
    notifyObservers(GameState(UpdateAction.CLOSE_APPLICATION, gameState.game))
  }

  override def newGame(): Unit = {
    gameState = GameState(UpdateAction.BEGIN, gameState.game.createQuestionList) // TODO delete
    notifyObservers(gameState)
  }

  override def startGame(): Unit = {
    gameState = GameState(UpdateAction.BEGIN, gameState.game.start) // TODO delete
    notifyObservers(gameState)
  }

  override def addNewPlayerToGame(name: String): Unit = {
    val questionList = gameState.game.questionList

    // shuffle the questionList for each new player
    val newPlayer = Player(name, 0, 0, Random.shuffle(questionList), List(), List(), Option.empty)

    gameState = GameState(UpdateAction.PLAYER_UPDATE, gameState.game.addNewPlayer(newPlayer)) // TODO delete
    notifyObservers(gameState)
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

  /**
   * NB: Only used by the TUI. The GUI will need a different mechanism to process Answers.
   * @param input
   */
  def processAnswer(input: Int): Unit = {
    val player = getCurrentPlayer()
    val currentQuestion = player.get.questions.lift(player.get.questionIndex).get
    val correctAnswer = currentQuestion.correctAnswer

    val updatedPlayer = if(input == correctAnswer) {
      player.get.correctAnswer(currentQuestion)
    } else {
      player.get.wrongAnswer(currentQuestion)
    }
    val game = gameState.game.updatePlayer(updatedPlayer).updateState()
    gameState = GameState(UpdateAction.SHOW_GAME, game) // TODO delete
    notifyObservers(gameState)
  }

  override def getPlayers(): List[PlayerTrait] = gameState.game.players
  override def getRoundNr(): Int = gameState.game.currentRoundNr
}
