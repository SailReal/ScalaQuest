package de.scala_quest.controller.defaultImpl

import de.scala_quest.controller.{Controller => ControllerTrait}
import de.scala_quest.model.Game
import com.google.inject.Inject
import de.scala_quest.model.{Player => PlayerTrait}
import de.scala_quest.model.defaultImpl.{Answer, Player, Question}
import scala.util.Random

/**
 *
 * @param game
 */
class Controller @Inject()(game: Game) extends ControllerTrait {

  override def onQuit(): Unit = {
    // notify observers
  }

  override def newGame() : Unit = {
    // notify observers
    // set game status to newGame
    game.createQuestionList()
  }

  override def startGame(): Unit = {
    // TODO pack into method game.setInitialState
    game.currentPlayer = game.players.lift(0).get
    game.maxRoundNr = 3
    game.currentRoundNr = 1
  }

  override def addNewPlayerToGame(name: String): Unit = {
    val questionList = game.questionList

    // shuffle the questionList for each new player
    val newPlayer = Player(name, 0, 0, Random.shuffle(questionList), List(), List(), null)
    newPlayer.currentQuestion = newPlayer.questions(0).asInstanceOf[Question] //r
    game.addNewPlayer(newPlayer)
    // notify observers new player
  }

  override def getPlayerInfo() : (String, String) = {
    (game.currentPlayer.name, game.currentPlayer.points.toString)
  }

  def getCurrentPlayer(): PlayerTrait = game.currentPlayer

  override def getPlayerNames(): List[String] = game.players.map(player => player.name)

  override def getPlayersCurrentQuestion(): Option[String] = {
    if (game.currentPlayer.questionIndex >= game.currentPlayer.questions.length) {
      None
    } else {
      game.currentPlayer.currentQuestion = game.currentPlayer.questions.lift(game.currentPlayer.questionIndex).get
      Some(game.currentPlayer.currentQuestion.text)
    }
  }

  override def getPlayersCurrentAnswers(): List[String] = {
    game.currentPlayer.currentQuestion.answers.map(a => a.text)
  }

  /**
   * NB: Only used by the TUI. The GUI will need a different mechanism to process Answers.
   * @param input
   */
  def processAnswer(input: Int): Unit = {
    var player = getCurrentPlayer()
    var currentQuestion = player.getNextQuestion()
    var correctAnswer = currentQuestion.correctAnswer

    if(input == correctAnswer) { // increase players points and add question to correct answers
      player.points += currentQuestion.points
      player.correctAnswers = player.correctAnswers :+ currentQuestion
    } else {
      player.wrongAnswers = player.wrongAnswers :+ currentQuestion
    }

    // Update player's current question
    player.questionIndex += 1
    // update the game's state
    game.updateState()
  }

  override def getPlayers(): List[PlayerTrait] = game.players
  override def getRoundNr(): Int = game.currentRoundNr
}
