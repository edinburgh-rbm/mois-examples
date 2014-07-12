package org.example.test

import org.scalatest.{FlatSpec, Matchers}

import org.example.Example

class ExampleTest extends FlatSpec with Matchers {
  "an example" should "test things here" in {

    val ex = new Example

    0 should not be 1
  }
}
