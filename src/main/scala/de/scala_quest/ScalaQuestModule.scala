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
    val game = GameImpl(List())
    val controller = ControllerImpl(GameState(UpdateAction.CLOSE_APPLICATION, game));

    bind(classOf[Controller]).toInstance(controller)
    bind(classOf[Game]).toInstance(game)
  }
}
