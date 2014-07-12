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

import uk.ac.ed.inf.mois.MoisMain
import uk.ac.ed.inf.mois.OrdinaryProcess

case class Roessler(a: Double, b: Double, c: Double) extends OrdinaryProcess("Henon") {
  val x = Double("ex:x")
  val y = Double("ex:y")
  val z = Double("ex:z")
  d(x) := -y - z
  d(y) := x + a*y
  d(z) := b + z * (x - c)
}

object RoesslerModel extends MoisMain("Roessler Model") {
  val model = new Roessler(0.2, 0.2, 5.7)
  import model._

  Double("ex:x") := 0.0
  Double("ex:y") := 0.0
  Double("ex:z") := 0.0
}
