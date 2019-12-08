package de.scala_quest.view.gui

import javafx.event.{ActionEvent, EventHandler}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Alert, Button, ButtonType, Label}
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.text.Text
import scalafx.Includes._
import scalafx.scene.control.Alert.AlertType

class GameStage(
                roundNr: Int,
                playersN: String,
                playersP: String,
                question: String,
                answers: List[String],
                quitGameAction: EventHandler[ActionEvent],
                processAnswerAction: Function[Int, Unit]
                ) extends PrimaryStage {

  title.value = "ScalaQuest"
  scene = new Scene {
    fill = White
    stylesheets += "styles.css"

    root = new VBox {
      styleClass += "game"

      // Display round information. Either 'Round x' or 'Final Round'
      val roundNrLabel = new Label {
        text = "Round " + roundNr
        styleClass += "round"

      }
      children += roundNrLabel // add roundLabel to scene

      // Display player's name and points
      val playersNameAndPointsContainer = new HBox {
        styleClass += "player-points-display"
        val playersName = new Label {
          text = playersN
        }
        val playersPoints = new Label {
          text = "(" + playersP + " pts)"
        }
        children += playersName
        children += playersPoints
      }
      children += playersNameAndPointsContainer

      // Display the current player's question and respective answers
      val QnAContainer = new VBox {
        spacing = 10
        alignment = Pos.Center
        val questionLabel = new Label {
          text = "\t" + question + "\t"
          styleClass += "question"
        }
        children += questionLabel

        val answerContainer: VBox = new VBox {
          styleClass += "answer-container"
          answers.zipWithIndex.foreach { case (ans, i) =>
            val btn = new Button {
              styleClass += "answer-button"
              text = "_" + (i + 1) + ") " + ans
              onAction = (event: ActionEvent) => processAnswerAction((i + 1))

            }
            children.add(btn)
          }
        }
        children.add(answerContainer)
      }
      children += QnAContainer

      // Create quit game button
      val quitButton: Button = new Button {
        text = "_Quit Application"
        mnemonicParsing = true
        tooltip = "Click me to exit the application"
        styleClass += "game-stage-quit-button"
        onAction = quitGameAction /*(event: ActionEvent) => {
            val ButtonTypeYes = new ButtonType("Yes")
            val alert = new Alert(AlertType.Confirmation) {
                title ="Confirmation Dialog"
                headerText = "Are you sure you want to quit the game?"
                contentText = "Choose an option"
                buttonTypes = Seq(ButtonTypeYes, ButtonType.Cancel)
            }
            val result = alert.showAndWait()

            result match {
                case Some(ButtonTypeYes) => quitGameAction
                case _ => quitGameAction
            }
        }*/

      }
      children += quitButton

    }
  }
}
