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
}
