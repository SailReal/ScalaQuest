package de.scala_quest

import com.google.inject.AbstractModule
import de.scala_quest.controller.Controller
import de.scala_quest.controller.defaultImpl.{Controller => ControllerImpl}
import de.scala_quest.model.{Game, Player}
import de.scala_quest.model.defaultImpl.{Game => GameImpl, Player => PlayerImpl}
import net.codingwell.scalaguice.ScalaModule

class ScalaQuestModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    // map our traits to  their implementations
    bind(classOf[Controller]).to(classOf[ControllerImpl])
    bind(classOf[Game]).toInstance(GameImpl(List()))
  }
}
