package uk.ac.ed.inf.mois.examples

import uk.ac.ed.inf.mois.reaction.DeterministicReactionNetwork
import uk.ac.ed.inf.mois.Model
import spire.implicits._

class Brusselator
  extends DeterministicReactionNetwork {

  val A = Species("A") default(1.0)
  val B = Species("B") default(1.7)
  val X = Species("X") default(1.0)
  val Y = Species("Y") default(1.0)

  reactions(
    A --> X + A at 1.0,
    2(X) + Y --> 3(X) at 1.0,
    B + X --> B + Y at 1.0,
    X --> () at 1.0
  )
}

/**
 * BrusselatorModel is used generically in test:run because it is a
 * decent model to test things like graphing out.
 */
class BrusselatorModel extends Model {
  val process = new Brusselator
}
