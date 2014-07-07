package uk.ac.ed.inf.mois.examples

import uk.ac.ed.inf.mois.MoisMain
import uk.ac.ed.inf.mois.OrdinaryProcess

case class Roessler(a: Double, b: Double, c: Double) extends OrdinaryProcess("Henon") {
  val x = Double("ex:x")
  val y = Double("ex:y")
  val z = Double("ex:z")
  d(x) := -y - z
  d(y) := x + a*y
  d(z) := b + z * (x - c)
}

object RoesslerModel extends MoisMain("Roessler Model") {
  val model = new Roessler(0.2, 0.2, 5.7)
  import model._

  Double("ex:x") := 0.0
  Double("ex:y") := 0.0
  Double("ex:z") := 0.0
}
