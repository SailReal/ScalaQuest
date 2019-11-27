package de.scala_quest.view.gui

import de.scala_quest.model.Player
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Pos
import scalafx.scene.control.Button
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.text.Text
import javafx.event.{ActionEvent, EventHandler}

class ResultStage(
                   players: List[Player],
                   backAction: () => Unit,
                   startNewGameAction: EventHandler[ActionEvent],
                   quitGameAction: EventHandler[ActionEvent],
                 ) extends PrimaryStage {

  title.value = "ScalaQuest Result"
  width = 1024
  height = 768

  scene = new Scene {
    fill = White
    stylesheets += "styles.css"

    root = new VBox {
      styleClass += "result"

      val headline: Text = new Text {
        text = "Result"
        styleClass += "headline"
        // add effect to css
        effect = new DropShadow {
          color = DodgerBlue
          radius = 25
          spread = 0.25
        }
      }
      children += headline

      val playerContainer: VBox = new VBox {
        styleClass += "player-results"

        players.foreach { p => {
          val singlePlayerContainer: VBox = new VBox {
            styleClass += "player-result"

            val player: Text = new Text {
              text = p.name
              styleClass += "player-name"
            }
            children += player

            val points: Text = new Text {
              text = "Points: " + p.points
              styleClass += "player-points"
            }
            children += points

            val correctContainer: VBox = new VBox {
              styleClass += "correct-container"

              if (p.correctAnswers.nonEmpty) {
                val correctText = new Text {
                  text = "Correctly answered questions:"
                  styleClass += "correct-text"
                }
                children += correctText

                p.correctAnswers.foreach(q => {
                  val correctQuestion = new Text {
                    text = q.text
                    styleClass += "correct-answer"
                  }
                  children += correctQuestion
                })
              }
            }
            children += correctContainer

            val wrongContainer: VBox = new VBox {
              styleClass += "wrong-container"

              if (p.wrongAnswers.nonEmpty) {
                val wrongText = new Text {
                  text = "Wrongly answered questions:"
                  styleClass += "wrong-text"
                }
                children += wrongText

                p.wrongAnswers.foreach(q => {
                  val correctAnswer = q.answers.find(a => a.id == q.correctAnswer).get
                  val answerText = new Text {
                    text = q.text + " (correct answer: " + correctAnswer.text + ")"
                    styleClass += "wrong-answer"
                  }
                  children += answerText
                })
              }
            }
            children += wrongContainer
          }
          children += singlePlayerContainer
        }}
      }
      children += playerContainer

      val player: Player = players.max[Player]{ case (p1: Player, p2: Player) => p1.points.compareTo(p2.points)}

      val winner: Text = new Text {
        text = "'" + player.name + "' won the game!"
        styleClass += "winner"
      }
      children += winner

      val actionButtonContainer = new HBox {
        styleClass += "add-player-stage-buttons-container"
        alignment = Pos.BottomRight // move to css

        // Create start game button
        val startGameButton: Button = new Button {
          text = "Start New Game"
          styleClass += "add-player-stage-buttons"
          //onAction = startNewGameAction
        }
        children += startGameButton

        // Create quit game button.
        val quitButton: Button = new Button {
          text = "Quit Game"
          styleClass += "add-player-stage-buttons"
          alignment = Pos.BottomRight
          onAction = quitGameAction
        }
        children += quitButton
      }
      children += actionButtonContainer

      /*val backButton: Button = new Button {
        text = "Back"
        onAction = backAction
        styleClass += "back-button"
      }
      children += backButton*/
    }
  }
}
