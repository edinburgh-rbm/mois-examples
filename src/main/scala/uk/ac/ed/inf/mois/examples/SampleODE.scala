package uk.ac.ed.inf.mois.examples

import uk.ac.ed.inf.mois.MoisMain
import uk.ac.ed.inf.mois.ProcessODE
import uk.ac.ed.inf.mois.sched

object sampleODE extends ProcessODE("SampleODE") {
  val x1 = Double("ex:x1")
  val x2 = Double("ex:x2")
  d(x1) := -0.3*x1 - 0.4*x2
  d(x2) := -0.5*x1 - 0.8*x2
}

object SampleODEModel extends MoisMain("Sample ODE Model") {
  val model = sampleODE
  import model._

  Double("ex:x1") := 25.0
  Double("ex:x2") := 50.0
}
