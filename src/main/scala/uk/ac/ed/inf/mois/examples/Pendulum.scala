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
import uk.ac.ed.inf.mois.VarCalc

case class Pendulum(m: Double, l: Double) 
     extends HamiltonianProcess("Pendulum") with VarCalc with Math {
  Annotate("title", "Planar Pendulum")

  val E = Double("E")
  E.Annotate("long_name", "Total energy")
  E.Annotate("units", "J")

  val θ = Double("θ")
  θ.Annotate("long_name", "Angle anti-clockwise from vertically downwards")
  θ.Annotate("units", "rad")

  val p = Double("p")
  p.Annotate("long_name", "Angular momentum")
  p.Annotate("units", "J.s")

  val x = Double("x")
  x.Annotate("long_name", "X position in cartesian coordinates")
  x.Annotate("units", "m")

  val y = Double("y")
  y.Annotate("long_name", "Y position in cartesian coordinates")
  y.Annotate("units", "m")

  val g = 9.81

  H(Seq(θ), Seq(p)) := pow(p,2)/(2*m*pow(l,2)) + m*g*l*(1 - cos(θ))

  calc(E) := totalEnergy
  calc(x) := l * sin(θ)
  calc(y) := -l * cos(θ)
}

class PendulumModel extends Model {
  val m = Double("m") := 1
  val l = Double("l") := 1

  val process = new Pendulum(m, l)
  import process._

  Dimension(E, 41)
  Annotate("mass", m.value)
  Annotate("length", l.value)

  override def run(t: Double, tau: Double) {
    for(i <- (-20 until 21 by 1).map(_.toDouble/2)) {
      θ := PI/8
      p := i
      reset(t)
      process(t, tau)
      Dimension(E) += 1 // next band
    }
  }
}
