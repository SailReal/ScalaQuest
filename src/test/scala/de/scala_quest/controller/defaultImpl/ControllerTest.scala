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
    val player = Player("TestPlayer", 0, 0, questions, List(), List(), None)
    val game = Game(List(player), Some(player), 0, 0, 1, List())
    val gameState = GameState(UpdateAction.DO_NOTHING, game)

    "constructed" should {
      val controller = new Controller(gameState)

      "be able to quit the game" in {
        controller.onQuit()
      }

      "be able to create a new game" in {
        controller.newGame()
      }

      "be able to start a game" in {
        controller.startGame()
      }

      "be able to add a new Player to the game" in {
        gameState.game.playerCount() should be(1)
        gameState.game = controller.addNewPlayerToGame("Foo")
        gameState.game.playerCount() should be(2)
      }

      "be able to remove a player with the given name from the game" in {
        gameState.game.playerCount() should be (2)
        gameState.game = controller.removePlayer("Foo")
        gameState.game.playerCount() should be (1)
      }

      "be able to get a player's information" in {
        gameState.game = controller.addNewPlayerToGame("Bar")
        gameState.game.playerCount() should be(2)
        //gameState.game.updateState()

        val playerInfo = controller.getPlayerInfo()
        playerInfo should be("TestPlayer", "0")
      }

      "be able to get the current player" in {
        controller.getCurrentPlayer()
      }

      "be able to get player names" in {
        controller.getPlayerNames()
      }

      "be able to get the player's current question" in {
        val currentQuestionText = controller.getPlayersCurrentQuestion()
        currentQuestionText should be(Some("Every value in Scala is an object. True or False?"))
      }

      "be able to get the player's current answers" in {
        controller.getPlayersCurrentAnswers()
      }

      "be able to get the number of players in game" in {
        controller.getPlayerCount()
      }

      "be able to check the game's round status when true" in {
        val roundStatus = controller.checkGameRoundStatus()
        roundStatus should be(true)
      }

      "be able to get the current round number" in {
        val roundNr = controller.getRoundNr()
        roundNr should be(gameState.game.currentRoundNr)
      }

      "be able to process the player's wrong answer" in {
        controller.processAnswer(0)
      }

      "be able to process the player's correct answer" in {
        controller.processAnswer(1)
      }

      "be able to get a list of all players" in {
        controller.getPlayers()
      }

      "be able to get the next player's name" in {
        controller.nextPlayerName()
      }
    }
  }

  "A Controller" when {
    val player = Player("TestPlayer", 0, 0, List(), List(), List(), None)
    val game = Game(List(player), Some(player), 0, 1, 2, List())
    val gameState = GameState(UpdateAction.SHOW_RESULT, game)

    "constructed" should {
      val controller = new Controller(gameState)

      "return none to when player has no more questions left" in {
        controller.getPlayersCurrentQuestion()
      }

      "be able to check the game's round status when false" in {
        val roundStatus = controller.checkGameRoundStatus()
        roundStatus should be(false)
      }

      "applied should accept the arguments" in {
        Controller.apply(gameState)
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
      val controller = new Controller(gameState)

      "be able to process the player's correct answer and realize the round is over" in {
        controller.processAnswer(1)
      }
    }
  }
}

