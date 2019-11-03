package de.scala_quest.model.defaultImpl

import org.scalatest.Matchers._
import org.scalatest.WordSpec

class GameTest extends WordSpec {

  final val players:List[Player] = List()
  final val questions:List[Question] = List()

  final val intest = Game(players, questions)

  "Game" should  {
    "have players" in {
      intest.players should equal(players)
    }

    "have questions" in {
      intest.questions should equal(questions)
    }
  }
}
