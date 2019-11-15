package de.scala_quest.model.defaultImpl

import java.security.InvalidParameterException

import org.scalatest.WordSpec
import org.scalatest._
import Matchers._

class PlayerTest extends WordSpec {

  final val name = "Foo"
  final val points = 25
  final val questionIndex = 3
  final val questions = List()
  final val correctAnswers = List()
  final val wrongAnswers = List()
  final val currentQuestion = None

  final val intest = Player(name, points, questionIndex, questions, correctAnswers, wrongAnswers, currentQuestion)

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

    "throw an InvalidParameterException if no name provided" in {
      intercept[InvalidParameterException] {
        val player = Player("", points, questionIndex, questions, correctAnswers, wrongAnswers, currentQuestion)
      }
    }

    "display the name" in {
      intest.toString should be(intest.name)
    }

    "update the player when providing a correct answer" in {
      val currentQuestionPoints = 10

      val question = Question(1, "foo", currentQuestionPoints, List(), 2)
      val game = intest.correctAnswer(question)

      game.points should be(points + currentQuestionPoints)
      game.correctAnswers should be(List(question))
      game.wrongAnswers should be(List())
    }

    "update the player when providing a wrong answer" in {
      val currentQuestionPoints = 10

      val question = Question(1, "foo", currentQuestionPoints, List(), 2)
      val game = intest.wrongAnswer(question)

      game.points should be(points)
      game.correctAnswers should be(List())
      game.wrongAnswers should be(List(question))
    }

    "update the player to the next question" in {
      val player = intest.nextQuestion()
    }
  }

  "A Player" when {
    "unapplied should have arguments" in {
      Player.unapply(intest).get should be((name, points, questionIndex, questions, correctAnswers, wrongAnswers, currentQuestion))
    }
  }

  "A Player" when {
    "applied should accept the arguments" in {
      Player.apply(name, points, questionIndex, questions, correctAnswers, wrongAnswers, currentQuestion) should be(intest)
    }
  }


}
