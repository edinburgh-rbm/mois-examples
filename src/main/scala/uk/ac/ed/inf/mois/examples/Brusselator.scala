package uk.ac.ed.inf.mois.examples

import uk.ac.ed.inf.mois.{DeterministicReactionNetwork, Model}

class Brusselator
  extends DeterministicReactionNetwork("Brusselator") {
  
  val A = Species("A")
  val B = Species("B")
  val X = Species("X")
  val Y = Species("Y")
  
  reactions(
    A -> X + A at 1.0,
    2(X) + Y -> 3(X) at 1.0,
    B + X -> B + Y at 1.0,
    X -> () at 1.0
  )
}

/**
 * BrusselatorModel is used generically in test:run because it is a
 * decent model to test things like graphing out.
 */
class BrusselatorModel extends Model {
  val process = new Brusselator
  import process._
  A := 1.0
  B := 1.7
  X := 1.0
  Y := 1.0
}
