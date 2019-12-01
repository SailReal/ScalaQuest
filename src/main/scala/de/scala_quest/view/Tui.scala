package de.scala_quest.view

import java.io.BufferedReader

import com.typesafe.scalalogging.LazyLogging
import de.scala_quest.{GameState, UpdateAction}
import de.scala_quest.controller.Controller
import de.scala_quest.model.Player

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Tui (controller: Controller) extends Ui with LazyLogging {

  controller.addObserver(this)
  val input = new BufferedReader(Console.in)
  var quit = false
  var startGame = false
  var displayMenu = true
  var nrOfRoundsWishedToPlay = 4

  var stopProcessingInput = false
  var inMenu = true
  var inGame = false

  logger.info(displayMainMenu())

  Future {
    while (!quit) {
      if (input.ready()) {
        val line = input.readLine()
        if (displayMenu) {
          handleMenuInput(line).apply()
        } else if (startGame) {
          handleGameInput(line).apply()
        }
      } else {
        Thread.sleep(200) // don't waste cpu cycles if no input is given
      }
    }
  }

  /** Displays the game's main menu. */
  private def displayMainMenu(): String = {
    "\nWelcome to ScalaQuest\nChoose and confirm entry with Enter...\n[n] Start New Game\n[q] Quit Game\n"
  }

  /** Handles the keyboard input in relation to the main menu options.
   *
   * @param line the text the user entered via the command line
   */
  private def handleMenuInput[T](line: String): Function[T, Unit] = {
    line match {
      case "q" => _ => controller.onQuit() // TODO onQuit() ?
      case "n" => _ => controller.newGame()
      case _ => _ => {
        line
          .split("\\s*(\\s|,)\\s*")
          .foreach(name => controller.addNewPlayerToGame(name))

        displayMenu = false
        startGame = true

        controller.startGame()
      }
    }
  }

  /** Displays the new game menu where players are allowed to enter their names. */
  private def displayNewGameMenu(): String = {
    "Enter player name(s), a comma or whitespace separated list. (For example: Foo, Bar, Baz)"
  }

  // TODO refactore
  /** Displays the game's current player with his/her question and respective answers. */
  private def displayGame(): Unit = {
    val currentRound = controller.getRoundNr()
    if(controller.checkGameRoundStatus()) {
    //if(currentRound <= nrOfRoundsWishedToPlay) {
      if (currentRound == nrOfRoundsWishedToPlay) {
        logger.info("Final Round!!!")
      } else {
        logger.info(s"Round: $currentRound / $nrOfRoundsWishedToPlay")
      }
      val (name, points) = controller.getPlayerInfo()
      logger.info(s"$name ($points pts)")
      // FIXME: Doesnt need to be an Option anymore since roundNr is fixed.
      val question = controller.getPlayersCurrentQuestion()
      question match {
        case Some(q) =>
          val questionLength = q.length
          var separator = ""
          // +10 includes "Question:".length + \\s
          (1 to (questionLength + 10)).foreach(_ => separator += "-")
          logger.info(separator)
          logger.info("Question: " + q)
        case None =>
          logger.info(s"Player '$name' has no more questions left...")
          logger.info(displayGameResults())
      }
      controller.getPlayersCurrentAnswers().zipWithIndex.foreach {
        case (answer, i) => logger.info((i + 1) + ") " + answer)
      }
      logger.info("[q] Quit game")
      logger.info("")
    } else {
      logger.info("That was the final round, calculating results.")
      Thread.sleep(1250) // Create suspense :)
      logger.info(displayGameResults())
    }
  }

  /** Displays the game's results. These results include the player names
   * with their respective points, correctly answered and wrongly answered
   * questions as well as the overall winner.
   */
  private def displayGameResults(): String = {
    val players = controller.getPlayers()

    var stringBuilder = new StringBuilder
    stringBuilder = stringBuilder ++= "\nGAME RESULTS\n"
    stringBuilder = stringBuilder ++= highScore(players)
    stringBuilder = stringBuilder ++= winner(players)
    stringBuilder.toString()
  }

  private def highScore(players: List[Player]): String = {
    var stringBuilder = new StringBuilder

    players.foreach(p => {
      stringBuilder = stringBuilder ++= s"Name: ${p.name} -> ${p.points} pts\n"
      stringBuilder = stringBuilder ++= s"Correctly answered questions: ${p.correctAnswers.size}\n"
      p.correctAnswers.foreach(q => stringBuilder = stringBuilder ++= s"    * ${q.text}\n")
      stringBuilder = stringBuilder ++= s"Wrongly answered questions: ${p.wrongAnswers.size}\n"
      p.wrongAnswers.foreach(q => stringBuilder = stringBuilder ++= s"  * ${q.text}. (correct ans: ${q.answers.find(a => a.id == q.correctAnswer).get.text})\n")
    })

    stringBuilder.toString()
  }

  private def winner(players: List[Player]): String = {
    // Derive winner(s)
    val sortedAscList = players.sortBy(_.points)
    var winner: String = sortedAscList.last.name
    val highestScore = sortedAscList.last.points
    // Check if any other player has same high score
    sortedAscList.dropRight(1).foreach(p => {
      if (p.points == highestScore) {
        winner += s", ${p.name}"
      }
    })

    s"Winner(s): $winner"
  }

  private def handleGameInput[T](line: String): Function[T, Unit] = {
    line match {
      case "1" => _ => controller.processAnswer(1)
      case "2" => _ => controller.processAnswer(2)
      case "3" => _ => controller.processAnswer(3)
      case "4" => _ => controller.processAnswer(4)
      case "q" => _ => controller.onQuit()
      case _ => _ => logger.info("Not a valid command")
    }
  }

  def update(gameState: GameState): Unit = {
      gameState.action match {
        case UpdateAction.NEW_GAME => logger.info(displayNewGameMenu())
        case UpdateAction.SHOW_GAME => displayGame()
        case UpdateAction.PLAYER_UPDATE => () // does nothing. TODO: delete?
        case UpdateAction.CLOSE_APPLICATION => quit = true
        case UpdateAction.DO_NOTHING => logger.info(displayNewGameMenu())// does nothing. TODO: delete?
        case UpdateAction.SHOW_RESULT => {
          logger.info("That was the final round, calculating results.")
          Thread.sleep(1250) // Create suspense :)
          logger.info(displayGameResults())
        }
        case _ => ()
    }
  }
}