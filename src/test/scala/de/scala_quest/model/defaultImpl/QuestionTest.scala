package de.scala_quest.model.defaultImpl

import org.scalatest.WordSpec
import org.scalatest._
import Matchers._

class QuestionTest extends WordSpec {

  final val text = "Foo"
  final val id = 1
  final val points = 215
  final val answers:List[Answer] = List()
  final val correctAnswer = 2

  final val intest = Question(id, text, points, answers, correctAnswer)

  "Question" should  {
    "have a text" in {
      intest.text should equal(text)
    }

    "have a id" in {
      intest.id should equal(id)
    }

    "have points" in {
      intest.points should equal(points)
    }

    "have answers" in {
      intest.answers should equal(answers)
    }

    "have a correct answer" in {
      intest.correctAnswer should equal(correctAnswer)
    }
  }

  "A Question" when {
    "unapplied should have arguments in" in {
      Question.unapply(intest).get should be((id, text, points, answers, correctAnswer))
    }
  }
}
