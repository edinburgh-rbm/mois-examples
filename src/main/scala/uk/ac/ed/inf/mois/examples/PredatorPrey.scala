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

import uk.ac.ed.inf.mois.{Model, ODE, ProcessGroup}
import uk.ac.ed.inf.mois.sched.AdaptiveKickScheduler

class PredatorPrey(alpha: Double, beta: Double, gamma: Double, delta: Double)
    extends ODE("Predator") {
  val x = Double("x")
  val y = Double("y")
  d(x) := x * (alpha - beta * y)
  d(y) := -y * (gamma - delta * x)
}

class Prey(alpha: Double, beta: Double) extends ODE("Prey") {
  val x = Double("x")
  val y = Double("y")
  d(x) := x * (alpha - beta * y)
}

class Predator(gamma: Double, delta: Double) extends ODE("Predator") {
  val x = Double("x")
  val y = Double("y")
  d(y) := -y * (gamma - delta * x)
}

class PredatorPreyModel extends Model {
  val alpha = Double("alpha") := 1.3
  val beta = Double("beta") := 0.5
  val gamma = Double("gamma") := 1.6
  val delta = Double("delta") := 0.1

  val process = new PredatorPrey(alpha, beta, gamma, delta)

  import process._
  x := 1
  y := 3
}

class PredatorPreyIndepModel extends Model {
  val alpha = Double("alpha") := 1.3
  val beta = Double("beta") := 0.5
  val gamma = Double("gamma") := 1.6
  val delta = Double("delta") := 0.1

  val process = new ProcessGroup("Predator Prey Model")
  process.scheduler = new AdaptiveKickScheduler(0.1)
  process += new Prey(alpha, beta)
  process += new Predator(gamma, delta)

  import uk.ac.ed.inf.mois.VarMeta
  implicit def stringToMeta(s: String) = VarMeta(s)

  import process._
  process.doubleVars("x") := 1
  process.doubleVars("y") := 3
}
