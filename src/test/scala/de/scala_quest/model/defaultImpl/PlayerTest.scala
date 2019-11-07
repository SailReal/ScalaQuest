package de.scala_quest.model.defaultImpl

import org.scalatest.WordSpec
import org.scalatest._
import Matchers._

class PlayerTest extends WordSpec {

  final val name = "Foo"
  final val points = 25
  final val questionIndex = 3
  final val correctAnswers = List()
  final val wrongAnswers = List()

  final val intest = Player(name, points, questionIndex, correctAnswers, wrongAnswers)

  "Player" should  {
    "have a name" in {
      intest.name should equal(name)
    }

    "have points" in {
      intest.points should be(points)
    }

    "have questionIndex" in {
      intest.questionIndex should be(questionIndex)
    }

    "have correct answers" in {
      intest.correctAnswers should be(correctAnswers)
    }

    "have wrong answers" in {
      intest.wrongAnswers should be(wrongAnswers)
    }
  }

}
