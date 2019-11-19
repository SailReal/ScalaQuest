package de.scala_quest.model.defaultImpl

import org.scalatest.Matchers._
import org.scalatest.WordSpec

class GameTest extends WordSpec {

  final val players: List[Player] = List()

  final val intest = Game(players)

  "Game" should  {
    "have players" in {
      intest.players should equal(players)
    }

    "have a player count" in {
      intest.playerCount() should equal(0)
    }
  }

  "Game" can {
    "add a player" in {
      val player = Player("name", 0, 0, List())
      intest.addNewPlayer(player).playerCount() should equal(1)
    }

    "remove a player" in {
      val player = Player("Bar", 21, 0, List(), List.empty)
      val tmp = Game(List(player))
      tmp.removePlayer(player).players should equal(players)
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
      intest.createQuestionList should equal(Game(players, None, 0, 0, 0, List(question1, question2, question3, question4)))

    }
  }

  "An Answer" when {
    "unapplied should have arguments" in {
      Game.unapply(intest).get should be((players, None, 0, 0, 0, List()))
    }
  }


}
