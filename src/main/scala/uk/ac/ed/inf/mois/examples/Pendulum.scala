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

import java.lang.Math.{cos, PI}
import uk.ac.ed.inf.mois.Model
import uk.ac.ed.inf.mois.HamiltonianProcess

case class Pendulum(m: Double, l: Double) extends HamiltonianProcess("Pendulum") {
  val θ = Double("ex:θ")
  val p = Double("ex:p")
  val g = 9.81
  H(Seq(θ), Seq(p)) := (p*p)/(2*m*l*l) + m*g*l*(1 - cos(θ))
}

class PendulumModel extends Model {
  val m = Double("ex:m") := 1
  val l = Double("ex:l") := 1

  val process = new Pendulum(m, l)
  import process._

  override def run(t: Double, tau: Double) {
    for(i <- (-20 until 20 by 1).map(_.toDouble/2)) {
      θ := PI/8
      p := i
      for (sh <- process.stepHandlers)
        sh.reset(t, process)
      process(t, tau)
    }
  }
}
