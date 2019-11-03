package de.scala_quest

import de.scala_quest.controller.defaultImpl.Controller
import de.scala_quest.view.Tui

object ScalaQuest {
  def main(args: Array[String]): Unit = {
    // TODO use GUICE to bind Controller and Tui to Impl
    new Tui(new Controller)
  }
}
