package de.scala_quest

import de.scala_quest.controller.Controller
import com.google.inject.Guice
import de.scala_quest.view.Tui
import de.scala_quest.view.gui.Gui

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object ScalaQuest {
  def main(args: Array[String]): Unit = {
    // Create injector instance
    val injector = Guice.createInjector(new ScalaQuestModule())
    val controller = injector.getInstance(classOf[Controller])

    val gui = Future {new Gui(controller).main(Array())}
    val tui = Future {new Tui(controller)}

    Await.ready(tui, Duration.Inf)
    Await.ready(gui, Duration.Inf)
  }
}
