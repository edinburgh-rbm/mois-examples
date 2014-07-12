/*
 *  MOIS Examples: Ordinary Differential Equations
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
import uk.ac.ed.inf.mois.sched

object sampleODE extends OrdinaryProcess("SampleODE") {
  val x1 = Double("ex:x1")
  val x2 = Double("ex:x2")
  d(x1) := -0.3*x1 - 0.4*x2
  d(x2) := -0.5*x1 - 0.8*x2
}

object SampleODEModel extends MoisMain("Sample ODE Model") {
  val model = sampleODE
  import model._

  Double("ex:x1") := 25.0
  Double("ex:x2") := 50.0
}
