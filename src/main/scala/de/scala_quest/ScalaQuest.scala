package de.scala_quest

import de.scala_quest.controller.Controller
import com.google.inject.Guice
import de.scala_quest.view.Tui

object ScalaQuest {
  def main(args: Array[String]): Unit = {
    // Create injector instance
    val injector = Guice.createInjector(new ScalaQuestModule())
    val controller = injector.getInstance(classOf[Controller])

    new Tui(controller)
  }
}
