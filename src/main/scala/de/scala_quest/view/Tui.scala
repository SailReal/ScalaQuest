package de.scala_quest.view

import java.io.BufferedReader

import com.typesafe.scalalogging.LazyLogging
import de.scala_quest.controller.Controller
import de.scala_quest.view.Ui

class Tui (controller: Controller) extends Ui with LazyLogging {
  // controller.addObserver(this)
  val input = new BufferedReader(Console.in)
  var quit = false
  var startGame = false
  var displayMenu = true

  displayMainMenu()

  while(!quit) {
    if (input.ready()) {
      val line = input.readLine()
      if (displayMenu) {
        println(displayMenu)
        handleMenuInput(line)
      } else if(startGame) {
        println(startGame)
        handleGameInput(line)
        displayGame()
      }
    } else {
      Thread.sleep(200) // don't waste cpu cycles if no input is given
    }
  }

  /** Displays the game's main menu. */
  def displayMainMenu(): Unit = {
    logger.info("")
    logger.info("Welcome to ScalaQuest")
    logger.info("Choose ...")
    logger.info("[n] Start new game (Single player)")
    logger.info("[m] Start new game (Multi-player")
    logger.info("[q] Quit game")
    logger.info("")
  }

  /** Handles the keyboard input in relation to the main menu options.
   *
   * @param line the text the user entered via the command line
   */
  protected def handleMenuInput(line: String): Unit = {
    line match {
      case "q" => onQuit()
      case "n" => {
        controller.newGame()
        // Get player information from user
        getPlayerInfo()
      }
      case answer if answer.matches("\\d") => controller.onAnswerChosen(answer.toInt)
      //case _ => logger.info("Unknown command")
    }
  }

  /** Allows the player to enter his nickname and appoints his/her questions. */
  def getPlayerInfo(): Unit = {
    logger.info("Enter Nickname")
    val playerName = input.readLine()
    controller.createPlayer(playerName)
    displayMenu = false
    startGame = true
    displayGame()
  }

  /** Displays the game's current player with his/her question and respective answers. */
  protected def displayGame(): Unit = {
    logger.info("")
    logger.info("get current playerName")
    logger.info("get current player's Question x: ")
    logger.info("get answers to the above question")
    logger.info("Answer 2")
    logger.info("Answer 3")
    logger.info("Answer 4")
    logger.info("[q] Quit game")
    logger.info("")
  }

  /** Handles the keyboard input in relation to the game menu options.
   *
   * @param line the user's input via the command line
   */
  protected def handleGameInput(line: String): Unit = {
    line match {
      case "1" => logger.info("selected answer 1")
      case "2" => logger.info("selected answer 2")
      case "3" => logger.info("selected answer 3")
      case "4" => logger.info("selected answer 4")
      case "q" => onQuit()
      case _ => logger.info("Not a valid command")
    }
  }

  /** Ensures for a clean application exit. */
  def onQuit(): Unit = {
    quit = true
    displayMenu = false
    startGame = false
    logger.info("Exiting Application")
    controller.onQuit()
  }

}