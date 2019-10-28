package de.scala_duel.model.defaultImpl

import org.scalatest.WordSpec
import org.scalatest._
import Matchers._

class AnswerTest extends WordSpec {

  final var id = 1
  final var text = "Foo"

  final val intest = Answer(id, text)


  "Answer" should  {
    "have a text" in {
      intest.text should equal(text)
    }

    "have a id" in {
      intest.id should equal(id)
    }
  }
}