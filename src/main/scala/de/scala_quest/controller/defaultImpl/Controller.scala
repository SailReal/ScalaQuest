package de.scala_quest.controller.defaultImpl

import de.scala_quest.controller.{Controller => ControllerTrait}
import de.scala_quest.model.Game
import com.google.inject.Inject
import de.scala_quest.model.{Player => PlayerTrait}
import de.scala_quest.model.defaultImpl.{Answer, Player, Question}

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
    // assign question list to game.QnA
  }

  override def addNewPlayerToGame(name: String): Unit = {
    // Create player and add to game.
    val ans1 = List(Answer(1, "True"), Answer(2, "False"))
    val question1 = Question(1, "Every value in Scala is an object. True or False?", 10, ans1, 1, 10)

    val ans2 = List(Answer(1, "var"), Answer(2, "val"))
    val question2 = Question(2, "Mutable variables start with the keyword", 10, ans2, 1, 10)

    val ans3 = List(Answer(1, "1, 2, 3, 4, 5, 6"), Answer(2, "1, 2, 3, 4, 5, 6, 7"))
    val question3 = Question(3, "In scala, the expression '1 to 7' returns a range from", 10, ans3, 2, 10)

    val newPlayer = Player(name, 0, 0, List(question1, question2, question3), List(), List(), null)
    newPlayer.currentQuestion = newPlayer.questions(0).asInstanceOf[Question]
    game.addNewPlayer(newPlayer)
    // notify observers new player
  }

  override def getCurrentPlayersName(): String = {
    game.currentPlayer().name
  }

  override def getCurrentPlayer(): PlayerTrait = {
    game.currentPlayer()
  }

  override def getPlayerList(): List[String] = {
    game.players.map(player => player.name)
  }

  override def getPlayersCurrentQuestion(): Option[String] = {
    if (game.currentPlayer().questionIndex >= game.currentPlayer().questions.length) {
      None
    } else {
      game.currentPlayer().currentQuestion = game.currentPlayer().questions.lift(game.currentPlayer().questionIndex).get
      Some(game.currentPlayer().currentQuestion.text)
    }
  }

  override def getPlayersCurrentAnswers(): List[String] = {
    game.currentPlayer().currentQuestion.answers.map(a => a.text)
  }

  /**
   * NB: Only used by the TUI. The GUI will need a different mechanism to process Answers.
   * @param input
   */
  def processAnswer(input: Int): Unit = {
    var player = getCurrentPlayer()
    var currentQuestion = player.getNextQuestion()
    var correctAnswer = currentQuestion.correctAnswer

    println("current Question: "+ currentQuestion)
    println("correct Answer: "+ correctAnswer)

    if(input == correctAnswer) { // increase players points and add question to correct answers
      println("points before: "+player.points)
      player.points += currentQuestion.points
      println("points after: "+player.points)
      player.correctAnswers = player.correctAnswers :+ currentQuestion
      println("CORRECT")
    } else {
      player.wrongAnswers = player.wrongAnswers :+ currentQuestion
      println("WRONG")
    }

    // Update player's current question
    player.questionIndex += 1
  }
}
