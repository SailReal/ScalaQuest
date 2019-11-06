package de.scala_quest

import com.google.inject.AbstractModule
import de.scala_quest.controller.Controller
import de.scala_quest.model.Game


class GuiceModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[Controller]).to(classOf[de.scala_quest.controller.defaultImpl.Controller])
    bind(classOf[Game]).toInstance(de.scala_quest.model.defaultImpl.Game(List()))
  }
}