package de.scala_quest.model

trait Game {
  val players: List[Player]
  def addPlayer(player: Player): Game
  def removePlayer(player: Player): Game
  def playerCount(): Int
}
