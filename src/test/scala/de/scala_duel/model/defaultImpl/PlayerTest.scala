package de.scala_duel.model.defaultImpl

import org.scalatest.WordSpec
import org.scalatest._
import Matchers._

class PlayerTest extends WordSpec {

  final val name = "Foo"
  final val points = 25
  final val correctAnswers = List()
  final val wrongAnswers = List()

  final val intest = Player(name, points, correctAnswers, wrongAnswers)

  "Player" should  {
    "have a name" in {
      intest.name should equal(name)
    }

    "have points" in {
      intest.points should be(points)
    }

    "have correct answers" in {
      intest.correctAnswers should be(correctAnswers)
    }

    "have wrong answers" in {
      intest.wrongAnswers should be(wrongAnswers)
    }
  }

}
