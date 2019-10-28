package de.scala_duel.model.defaultImpl

import org.scalatest.WordSpec
import org.scalatest._
import Matchers._

class QuestionTest extends WordSpec {

  final val text = "Foo"
  final val id = 1
  final val points = 215
  final val answers:List[Answer] = List()
  final val correctAnswer = 2
  final val time = 21

  final val intest = Question(id, text, points, answers, correctAnswer, time)

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

    "have a time" in {
      intest.time should equal(time)
    }


  }
}
