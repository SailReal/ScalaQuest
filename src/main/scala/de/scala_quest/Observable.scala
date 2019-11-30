package de.scala_quest

trait Observable {

    private var observers: List[Observer] = List()

    def addObserver(observer: Observer): Unit = observers = observer :: observers

    def removeObserver(observer: Observer): Unit = observers = observers.filter(_ != observer)

    protected def notifyObservers(data: GameState): Unit = observers.foreach(o => o.update(data).apply())

}
