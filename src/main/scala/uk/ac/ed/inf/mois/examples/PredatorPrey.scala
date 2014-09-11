/*
 *  MOIS Examples: Predator Prey
 *  Copyright (C) 2014 University of Edinburgh School of Informatics
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package uk.ac.ed.inf.mois.examples

import uk.ac.ed.inf.mois.{Model, ODE, ProcessGroup, VarMeta}
import uk.ac.ed.inf.mois.sched.NaiveScheduler
import spire.algebra.Rig
import spire.implicits._
import uk.ac.ed.inf.mois.implicits._

class PredatorPrey(alpha: Double, beta: Double, gamma: Double, delta: Double)
    extends ODE {
  val x = Double("x")
  val y = Double("y")
  d(x) := x * (alpha - beta * y)
  d(y) := -y * (gamma - delta * x)
}

class Prey(alpha: Double, beta: Double) extends ODE {
  val x = Double("x")
  val y = Double("y")
  d(x) := x * (alpha - beta * y)
}

class Predator(gamma: Double, delta: Double) extends ODE {
  val x = Double("x")
  val y = Double("y")
  d(y) := -y * (gamma - delta * x)
}

class PredatorPreyModel extends Model {
  val alpha = Double("alpha")
  val beta = Double("beta")
  val gamma = Double("gamma")
  val delta = Double("delta")

  val process = new PredatorPrey(alpha, beta, gamma, delta)

  override def init(t: Double) {
    super.init(t)
    import process._
    alpha := 1.3
    beta := 0.5
    gamma := 1.6
    delta := 0.1
    x := 1
    y := 3
  }
}

class PredatorPreyIndepModel extends Model {
  val alpha = Double("alpha") := 1.3
  val beta = Double("beta") := 0.5
  val gamma = Double("gamma") := 1.6
  val delta = Double("delta") := 0.1

  val process = new ProcessGroup
  process.scheduler = new NaiveScheduler(0.1)
  process += new Prey(alpha, beta)
  process += new Predator(gamma, delta)

  override def init(t: Double) {
    super.init(t)
    implicit def stringToMeta(s: String) = VarMeta(s, Rig[Double])

    import process._
    process.state.getVar[Double]("x") := 1
    process.state.getVar[Double]("y") := 3
  }
}
