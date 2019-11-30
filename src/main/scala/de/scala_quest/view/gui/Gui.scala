package de.scala_quest.view.gui

import de.scala_quest.{GameState, Observer, UpdateAction}
import de.scala_quest.controller.Controller
import de.scala_quest.model.{Player, Question}
import de.scala_quest.view.Ui
import scalafx.application.{JFXApp, Platform}

class Gui (controller: Controller
           ) extends JFXApp with Ui with Observer with Runnable {
  controller.addObserver(this)

  displayMainMenu()

  override def update[T](gameState: GameState) : Function[T, Unit] = {
      gameState.action match {
        case UpdateAction.NEW_GAME => _ => displayAddPlayersStage()
        case UpdateAction.CLOSE_APPLICATION => _ => this.stage.close()
        case UpdateAction.PLAYER_UPDATE => _ => displayAddPlayersStage()
        case UpdateAction.SHOW_GAME => _ =>
          val (name, points) = controller.getPlayerInfo()
          displayGame(name, points)
        case UpdateAction.SHOW_RESULT => _ => displayResult(gameState.game.players)
        case _ => _ => displayAddPlayersStage()
      }
  }

  def displayMainMenu(): Unit = {
    this.stage = new MainMenuStage(
      _ => controller.newGame(),
      _ => controller.onQuit(),
      (controller.getPlayerNames(), controller.nextPlayerName()),
      name => controller.addNewPlayerToGame(name)
    )
  }

  def displayAddPlayersStage(): Unit = {
    this.stage = new AddPlayersStage(
      _ => controller.onQuit(),
      controller.getPlayerNames(),
      nameToAdd => controller.addNewPlayerToGame(nameToAdd),
      nameToRemove => controller.removePlayer(nameToRemove),
      _ => controller.startGame(),
    )
  }

  def displayGame(playersName: String, playersPoints: String): Unit = {
    this.stage = new GameStage(
      controller.getRoundNr(),
      playersName,
      playersPoints,
      controller.getPlayersCurrentQuestion().get,
      controller.getPlayersCurrentAnswers(),
      _ => controller.onQuit(),
      input => controller.processAnswer(input),
    )
  }

  def displayResult(players: List[Player]): Unit = {
    this.stage = new ResultStage(
      players,
      () => (),
      _ => controller.startGame(),
      _ => controller.onQuit()
    )
  }

  override def run(): Unit = {
    displayMainMenu()
  }
}
