package de.scala_quest.model.defaultImpl

import de.scala_quest.model.{Game => GameTrait, Player => PlayerTrait}

case class Game (
  var players: List[PlayerTrait] = List(),
  var currentPlayer:PlayerTrait = null,
  var currentPlayerIndex: Int = 0,
  var maxRoundNr: Int = 0,
  var currentRoundNr: Int = 0,
  var questionList: List[Question] = List(),
) extends GameTrait {

  override def addNewPlayer(newPlayer: PlayerTrait): Unit = {
    // addPlayer parameter = name: String
    // create questionList for player
    players = players :+ newPlayer
  }

  override def removePlayer(player: PlayerTrait): Game = {
    Game(players.filter(_ != player))
  }

  override def playerCount(): Int = players.size

  override def updateState() : Unit = {
    currentPlayerIndex += 1
    if (currentPlayerIndex < players.size) {
      currentPlayer = players.lift(currentPlayerIndex).get
    } else {
      currentPlayerIndex = 0
      currentRoundNr += 1
      currentPlayer = players.lift(currentPlayerIndex).get
    }
  }

  override def createQuestionList(): Unit = {
    // TODO: read questions from JSON file
    val ans1 = List(Answer(1, "True"), Answer(2, "False"))
    val question1 = Question(1, "Every value in Scala is an object. True or False?", 10, ans1, 1, 10)

    val ans2 = List(Answer(1, "var"), Answer(2, "val"))
    val question2 = Question(2, "Mutable variables start with the keyword", 10, ans2, 1, 10)

    val ans3 = List(Answer(1, "1, 2, 3, 4, 5, 6"), Answer(2, "1, 2, 3, 4, 5, 6, 7"))
    val question3 = Question(3, "In scala, the expression '1 to 7' returns a range from", 10, ans3, 2, 10)

    questionList = List(question1, question2, question3)
  }

}
