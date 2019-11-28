package de.scala_quest.model.defaultImpl

import de.scala_quest.model.{Game => GameTrait, Player => PlayerTrait, Question => QuestionTrait}

case class Game (
  players: List[PlayerTrait] = List(),
  currentPlayer: Option[PlayerTrait] = Option.empty,
  currentPlayerIndex: Int = 0,
  maxRoundNr: Int = 0,
  currentRoundNr: Int = 0,
  questionList: List[Question] = List(),
) extends GameTrait {

  override def addNewPlayer(newPlayer: PlayerTrait): Game = copy(players = players :+ newPlayer)

  override def removePlayer(player: PlayerTrait): Game = copy(players = players.filter(_.name != player.name))

  override def updatePlayer(player: PlayerTrait): Game = {
    val updatedPlayers = players.map(play => if(play.name == player.name) {
      player
    } else {
      play
    })

    copy(players = updatedPlayers)
  }

  override def playerCount(): Int = players.size

  override def updateRoundNr: GameTrait = copy(currentRoundNr = currentRoundNr + 1)

  override def updateState() : Game = {
    val playerIndex = currentPlayerIndex + 1
    if (playerIndex < players.size) {
      copy(currentPlayer = players.lift(playerIndex), currentPlayerIndex = playerIndex)
    } else {
      copy(currentPlayer = players.lift(0), currentPlayerIndex = 0, currentRoundNr = currentRoundNr + 1)
    }
  }

  override def createQuestionList: Game = {
    // TODO: read questions from JSON file
    val ans1 = List(Answer(1, "True"), Answer(2, "False"))
    val question1 = Question(1, "Every value in Scala is an object. True or False?", 10, ans1, 1)

    val ans2 = List(Answer(1, "var"), Answer(2, "val"))
    val question2 = Question(2, "Mutable variables start with the keyword", 10, ans2, 1)

    val ans3 = List(Answer(1, "1, 2, 3, 4, 5, 6"), Answer(2, "1, 2, 3, 4, 5, 6, 7"))
    val question3 = Question(3, "In scala, the expression '1 to 7' returns a range from", 10, ans3, 2)

    val ans4 = List(Answer(1, "(String, Int, Char)"), Answer(2, "((String, Int), Char)"))
    val question4 = Question(4, "The expression \"Hello\" -> 42 -> 'c' is an instance of", 60, ans4, 2)

    copy(questionList = List(question1, question2, question3, question4))
  }

  override def start: Game = {
    val currPlayer = players.lift(0)
    copy(currentPlayer = currPlayer, currentPlayerIndex = 0, maxRoundNr = 4, currentRoundNr = 1)
  }

  override def nextQuestion(player: PlayerTrait): GameTrait = {
    val updatedPlayer = player.nextQuestion()
    val updatedPlayers = players.map(play => if(play.name == player.name) {
      updatedPlayer
    } else {
      play
    })

    copy(players = updatedPlayers, currentPlayer = Option(updatedPlayer))
  }
}
