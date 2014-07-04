package uk.ac.ed.inf.mois.examples

import uk.ac.ed.inf.mois.MoisMain
import uk.ac.ed.inf.mois.{ProcessODE, State, Var}

object sampleODE extends ProcessODE("SampleODE") {
  integral(
    Var(25.0, "ex:x1"),
    Var(50.0, "ex:x2")
  )
  def computeDerivatives(t: Double, y: Array[Double], ẏ: Array[Double]) {
    ẏ(0) = -0.3*y(0) - 0.4*y(1)
    ẏ(1) = -0.5*y(0) - 0.8*y(1)
  }
}

object SampleODEModel extends MoisMain {
  val name = "Sample ODE Model"
  val state = new State
  // default initial conditions
  state := Var(25.0, "ex:x1")
  state := Var(50.0, "ex:x2")
  val process = sampleODE
}
