package de.scala_quest.model.defaultImpl

import java.security.InvalidParameterException

import org.scalatest.WordSpec
import org.scalatest._
import Matchers._

class PlayerTest extends WordSpec {

  final val name = "Foo"
  final val points = 0
  final val questionIndex = 0
  val ans1 = List(Answer(1, "True"), Answer(2, "False"))
  val question1 = Question(1, "Every value in Scala is an object. True or False?", 10, ans1, 1)

  val ans2 = List(Answer(1, "var"), Answer(2, "val"))
  val question2 = Question(2, "Mutable variables start with the keyword", 10, ans2, 1)
  final val questions = List(question1, question2)
  final val correctAnswers = List()
  final val wrongAnswers = List()
  final val currentQuestion = None

  final val intest = Player(name, points, questionIndex, questions, correctAnswers, wrongAnswers, currentQuestion)

  "Player" should  {
    "have a name" in {
      intest.name should be(name)
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
        Player("", points, questionIndex, questions, correctAnswers, wrongAnswers, currentQuestion)
      }
    }

    "display the name" in {
      intest.toString should be(intest.name)
    }

    "update the player when providing a correct answer" in {
      val currentQuestionPoints = intest.questions(questionIndex).points
      val currentQuestion = intest.questions(questionIndex)
      val player1 = intest.correctAnswer(currentQuestion)

      player1.points should be(points + currentQuestionPoints)
      player1.correctAnswers should be(List(currentQuestion))
      player1.correctAnswers.length should be(1)
      player1.questionIndex should be(1)
      player1.wrongAnswers should be(List())
    }

    "update the player when providing a wrong answer" in {
      val currentQuestionPoints = 10

      val question = Question(1, "foo", currentQuestionPoints, List(), 2)
      val game = intest.wrongAnswer(question)

      game.points should be(points)
      game.correctAnswers should be(List())
      game.wrongAnswers should be(List(question))
      game.questionIndex should be (1)
    }

    "update the player to the next question" in {
      intest.currentQuestion should not be(intest.nextQuestion())
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