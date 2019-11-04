import de.scala_quest.model.defaultImpl.{Answer, Game, Player, Question}

val answer = Answer(1, "Foo")
val question = Question(1, "Foo", 215, List(answer), 1, 21)
val player = Player("Foo", 23, List(question), List.empty)
val game = Game(List(player))

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

val tmpPlayer = Player("Bar", 21, List(question), List.empty)
game.addPlayer(tmpPlayer)