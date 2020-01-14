package de.scala_quest.view.gui

import javafx.event.{ActionEvent, EventHandler}
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout._
import scalafx.scene.paint.Color._
import scalafx.scene.text.Text

class MainMenuStage(
                 startNewGameAction: EventHandler[ActionEvent],
                 quitGameAction: EventHandler[ActionEvent],
                 playerInfo: (List[String], Option[String]),
                 playerAddAction: Function[String, Unit] // remove
               ) extends PrimaryStage {

  title.value = "ScalaQuest Main Menu"
  resizable = false
  width = 1024
  height = 768

  scene = new Scene {
    fill = White
    stylesheets += "styles.css"

    root = new VBox {
      styleClass += "menu"

      val headLine: Text = new Text {
        text = "ScalaQuest"
        styleClass += "headline"
      }
      children += headLine // add headline to scene

      val mainMenuButtonsContainer = new VBox {
        styleClass += "main-menu-buttons-container"
        val startNewGame: Button = new Button {
          text = "Start _New Game"
          onAction = startNewGameAction
          mnemonicParsing = true
          styleClass += "main-menu-buttons"
        }
        children += startNewGame

        val quitButton: Button = new Button {
          text = "_Quit Game"
          onAction = quitGameAction
          mnemonicParsing = true
          styleClass += "main-menu-buttons"
        }
        children += quitButton
      }
      children += mainMenuButtonsContainer
    }
  }
}