package de.scala_quest.view.gui

import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.control.ButtonType
import javafx.scene.input.KeyCode
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.Platform
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, Label, TextField, TextInputDialog}
import scalafx.scene.layout._
import scalafx.scene.text.Text

class AddPlayersStage(
        quitGameAction: EventHandler[ActionEvent],
        playerInfo: List[String],
        playerAddAction: Function[String, Unit],
        playerRemoveAction: Function[String, Unit],
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
        alignment = Pos.Center // TODO: get this to work in style.css
      }
      Platform.runLater(headLine.requestFocus())
      children += headLine

      val displayPlayerNamesContainer: VBox = new VBox {
        alignment = Pos.CenterLeft
        val playerNameLabel = new Label {
          text = "Players:"
          style = "-fx-font-size: 36pt; -fx-padding: 0 0 0 135"
        }
        children += playerNameLabel

        val currentPlayerNamesContainer: VBox = new VBox {
          styleClass += "player-names-container"
          playerInfo.map(player => {
            val playerNameAndRemoveContainer = new HBox {
              styleClass += "player-names-container"
              val playerText = new Text {
                text = player.toString
                style = "-fx-font-size: 24pt"
              }
              children += playerText
              val removeButton = new Button {
                text = "X"
                tooltip = "Click me to remove player"
                styleClass += "remove-player-button"
                onAction = (event: ActionEvent) => playerRemoveAction(playerText.getText)
              }
              children += removeButton
            }
            children += playerNameAndRemoveContainer
          })

        }
        children += currentPlayerNamesContainer
      }
      children += displayPlayerNamesContainer


      // Create container, which contains a text field to write player names and a button to add player to game
      val addPlayerContainer: HBox = new HBox {
        styleClass += "add-player-container"
        val textField = new TextField {
          promptText = "Insert player name"
          style = "-fx-font-size: 18pt"
        }

        def getName(): String = {
          var enteredName  = textField.getText()

          if (enteredName.isEmpty) {
            showDialog("A Player must have a name!")
          } else if (playerInfo.contains(enteredName)) {
              showDialog(s"A Player with name: '${enteredName}' already exists!")
          }

          def showDialog(hText: String): String = {
            val dialog = new TextInputDialog() {
              title = "Insert player name"
              headerText = hText
              contentText = "Insert player name"
            }
            dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true)
            val result = dialog.showAndWait()
            result match {
              case Some(name) => {
                if (playerInfo.contains(name)) {
                  showDialog(s"A Player with name: '${name}' already exists!")
                } else {
                  enteredName = name
                }
              }
            }
            enteredName
          }

          textField.setText("") // clear textField back to prompt Text
          enteredName
        }

        val addPlayerButton = new Button {
          text = "_Add Player"
          styleClass += "add-player-button"
          mnemonicParsing = true
          onAction = (event: ActionEvent) => playerAddAction(getName())
        }
        onKeyPressed = { e => {
          e.getCode match {
            case KeyCode.ENTER => playerAddAction(getName())
            case _ =>
          }
        }}
        children += textField
        children += addPlayerButton
      }
      children += addPlayerContainer

      val actionButtonContainer = new HBox {
        styleClass += "add-player-stage-buttons-container"
        alignment = Pos.BottomRight // move to css

        val startGameButton: Button = new Button {
          text = "_Start Quest"
          tooltip = "Click me to start quest"
          styleClass += "add-player-stage-buttons"
          mnemonicParsing = true
          onAction = startGameAction
        }
        if(playerInfo.size == 0){
          startGameButton.setDisable(true)
        }
        children += startGameButton

        val quitButton: Button = new Button {
          text = "_Quit Game"
          tooltip = "Click me to exit the application"
          styleClass += "add-player-stage-buttons"
          alignment = Pos.BottomRight
          mnemonicParsing = true
          onAction = quitGameAction
        }
        children += quitButton
      }
      children += actionButtonContainer
    }
  }

}
