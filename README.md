# ScalaQuest

### About

ScalaQuest is a single as well as a multi-player game implemented in Scala.
ScalaQuest tests the users knowledge on all things about the Scala programming language.
It uses the Model-View-Controller (MVC) architecture which separates the application into
three main logical components, the model, the view and the controller. 

### Building the Application from source

#####  Clone the repository
```bash
git clone https://github.com/SailReal/ScalaQuest.git
cd ScalaQuest
```

##### Running the project (command line)

This project use [`sbt`](https://www.scala-sbt.org/1.x/docs/index.html). sbt compiles and runs the project.
Dont have sbt? Install sbt for 
[Mac](https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Mac.html),
[Windows](https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Mac.html),
[Linux](https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Linux.html)
```bash
cd in ScalaQuest
Run sbt. This will open up the sbt console
Type run
```
##### Running the project (IntelliJ IDEA)
```
Open the project with IntelliJ
Locate 'ScalaQuest' found in ScalaQuest -> src -> main -> scala -> de.scala_quest via
To run 'ScalaQuest' right click and select 'Run ScalaQuest'
```
##### Running the project (Eclipse)


### Components
##### TUI (Text-based User Interface)
##### GUI (Graphical User Interface)
##### WUI (Web User Interface)
`Include pictures when they are available`

### How to play
##### TUI
The TUI is turn-based. In single player mode, the player will have o answer the questions one after the other. 
In multiplayer mode, the different players will have to take turns answering their respective questions. The answers
to the questions will be numbered from 1-4. The player(s) will have to select the corresponding number via keyboard input.

##### GUI
The GUI is more interactive than the TUI. Here the players (maximum 2) will be shown a split-screen 
application with player1 on the left and player2 on the right of the screen. Each player receives his/her
questions and moves onto the next question after selecting an answer. Like the TUI, the answers
will be marked with numbers (1, 2, 3, 4),  and letters (h, j, k, l). 

##### WUI
`WIP`

The game is then over when one player completes all the questions.




### Checklist
- [x] MVC architecture
- [ ] 100% model code coverage
- [ ] Text-based User Interface
- [ ] Graphical User Interface
- [ ] Web User Interface