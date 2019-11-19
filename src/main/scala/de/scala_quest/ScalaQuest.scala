package de.scala_quest

import java.util.concurrent.CountDownLatch

import de.scala_quest.controller.Controller
import com.google.inject.Guice
import de.scala_quest.view.Tui
import de.scala_quest.view.gui.Gui

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

object ScalaQuest {
  def main(args: Array[String]): Unit = {
    // Create injector instance
    val injector = Guice.createInjector(new ScalaQuestModule())
    val controller = injector.getInstance(classOf[Controller])

    val latch = new CountDownLatch(1)

    // run GUI on its own thread
    Future {
      val gui = new Gui(controller, latch)
      gui.main(Array())
    }

    // wait for initialization of JFXApp to be done
    latch.await()

    val tui = new Tui(controller)
  }
}
