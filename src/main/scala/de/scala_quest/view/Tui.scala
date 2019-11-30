package de.scala_quest.view

import java.io.BufferedReader

import com.typesafe.scalalogging.LazyLogging
import de.scala_quest.{GameState, UpdateAction}
import de.scala_quest.controller.Controller

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
        line.split("\\s*(\\s|,)\\s*").foreach(name => controller.addNewPlayerToGame(name))

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

  /** Displays the game's current player with his/her question and respective answers. */
  private def displayGame(): Unit = {
    val currentRound = controller.getRoundNr()
    if(controller.checkGameRoundStatus) {
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
          displayGameResults()
      }
      controller.getPlayersCurrentAnswers().zipWithIndex.foreach {
        case (answer, i) => logger.info((i + 1) + ") " + answer)
      }
      logger.info("[q] Quit game")
      logger.info("")
    } else {
      logger.info("That was the final round, calculating results.")
      Thread.sleep(1250) // Create suspense :)
      displayGameResults()
    }
  }

  /** Displays the game's results. These results include the player names
   * with their respective points, correctly answered and wrongly answered
   * questions as well as the overall winner.
   */
  private def displayGameResults(): Unit = {
    val players = controller.getPlayers()
    println("")
    logger.info("")
    logger.info("GAME RESULTS")
    logger.info("")
    players.foreach(p => {
      logger.info(s"Name: ${p.name} -> ${p.points} pts")
      logger.info(s"Correctly answered questions: ${p.correctAnswers.size}")
      p.correctAnswers.foreach(q => {
        logger.info(s"    * ${q.text}")
      })
      logger.info(s"Wrongly answered questions: ${p.wrongAnswers.size}")
      p.wrongAnswers.foreach(q => {
        logger.info(s"  * ${q.text}. (correct ans: ${q.answers.find(a => a.id == q.correctAnswer).get.text})")
      })
      logger.info("")
    })

    if (controller.getPlayerCount() > 1) {
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
      logger.info(s"Winner(s): $winner")
      logger.info("")
    }
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

  override def update[T](updateData: GameState) : Function[T, Unit] = {
    updateData.action match {
      case UpdateAction.NEW_GAME => _ => logger.info(displayNewGameMenu())
      case UpdateAction.SHOW_GAME => _ => displayGame()
      case UpdateAction.PLAYER_UPDATE => _ => () // does nothing. TODO: delete?
      case UpdateAction.CLOSE_APPLICATION => _ => quit = true
      case UpdateAction.DO_NOTHING => _ => logger.info(displayNewGameMenu())// does nothing. TODO: delete?
      case UpdateAction.SHOW_RESULT => _ => {
        logger.info("That was the final round, calculating results.")
        Thread.sleep(1250) // Create suspense :)
        displayGameResults()
      }
      case _ => _ => ()
    }
  }
}