package org.example

import uk.ac.ed.inf.mois.{MoisMain, Process}

class Example extends Process("example") {
  def step(t: Double, tau: Double) {}
}

object ExampleModel extends MoisMain("Example") {
  val model = new Example
}
