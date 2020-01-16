package de.scala_quest.model.defaultImpl

import org.scalatest.Matchers._
import org.scalatest.WordSpec

class GameTest extends WordSpec {
  final val player = Player("Foo", 0, 0, List(), List(), List(), None)
  final val player2 = Player("Baz", 0, 0, List(), List(), List(), None)
  final val players: List[Player] = List(player, player2)
  final val currentPlayer: Option[Player] = Option.empty
  final val currentPlayerIndex: Int = 0
  final val maxRoundNr: Int = 0
  final val currentRoundNr: Int = 0
  final val questionList: List[Question] = List()

  final val intest = Game(players, currentPlayer, currentPlayerIndex, maxRoundNr, currentRoundNr, questionList)

  "Game" should  {
    "have players" in {
      intest.players should equal(players)
    }

    "have an empty list of players" in {
      val game = Game(List(),currentPlayer, currentPlayerIndex, maxRoundNr, currentRoundNr, questionList)
      game.players should be(List())
    }

    "have a player count" in {
      intest.playerCount() should equal(2)
    }
  }

  "Game" can {
    "add a player" in {
      val player = Player("name", 0, 0, List())
      intest.addNewPlayer(player).playerCount() should equal(3)
    }

    "remove a player" in {
      val player = Player("Bar", 21, 0, List(), List.empty)
      val tmp = Game(List(player))
      tmp.removePlayer(player).players should equal(List())
    }
  }

  "A Game" can {
    "update a player" in {
      //intest.updatePlayer(player) should be (intest)
      val game1 = intest.updatePlayer(player)
      game1.players should be(players)
    }
  }

  "A Game" can {
    "update its state" in {
      val game1 = intest.updateState()
      val game2 = game1.updateState()

      game2.currentRoundNr should be(1)
    }
  }

  "A Game" can {
    "update round number" in {
      val tmp = Game(List(), None, 0, 0, 1, List())
      tmp.updateRoundNr should equal(Game(List(), None, 0, 0, 2, List()))
    }

    "create a question List" in {
      val ans1 = List(Answer(1, "True"), Answer(2, "False"))
      val question1 = Question(1, "Every value in Scala is an object. True or False?", 10, ans1, 1)

      val ans2 = List(Answer(1, "var"), Answer(2, "val"))
      val question2 = Question(2, "Mutable variables start with the keyword", 10, ans2, 1)

      val ans3 = List(Answer(1, "1, 2, 3, 4, 5, 6"), Answer(2, "1, 2, 3, 4, 5, 6, 7"))
      val question3 = Question(3, "In scala, the expression '1 to 7' returns a range from", 10, ans3, 2)

      val ans4 = List(Answer(1, "(String, Int, Char)"), Answer(2, "((String, Int), Char)"))
      val question4 = Question(4, "The expression \"Hello\" -> 42 -> 'c' is an instance of", 60, ans4, 2)

      val ans5 = List(Answer(1, "char"), Answer(2, "boolean"), Answer(3, "int"), Answer(4, "All of the above"))
      val question5 = Question(5, "Which is not a basic scala data type?", 20, ans5, 4)

      val ans6 = List(Answer(1, "5"), Answer(2, "6"))
      val question6 = Question(6, "5.6.toInt returns?", 10, ans6, 1)

      val ans7 = List(Answer(1, "Char"), Answer(2, "Boolean"), Answer(3, "int"), Answer(4, "Double"))
      val question7 = Question(7, "Which is not a basic scala data type?", 30, ans7, 3)
      intest.createQuestionList should equal(Game(players, None, 0, 0, 0, List(question1, question2, question3, question4, question5, question6, question7)))
    }
  }

  "A Game" when {
    "unapplied should have arguments" in {
      Game.unapply(intest).get should be((players, None, 0, 0, 0, List()))
    }

    "applied should accept the arguments" in {
      Game.apply(players, currentPlayer, currentPlayerIndex, maxRoundNr, currentRoundNr, questionList) should be(intest)
    }
  }

  "A Game" can {
    "start" in {
      intest.start
    }

    "can get a next question" in {
      intest.nextQuestion(player)
    }
  }
}