/*
 *  MOIS Examples: Roessler Map
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

import uk.ac.ed.inf.mois.{Model, ODE, Var}
import spire.implicits._
import uk.ac.ed.inf.mois.implicits._

case class Roessler(a: Var[Double], b: Var[Double], c: Var[Double]) extends ODE {
  val x = Double("x")
  val y = Double("y")
  val z = Double("z")
  d(x) := -y - z
  d(y) := x + a*y
  d(z) := b + z * (x - c)
}

class RoesslerModel extends Model {
  val a = Double("a") default(0.2)
  val b = Double("b") default(0.2)
  val c = Double("c") default(5.7)

  val process = new Roessler(a, b, c)
}
