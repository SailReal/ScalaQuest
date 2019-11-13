package de.scala_quest.view

import java.io.BufferedReader

import com.typesafe.scalalogging.LazyLogging
import de.scala_quest.GameState
import de.scala_quest.controller.Controller

class Tui (controller: Controller) extends Ui with LazyLogging {
  // controller.addObserver(this)
  val input = new BufferedReader(Console.in)
  var quit = false
  var startGame = false
  var displayMenu = true
  var nrOfRoundsWishedToPlay = 3

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
    logger.info("Choose and confirm entry with Enter...")
    logger.info("[n] Start new game")
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
        displayNewGameMenu()
      }
      //case answer if answer.matches("\\d") => controller.onAnswerChosen(answer.toInt)
      case _ => {
        logger.info("Unknown command, select either 'n' or 'q'")
        displayMainMenu()
      }
    }
  }

  /** Displays the new game menu where players are allowed to enter their names. */
  def displayNewGameMenu(): Unit = {
    logger.info("")
    logger.info("Enter player name(s), a comma or whitespace separated list. (For example: Foo, Bar, Baz)")
    val playerNames = input.readLine()
    logger.info("Welcome")
    // add each player to the game
    playerNames.split("\\s*(\\s|,)\\s*").foreach(name => controller.addNewPlayerToGame(name))
    displayPlayers()
    println("")
    displayMenu = false
    startGame = true
    controller.startGame()
    displayGame()
  }

  /** Displays the names of the current players within the game. */
  def displayPlayers(): Unit = {
    logger.info("Players: " + controller.getPlayerNames().mkString(", "))
  }

  /** Displays the game's current player with his/her question and respective answers. */
  protected def displayGame(): Unit = {
    val currentRound = controller.getRoundNr()
    if(currentRound <= nrOfRoundsWishedToPlay) {
      if (currentRound == nrOfRoundsWishedToPlay) {
        logger.info("Final Round!!!")
      } else {
        logger.info(s"Round: $currentRound")
      }
      val (name, points) = controller.getPlayerInfo()
      logger.info(s"$name ($points pts)")
      // FIXME: Doesnt need to be an Option anymore since roundNr is fixed.
      val question = controller.getPlayersCurrentQuestion()
      question match {
        case Some(q) => {
          val questionLength = q.size
          var separator = ""
          // +10 includes "Question:".length + \\s
          (1 to (questionLength + 10)).foreach(l => separator += "-")
          logger.info(separator)
          logger.info("Question: " + q)
        }
          // FIXME: could omit this
        case None => {
          logger.info(s"Player '$name' has no more questions left...")
          displayGameResults()
        }
      }
      controller.getPlayersCurrentAnswers.zipWithIndex.foreach {
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
  protected def displayGameResults(): Unit = {
    val players = controller.getPlayers()
    println("")
    logger.info("")
    logger.info("GAME RESULTS")
    logger.info("")
    players.map(p => {
      logger.info(s"Name: ${p.name} -> ${p.points} pts")
      logger.info(s"Correctly answered questions: ${p.correctAnswers.size}")
      p.correctAnswers.map(q => {
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

    onQuit()
    /*
    logger.info("")
    displayMenu = true
    startGame = false
    displayMainMenu()*/
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
    sys.exit(0) // FIXME
  }

  override def update(updateData: GameState): Unit = {

  }
}