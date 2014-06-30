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
