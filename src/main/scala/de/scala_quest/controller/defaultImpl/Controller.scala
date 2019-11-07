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
  }

  override def addNewPlayerToGame(name: String): Unit = {
    // Create player and add to game.
    val answer1 = Answer(1, "Bar")
    val answer2 = Answer(2, "Baz")
    val question = Question(1, "After Foo comes?", 215, List(answer1, answer2), 1, 21)
    val newPlayer = Player(name, 0, 0, List(question), List())
    game.addNewPlayer(newPlayer)
    // notify observers new player
  }

  override def getCurrentPlayer(): PlayerTrait = {
    game.currentPlayer()
  }

  override def getPlayerList(): List[String] = {
    game.players.map(player => player.name)
  }

  /**
   * NB: Only used by the TUI. The GUI will need a different mechanism to process Answers.
   * @param input
   */
  def processAnswer(input: Int): Unit = {
    var player = getCurrentPlayer()
    val currentQuestion = player.getNextQuestion()
    val correctAnswer = currentQuestion.correctAnswer

    println("current Question: "+ currentQuestion)
    println("correct Answer: "+ correctAnswer)

    if(input == correctAnswer) { // increase players points and add question to correct answers

      println("points before: "+player.points)

      println("points after: "+player.points)

      //player.correctAnswers = player.correctAnswers += currentQuestion
      println("CORRECT")
    } else {
      println("WRONG")
    }

    // Update player's current question
  }
}
