/*
 *  MOIS Examples: Coupled Oscillators
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
import uk.ac.ed.inf.mois.sched.NaiveScheduler
import spire.implicits._
import uk.ac.ed.inf.mois.implicits._

class CoupledOscillator(w: Double, k: Double)
    extends ODE {
  val x1 = Double("c:x1")
  val x2 = Double("c:x2")
  val x3 = Double("c:x3")
  val x4 = Double("c:x4")

  d(x1) := x2
  d(x2) := w * (x1 - 100) - k * (x1 - x3)
  d(x3) := x4
  d(x4) := w * (x3 - 100) - k * (x3 - x1)
}

class CoupledOscillatorModel extends Model {
  val w = Double("w")
  val k = Double("k")
  val process = new CoupledOscillator(w, k)
  override def init(t: Double) {
    super.init(t)
    w := -1.0
    k := 0.5
  }
}

class CoupledOscillatorA(w: Double, k: Double)
    extends ODE {
  val x1 = Double("d:x1")
  val x2 = Double("d:x2")
  val x3 = Double("d:x3")

  d(x1) := x2
  d(x2) := w * (x1 - 100) - k * (x1 - x3)
}

class CoupledOscillatorB(w: Double, k: Double)
    extends ODE {
  val x1 = Double("d:x1")
  val x3 = Double("d:x3")
  val x4 = Double("d:x4")

  d(x3) := x4
  d(x4) := w * (x3 - 100) - k * (x3 - x1)
}

class CoupledOscillatorGroupModel extends Model {
  val w = Double("w")
  val k = Double("k")
  val process = new ProcessGroup {
    scheduler = new NaiveScheduler(0.01)
  }
  process += new CoupledOscillatorA(w, k)
  process += new CoupledOscillatorB(w, k)
  override def init(t: Double) {
    super.init(t)
    w := -1.0
    k := 0.5
  }
}
