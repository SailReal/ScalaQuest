package de.scala_quest.view.gui

import java.util.concurrent.CountDownLatch

import de.scala_quest.{GameState, Observer, UpdateAction}
import de.scala_quest.controller.Controller
import de.scala_quest.model.{Player, Question}
import de.scala_quest.view.Ui
import scalafx.application.{JFXApp, Platform}

class Gui (controller: Controller, latch: CountDownLatch) extends JFXApp with Ui with Observer {
  controller.addObserver(this)

  // signal initialization finished
  latch.countDown()

  displayMenu()

  override def update(gameState: GameState): Unit = {
    Platform.runLater { () =>
      gameState.action match {
        case UpdateAction.NEW_GAME =>
          displayMenu()
          this.stage.onCloseRequest = { _ =>
            controller.onQuit()
          }
        case UpdateAction.CLOSE_APPLICATION => this.stage.close()
        case UpdateAction.PLAYER_UPDATE => displayMenu()
        case UpdateAction.SHOW_GAME =>
          displayGame(
            gameState.game.currentPlayer.get.currentQuestion.get,
            gameState.game.players.length > 1
          )
        case UpdateAction.SHOW_RESULT => displayResult(gameState.game.players)
        case _ =>
      }
    }
  }

  def displayMenu(): Unit = {
    this.stage = new MenuStage(
      _ => controller.startGame(),
      (controller.getPlayerNames(), controller.nextPlayerName()),
      name => controller.addNewPlayerToGame(name)
    )
  }

  def displayGame(question: Question, multiplayer: Boolean): Unit = {
    this.stage = new GameStage(
      question,
      !multiplayer,
      controller.processAnswer
    )
  }

  def displayResult(players: List[Player]): Unit = {
    this.stage = new ResultStage(players, () => ())
  }
}
