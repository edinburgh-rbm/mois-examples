/*
 *  MOIS Hamiltonian Plane Pendulum
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

import java.lang.Math.PI
import uk.ac.ed.inf.mois.Math
import uk.ac.ed.inf.mois.Model
import uk.ac.ed.inf.mois.HamiltonianProcess
import uk.ac.ed.inf.mois.Var
import uk.ac.ed.inf.mois.VarCalc
import spire.implicits._
import uk.ac.ed.inf.mois.implicits._

/**
 * Simulate the motion of a pendulum parametrised by mass and length
 */
class Pendulum(m: Var[Double], l: Var[Double])
     // the motion of the pendulum is defined in terms of the Hamiltonian
     extends HamiltonianProcess
     // this allows us to set variables after each integration step
     with VarCalc
     // this allows us to use standard math functions in the definitions
     with Math {

  /*
   * First define the variables
   */

  // E will hold the total energy
  val E = Double("E")

  // These are the variables in phase space
  val θ = Double("θ")
  val p = Double("p")

  // These are the corresponding positions in cartesian space
  val x = Double("x")
  val y = Double("y")

  // a constant
  val g = 9.81

  /*
   * Next define the Hamiltonian itself
   */
  H(θ)(p) := pow(p,2)/(2*m*pow(l,2)) + m*g*l*(1 - cos(θ))

  /*
   * Finally define the way to arrive at derived variables from
   * the result of integrating the Hamiltonian equations of motion
   */
  calc(E) := totalEnergy
  calc(x) := l * sin(θ)
  calc(y) := -l * cos(θ)
}

/**
 * The Pendulum Model acts as the entry-point for the simulation, and is
 * responsible for setting up initial conditions and documenting the process.
 */
class PendulumModel extends Model {
  // These are like variables but are actually model parameters
  val m = Double("m") default(1)
  val l = Double("l") default(1)

  // These variables govern the setting of initial conditions and the
  // number of times to run the simulation. Run starting at p0
  // n times and increase p0 by 0.5 each time
  val θ_0 = Double("θ0") default(0)
  val p_0 = Double("p0") default(-10)
  val p_n = Int("n") default(41)
  val p_delta = Double("p_delta") default(0.5)

  // Create the process
  val process = new Pendulum(m, l)

  // bring the process' variables and supporting functions within scope
  import process._

  // E is constant to within floating point errors for each run of the simulation.
  // So best to treat it as a Dimension with 41 different values
  Dimension(E, 41)

  // Global annotations on the model
  annotate("title", "Planar Pendulum")

  // Annotations on each variable
  E.annotate("long_name", "Total energy")
  E.annotate("units", "J")

  θ.annotate("long_name", "Angle anti-clockwise from vertically downwards")
  θ.annotate("units", "radian")
  p.annotate("long_name", "Angular momentum")
  p.annotate("units", "J.s")

  x.annotate("long_name", "X position in cartesian coordinates")
  x.annotate("units", "m")
  y.annotate("long_name", "Y position in cartesian coordinates")
  y.annotate("units", "m")

  // We override the `init` method because we want to set initial values
  override def init(t: Double) {
    super.init(t)
    // We store and document the mass and length but not the p_*
    // simulation parameters because they appear in the data
    // we use the actual value here so it is only possible within
    // the init function
    annotate("mass", m.value)
    annotate("length", l.value)
  }

  // We override the `run` method because we want to run the simulation several
  // times, 41 times in all from various initial conditions, -10 to 10 J.s for the
  // angular momentum in steps of 0.5
  override def run(t: Double, tau: Double) {
    for(i <- 0 until p_n) {
      θ := θ_0
      p := p_0
      // for next time, increment p0
      p_0 := p_0 + p_delta
      // reset all output and any internal state
      reset(t)
      // run the process
      process(t, tau)
      // the time dimension is treated automatically but the energy is not,
      // so we have to signal that we are going to the next level
      Dimension(E) += 1
    }
  }
}
