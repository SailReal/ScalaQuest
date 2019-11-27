package de.scala_quest.view.gui

import javafx.event.{ActionEvent, EventHandler}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.text.Text
import scalafx.Includes._

class GameStage2(
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
          text = "[" + playersP + " pts]"
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
          text = question
          styleClass += "question"
        }
        children += questionLabel

        val answerContainer: VBox = new VBox {
          styleClass += "answer-container"
          answers.zipWithIndex.foreach { case (ans, i) =>
            val btn = new Button {
              styleClass += "answer-button"
              text = "" + (i + 1) + ") " + ans
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
        text = "Quit Application"
        tooltip = "Click me to exit the application"
        styleClass += "single-player-button"
        alignment = Pos.CenterRight
        onAction = quitGameAction
      }
      children += quitButton

    }
  }
}
