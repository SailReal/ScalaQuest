package de.scala_quest.view.gui

import de.scala_quest.{GameState, Observer, UpdateAction}
import de.scala_quest.controller.Controller
import de.scala_quest.model.{Player, Question}
import de.scala_quest.view.Ui
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.{JFXApp, Platform}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Gui (controller: Controller
           ) extends JFXApp with Ui with Observer with Runnable {
  controller.addObserver(this)

  this.stage = displayMainMenu()

  override def update(gameState: GameState): Unit = {
    Platform.runLater { () =>
      gameState.action match {
        case UpdateAction.NEW_GAME => this.stage = displayAddPlayersStage()
        case UpdateAction.CLOSE_APPLICATION => this.stage.close()
        case UpdateAction.PLAYER_UPDATE => this.stage = displayAddPlayersStage()
        case UpdateAction.SHOW_GAME =>
          val (name, points) = controller.getPlayerInfo()
          this.stage = displayGame(name, points)
        case UpdateAction.SHOW_RESULT => this.stage = displayResult(gameState.game.players)
        case _ => this.stage = displayAddPlayersStage()
      }
    }
  }

  def displayMainMenu(): PrimaryStage = {
    new MainMenuStage(
      _ => controller.newGame(),
      _ => controller.onQuit(),
      (controller.getPlayerNames(), controller.nextPlayerName()),
      name => controller.addNewPlayerToGame(name)
    )
  }

  def displayAddPlayersStage(): PrimaryStage = {
    new AddPlayersStage(
      _ => controller.onQuit(),
      controller.getPlayerNames(),
      nameToAdd => controller.addNewPlayerToGame(nameToAdd),
      nameToRemove => controller.removePlayer(nameToRemove),
      _ => controller.startGame(),
    )
  }

  def displayGame(playersName: String, playersPoints: String): PrimaryStage = {
    new GameStage(
      controller.getRoundNr(),
      playersName,
      playersPoints,
      controller.getPlayersCurrentQuestion().get,
      controller.getPlayersCurrentAnswers(),
      _ => controller.onQuit(),
      input => controller.processAnswer(input),
    )
  }

  def displayResult(players: List[Player]): PrimaryStage = {
    new ResultStage(
      players,
      () => (),
      _ => controller.startGame(),
      _ => controller.onQuit()
    )
  }

  override def run(): Unit = {
    this.stage = displayMainMenu()
  }
}
