package uk.ac.ed.inf.mois.examples

import uk.ac.ed.inf.mois.sbt.MoisMain
import uk.ac.ed.inf.mois.{ProcessODE, State, Var}

object sampleODE extends ProcessODE("sample") {
  integral(
    Var(25.0, "ex:x1"),
    Var(50.0, "ex:x2")
  )
  def computeDerivatives(t: Double, y: Array[Double], ẏ: Array[Double]) {
    ẏ(0) = -0.3*y(0) - 0.4*y(1)
    ẏ(1) = -0.5*y(0) - 0.8*y(1)
  }
}

final class SampleODEModel extends MoisMain {
  val state = new State
  state := Var(25.0, "ex:x1")
  state := Var(50.0, "ex:x2")
  val process = sampleODE
}
