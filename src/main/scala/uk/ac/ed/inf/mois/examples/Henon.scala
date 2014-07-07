package uk.ac.ed.inf.mois.examples

import uk.ac.ed.inf.mois.MoisMain
import uk.ac.ed.inf.mois.DiscreteProcess

case class Hénon(a: Double, b: Double) extends DiscreteProcess("Henon") {
  val x = Double("ex:x")
  val y = Double("ex:y")
  n(x) := 1.0 - a * x*x + y
  n(y) := b * x
}

object HénonModel extends MoisMain("Sample ODE Model") {
  val model = new Hénon(1.4, 0.3)
  import model._

  Double("ex:x") := 0.0
  Double("ex:y") := 0.0
}
