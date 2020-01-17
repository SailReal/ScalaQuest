package de.scala_quest.controller.defaultImpl

import de.scala_quest.model.defaultImpl.{Answer, Game, Player, Question}
import de.scala_quest.{GameState, Observer, UpdateAction}
import org.scalatest.{Matchers, WordSpec}

class ControllerTest extends WordSpec with Matchers {

  "A Controller" when {
    val ans1 = List(Answer(1, "True"), Answer(2, "False"))
    val question1 = Question(1, "Every value in Scala is an object. True or False?", 10, ans1, 1)
    val ans2 = List(Answer(1, "var"), Answer(2, "val"))
    val question2 = Question(2, "Mutable variables start with the keyword", 10, ans2, 1)
    val questions = List(question1, question2)
    val player1 = Player("TestPlayer", 0, 0, questions, List(), List(), Some(question1))
    val player2 = Player("TestPlayer2", 0, 0, questions, List(), List(), Some(question1))
    val game = Game(List(player1, player2), Some(player1), 0, 2, 1, List())
    val gameState = GameState(UpdateAction.DO_NOTHING, game)

    "constructed" should {

      "be able to quit the game" in {
        Controller(gameState).onQuit()
      }

      "be able to create a new game" in {
        Controller(gameState).newGame().questionList should not be(List())
      }

      "be able to start a game" in {
        Controller(gameState).startGame().currentRoundNr should be(1)
      }

      "be able to add a new Player to the game" in {
        gameState.game.playerCount() should be(2)
        Controller(gameState).addNewPlayerToGame("Foo").playerCount() should be(3)
      }

      "be able to remove a player with the given name from the game" in {
        gameState.game.playerCount() should be (2)
        Controller(gameState).removePlayer(player1.name).playerCount() should be (1)
      }

      "be able to get a player's information" in {
        Controller(gameState).getPlayerInfo() should be("TestPlayer", "0")
      }

      "be able to get the current player" in {
        Controller(gameState).getCurrentPlayer() should be(Option(player1))
      }

      "be able to get player names" in {
        Controller(gameState).getPlayerNames() should be(List("TestPlayer", "TestPlayer2"))
      }

      "be able to get the player's current question" in {
        val currentQuestionText = Controller(gameState).getPlayersCurrentQuestion()
        currentQuestionText should be(Some("Every value in Scala is an object. True or False?"))
      }

      "be able to get the player's current answers" in {
        Controller(gameState).getPlayersCurrentAnswers() should be(List("True", "False"))
      }

      "be able to get the number of players in game" in {
        Controller(gameState).getPlayerCount() should be(2)
      }

      "be able to check the game's round status when true" in {
        Controller(gameState).checkGameRoundStatus() should be(true)
      }

      "be able to get the current round number" in {
        Controller(gameState).getRoundNr() should be(gameState.game.currentRoundNr)
      }

      "be able to process the player's wrong answer" in {
        /*val controller = Controller(gameState)
        val wrongAnswers = controller.getCurrentPlayer().get.wrongAnswers.size
        val game = controller.processAnswer(0)
        game.currentPlayer.get.wrongAnswers.size should be(wrongAnswers+1)*/

        Controller(gameState).processAnswer(0)
      }

      "be able to process the player's correct answer" in {
        /*val controller = Controller(gameState)
        val correctAnswers = controller.getCurrentPlayer().get.correctAnswers.size
        val game = controller.processAnswer(1)
        game.currentPlayer.get.correctAnswers.size should be(correctAnswers+1)*/

        Controller(gameState).processAnswer(1)
      }

      "be able to get a list of all players" in {
        Controller(gameState).getPlayers() should be(List(player1, player2))
      }

      "be able to get the next player's name" in {
        val tmpGameState = GameState(UpdateAction.DO_NOTHING, gameState.game.removePlayer(player1))
        Controller(tmpGameState).nextPlayerName() should be(Some("Player 2"))
      }
    }
  }

  "A Controller" when {
    val player = Player("TestPlayer", 0, 0, List(), List(), List(), None)
    val game = Game(List(player), Some(player), 0, 1, 2, List())
    val gameState = GameState(UpdateAction.SHOW_RESULT, game)

    "constructed" should {
      val controller = Controller(gameState)

      "return none to when player has no more questions left" in {
        controller.getPlayersCurrentQuestion() should be(Option.empty)
      }

      "be able to check the game's round status when false" in {
        val roundStatus = controller.checkGameRoundStatus()
        roundStatus should be(false)
      }

      "applied should accept the arguments" in {
        val controller = Controller.apply(gameState)
        controller.checkGameRoundStatus() should be(false)
      }

      "unapplied should have arguments" in {
        Controller.unapply(controller).get should be(gameState)
      }
    }
  }

  "A Controller" when {
    val ans1 = List(Answer(1, "True"), Answer(2, "False"))
    val question1 = Question(1, "Every value in Scala is an object. True or False?", 10, ans1, 1)
    val player = Player("TestPlayer", 0, 0, List(question1), List(), List(), None)
    val game = Game(List(player), Some(player), 0, 1, 2, List())
    val gameState = GameState(UpdateAction.SHOW_RESULT, game)

    "constructed" should {

      "be able to process the player's correct answer and realize the round is over" in {
        Controller(gameState).processAnswer(1)
      }
    }
  }
}

