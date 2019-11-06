package de.scala_quest

import com.google.inject.Guice
import de.scala_quest.controller.Controller
import de.scala_quest.view.Tui

object ScalaQuest {
  def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector(new GuiceModule())
    val controller = injector.getInstance(classOf[Controller])

    new Tui(controller)
  }
}
