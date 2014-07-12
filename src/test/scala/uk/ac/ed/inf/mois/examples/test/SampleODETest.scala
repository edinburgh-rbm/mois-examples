/*    
 *  Sample ODE Tests
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
package uk.ac.ed.inf.mois.examples.test

import java.lang.Math.abs
import org.scalatest.FlatSpec

import uk.ac.ed.inf.mois.examples.sampleODE
import uk.ac.ed.inf.mois.Conversions._

class SampleODETest extends FlatSpec {
  "sample ode" should "give dominik's expected results" in {

    val initialConditions = sampleODE.state.copy

    sampleODE(0, 50.0)

    assert(abs(sampleODE.y(0) + 0.1398) < 0.0001)
    assert(abs(sampleODE.y(1) + (-0.0916)) < 0.0001)

    // run it again
    sampleODE(50.0, 100)

    assert(abs(sampleODE.y(0) + 0.0032) < 0.0001)
    assert(abs(sampleODE.y(1) + (-0.0021)) < 0.0001)

    // reset the initial conditions
    sampleODE.state <<< initialConditions

    // make sure we get the same results
    sampleODE(0, 50.0)

    assert(abs(sampleODE.y(0) + 0.1398) < 0.0001)
    assert(abs(sampleODE.y(1) + (-0.0916)) < 0.0001)
  }
}
