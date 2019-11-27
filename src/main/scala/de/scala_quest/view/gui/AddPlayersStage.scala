package de.scala_quest.view.gui

import javafx.event.{ActionEvent, EventHandler}
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.Platform
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.DepthTest.Disabled
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout._
import scalafx.scene.paint.Color._
import scalafx.scene.text.Text

class AddPlayersStage(
        quitGameAction: EventHandler[ActionEvent],
        playerInfo: List[String],
        playerAddAction: Function[String, Unit],
        startGameAction: EventHandler[ActionEvent],
     ) extends PrimaryStage {

  title.value = "ScalaQuest Add Players Menu"
  resizable = false
  width = 1024
  height = 768

  scene = new Scene {
    stylesheets += "styles.css"

    root = new VBox {

      val headLine: Text = new Text {
        text = "ScalaQuest"
        styleClass += "headline"
        alignment = Pos.Center
      }
      Platform.runLater(headLine.requestFocus())
      children += headLine

      val displayPlayerNamesContainer: VBox = new VBox {
        alignment = Pos.CenterLeft
        val playerNamesLabel = new Label {
          text = "Players:"
          style = "-fx-font-size: 36pt; -fx-padding: 0 0 0 135"
        }
        children += playerNamesLabel

        val currentPlayerNamesContainer: VBox = new VBox {
          styleClass += "player-names-container"
          spacing = 15
          padding = Insets(10)
          playerInfo.map(player => {
            val playerNameAndRemoveContainer = new HBox {
              styleClass += "player-names-container"
              spacing = 10
              val playerText = new Text {
                text = player.toString
                style = "-fx-font-size: 24pt"
              }
              children += playerText
              val removeButton = new Button {
                text = "X"
                tooltip = "Click me to remove player"
                styleClass += "remove-player-button"
              }
              children += removeButton
            }
            children += playerNameAndRemoveContainer
          })

        }
        children += currentPlayerNamesContainer
      }
      children += displayPlayerNamesContainer


      // Create container, which contains a text field to write player names and button to add player to game
      val addPlayerContainer: HBox = new HBox {
        alignment = Pos.Center
        spacing = 8
        padding = Insets(10)
        val textField = new TextField {
          promptText = "Insert player's name"
          style = "-fx-font-size: 18pt"
        }

        val addPlayerButton = new Button {
          text = "Add Player"
          styleClass += "add-player-button"
          onAction = (event: ActionEvent) => {
            val enteredName = textField.getText()
            textField.setText("") // clear textField back to promtText
            playerAddAction(enteredName)
          }
        }
        children += textField
        children += addPlayerButton
      }
      children += addPlayerContainer

      val actionButtonContainer = new HBox {
        styleClass += "add-player-stage-buttons-container"
        alignment = Pos.BottomRight // move to css

        val startGameButton: Button = new Button {
          text = "Start Quest"
          tooltip = "Click me to start quest"
          styleClass += "add-player-stage-buttons"
          onAction = startGameAction

        }
        if(playerInfo.size == 0){
          startGameButton.setDisable(true)
        }
        children += startGameButton

        val quitButton: Button = new Button {
          text = "Quit Game"
          tooltip = "Click me to exit the application"
          styleClass += "add-player-stage-buttons"
          alignment = Pos.BottomRight
          onAction = quitGameAction
        }
        children += quitButton
      }
      children += actionButtonContainer
    }
  }

}
