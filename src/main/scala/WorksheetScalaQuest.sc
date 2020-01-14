import de.scala_quest.model.defaultImpl.{Answer, Game, Player, Question}
import de.scala_quest.model.defaultImpl.Answer

val test: Int = 1
val a: Int = test + 1


val answer = Answer(1, "Foo")
val question = Question(1, "Foo", 215, List(answer), 1, 21)
val player = Player("Foo", 23, List(question), List.empty)
val player2 = Player("Bar", 0, List(question), List.empty)
val game = Game(List(player, player2))

print("answer")
answer.id
answer.text

print("question")
question.id
question.text
question.points
question.answers
question.correctAnswer
question.time

print("player")
player.name
player.points
player.wrongAnswers
player.correctAnswers


print("game")
game.players



val test = "STring"