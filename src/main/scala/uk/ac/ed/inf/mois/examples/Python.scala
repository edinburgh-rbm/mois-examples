/*
 *  MOIS Examples: Python Process
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
import uk.ac.ed.inf.mois.{PythonProcess, ProcessGroup}
import uk.ac.ed.inf.mois.sched.NaiveScheduler

case class PySpiral(val r0: Double) extends PythonProcess("Python Parametrised Spiral") {
  val x = Double("ex:x")
  val y = Double("ex:y")
  val r = Double("ex:r") := r0
  py(x, y) := Python("demo").spiral(r)
}

object PySpiralModel extends MoisMain("Python Parametrised Spiral Model") {
  val model = new ProcessGroup("Python Parametrised Spiral Process Group") {
    val x = Double("ex:x")
    val y = Double("ex:y")
    scheduler = new NaiveScheduler(0.01)
    this += new PySpiral(0.1)
  }
}
