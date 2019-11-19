package de.scala_quest.view.gui

import de.scala_quest.model.Question
import javafx.scene.input.KeyCode
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._
import scalafx.scene.text.Text

class GameStage(
    question: Question,
    allowMouseInput: Boolean,
    onInput: Function[Int, Unit]
) extends PrimaryStage {

    title.value = "ScalaQuest Game"
    width = 640
    height = if (allowMouseInput) 480 else 520

    scene = new Scene {
        fill = White
        stylesheets.add("styles.css")

        root = new VBox {
            styleClass += "game"

            val questionProp: Text = new Text {
                text = question.text
                styleClass += "headline"
            }

            children.add(questionProp)

            val answerBox: VBox = new VBox {
                styleClass += "answer-container"

                question.answers.zipWithIndex.foreach { case (ans, i) =>
                    val btn = new Button {
                        styleClass += "answer-button"

                        text = "Answer " + (i + 1) + ": " + ans.text
                        if (allowMouseInput) {
                            onAction = _ => onInput(ans.id)
                        }
                    }

                    children.add(btn)
                }
            }
            children.add(answerBox)

            if (!allowMouseInput) {
                val warning = new Text {
                    text = "Mouse input not allowed, use keyboard instead"
                    fill = Color.Red
                }

                children.add(warning)
            }
        }

        onKeyReleased = { e => {
            e.getCode match {
                case KeyCode.DIGIT1 => onInput(1)
                case KeyCode.DIGIT2 => onInput(2)
                case KeyCode.DIGIT3 => onInput(3)
                case KeyCode.DIGIT4 => onInput(4)
                case KeyCode.DIGIT6 => onInput(6)
                case KeyCode.DIGIT7 => onInput(7)
                case KeyCode.DIGIT8 => onInput(8)
                case KeyCode.DIGIT9 => onInput(9)
                case _ =>
            }
        }}
    }
}
