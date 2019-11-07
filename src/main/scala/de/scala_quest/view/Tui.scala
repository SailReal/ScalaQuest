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
        handleMenuInput(line)
      } else if(startGame) {
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
    logger.info("[m] Start new game (Multi-player)")
    logger.info("[q] Quit game")
    logger.info("")
  }

  def displayMultiPlayerMenu(): Unit = {
    logger.info("[a] Add player")
    logger.info("[r] Remove player")
  }

  def multiplayerMenu(): Unit = {
    logger.info("")
    logger.info("Multi-player mode")
    //displayMultiPlayerMenu()
    logger.info("Enter a comma separated list of names, (e.g. Foo, Bar, Baz)")
    val playerNames = input.readLine()
    logger.info("Welcome")
    var names = ""
    playerNames.split(", ").foreach(n => names = names.concat(n + " "))
    logger.info(names)
  }

  /** Handles the keyboard input in relation to the main menu options.
   *
   * @param line the text the user entered via the command line
   */
  protected def handleMenuInput(line: String): Unit = {
    line match {
      case "q" => onQuit()
      case "n" => {
        controller.newGame() // Start new game, create questionList.
        addPlayerToGame()
      }
      case "m" => {
        controller.newGame()
        multiplayerMenu()
      }
      //case answer if answer.matches("\\d") => controller.onAnswerChosen(answer.toInt)
      //case _ => logger.info("Unknown command")
    }
  }

  /** Adds a new player to the game. Allows the player to enter his nickname. */
  def addPlayerToGame(): Unit = {
    logger.info("Enter Nickname")
    val playerNickname = input.readLine()
    controller.addNewPlayerToGame(playerNickname)
    displayMenu = false
    startGame = true
    displayGame()
  }

  /** Displays the game's current player with his/her question and respective answers. */
  protected def displayGame(): Unit = {
    val currentPlayer = controller.getCurrentPlayersName()
    logger.info("")
    logger.info(currentPlayer)
    val question = controller.getPlayersCurrentQuestion()
    question match {
      case Some(q) => logger.info("Question: " + q)
      case None => {
        logger.info(s"Player '$currentPlayer' has no more questions left...")
        displayGameResults()
      }
    }
    controller.getPlayersCurrentAnswers.zipWithIndex.foreach {
      case (answer, i) => logger.info((i + 1) + ") " + answer)
    }
    logger.info("[q] Quit game")
    logger.info("")
  }

  protected def displayGameResults(): Unit = {
    println("")
    logger.info("")
    logger.info("Game results")
    logger.info("")
    onQuit()
  }

  /** Handles the keyboard input in relation to the game menu options.
   *
   * @param line the user's input via the command line
   */
  protected def handleGameInput(line: String): Unit = {
    line match {
      case "1" => controller.processAnswer(1)
      case "2" => controller.processAnswer(2)
      case "3" => controller.processAnswer(3)
      case "4" => controller.processAnswer(4)
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