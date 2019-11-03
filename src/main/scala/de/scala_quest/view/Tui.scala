package de.scala_quest.view

import java.io.BufferedReader

import de.scala_quest.controller.Controller

class Tui(controller: Controller) extends Ui {
  val input = new BufferedReader(Console.in)

  while(true) {
    if (input.ready()) {
      val line = input.readLine()
      processInput(line)
    } else {
      Thread.sleep(200) // don't waste cpu cycles if no input is given
    }
  }

  protected def processInput(line: String): Unit = {
    line match {
      case "q" => controller.onQuit()
      case answer if answer.matches("\\d") => controller.onAnswerChosen(answer.toInt)
      //case _ => logger.info("Unknown command")
    }
  }
}
