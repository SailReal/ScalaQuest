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

  displayMainMenu()

  override def update(gameState: GameState): Unit = {
    Platform.runLater { () =>
      gameState.action match {
        case UpdateAction.NEW_GAME => {
          println("gui.NEW_GAME")
        }
        case UpdateAction.CLOSE_APPLICATION => {
          println("gui.CLOSE_APPLICATION")
          this.stage.close()
        }
        case UpdateAction.PLAYER_UPDATE => displayAddPlayersStage()
        case UpdateAction.SHOW_GAME => {
          println("gui.show_game")
          val (name, points) = controller.getPlayerInfo()
          displayGame2(
            controller.getRoundNr(),
            name,
            points,
            controller.getPlayersCurrentQuestion().get,
            controller.getPlayersCurrentAnswers(),
          )
        }
        case UpdateAction.SHOW_RESULT => {
          displayResult(gameState.game.players)
        }
        case _ => {
          println("case _")
          displayAddPlayersStage
        }
      }
    }
  }

  def displayMainMenu(): Unit = {
    this.stage = new MainMenuStage(
      _ => {
        println("...")
        controller.newGame()
      },
      _ => {
        println("abc")
        controller.onQuit()
      },
      (controller.getPlayerNames(), controller.nextPlayerName()),
      name => controller.addNewPlayerToGame(name)
    )
  }

  def displayAddPlayersStage(): Unit = {
    this.stage = new AddPlayersStage(
      quitGameAction => controller.onQuit(),
      controller.getPlayerNames(),
      name => controller.addNewPlayerToGame(name),
      _ => controller.startGame(),
    )
  }

  def displayGame(question: Question, multiplayer: Boolean): Unit = {
    this.stage = new GameStage(
      question,
      !multiplayer,
      controller.processAnswer
    )
  }

  def displayGame2(roundNr: Int, playersName: String, playersPoints: String, question: String, answers: List[String]): Unit = {
    this.stage = new GameStage2(
      roundNr,
      playersName,
      playersPoints,
      question,
      answers,
      _ => controller.onQuit(),
      input => controller.processAnswer(input),
    )
  }

  def displayResult(players: List[Player]): Unit = {
    this.stage = new ResultStage(players, () => (), _ => controller.startGame(), _ => controller.onQuit())
  }
}
