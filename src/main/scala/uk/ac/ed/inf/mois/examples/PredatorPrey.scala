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

import uk.ac.ed.inf.mois.{Model, ProcessGroup, Var}
import uk.ac.ed.inf.mois.ode.{ODE, Apache}
import uk.ac.ed.inf.mois.sched.NaiveScheduler
import spire.algebra.Rig
import spire.implicits._
import uk.ac.ed.inf.mois.implicits._

class PredatorPrey(alpha: Var[Double], beta: Var[Double], gamma: Var[Double], delta: Var[Double])
    extends ODE[Double, Double] with Apache {

  val x = Double("pp:x")
  val y = Double("pp:y")
  d(x) := x * (alpha - beta * y)
  d(y) := -y * (gamma - delta * x)
}

class Prey(alpha: Var[Double], beta: Var[Double]) extends ODE[Double, Double] with Apache {
  val x = Double("pp:x")
  val y = Double("pp:y")
  d(x) := x * (alpha - beta * y)
}

class Predator(gamma: Var[Double], delta: Var[Double]) extends ODE[Double, Double] with Apache {
  val x = Double("pp:x")
  val y = Double("pp:y")
  d(y) := -y * (gamma - delta * x)
}

class PredatorPreyModel extends Model {
  prefix("pp", "https://edinburgh-rbm.github.io/mois-examples/pp#")
  val alpha = Double("pp:alpha") param() default(1.3)
  val beta = Double("pp:beta") param() default(0.5)
  val gamma = Double("pp:gamma") param() default(1.6)
  val delta = Double("pp:delta") param() default(0.1)
  val x = Double("pp:x") default(1)
  val y = Double("pp:y") default(3)

  val process = new PredatorPrey(alpha, beta, gamma, delta)
}

class PredatorPreyIndepModel extends Model {
  prefix("pp", "https://edinburgh-rbm.github.io/mois-examples/pp#")
  val alpha = Double("pp:alpha") param() default(1.3)
  val beta = Double("pp:beta") param() default(0.5)
  val gamma = Double("pp:gamma") param() default(1.6)
  val delta = Double("pp:delta") param() default(0.1)
  val x = Double("pp:x") default(1)
  val y = Double("pp:y") default(3)

  val process = new ProcessGroup
  process.scheduler = new NaiveScheduler(0.1)
  process += new Prey(alpha, beta)
  process += new Predator(gamma, delta)
}
