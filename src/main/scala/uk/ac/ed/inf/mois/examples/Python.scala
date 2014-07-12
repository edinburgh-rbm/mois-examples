package uk.ac.ed.inf.mois.examples

import uk.ac.ed.inf.mois.MoisMain
import uk.ac.ed.inf.mois.{PythonProcess, ProcessGroup}
import uk.ac.ed.inf.mois.sched.NaiveScheduler

case class PySpiral(val r0: Double) extends PythonProcess("Python Parametrised Spiral") {
  val x = Double("ex:x")
  val y = Double("ex:y")
  val r = Double("ex:r") := r0
  py(x, y) := Python("demo").spiral(r)
}

object PySpiralModel extends MoisMain("Python Parametrised Spiral Model") {
  val model = new ProcessGroup("Python Parametrised Spiral Process Group") {
    val x = Double("ex:x")
    val y = Double("ex:y")
    scheduler = new NaiveScheduler(0.01)
    this += new PySpiral(0.1)
  }
}
