---
layout: default
title: Creating Models
---

In the examples repository there is a 
[skeleton template](https://github.com/edinburgh-rbm/mois-examples/tree/master/skel)
directory tree that can be used as a starting point for developing new
models and processes. It contains these files:

~~~~~
skel/build.sbt
skel/src/main/scala/org/example/Example.scala
skel/src/main/resources/example.py
skel/src/main/resources/META-INF/services/uk.ac.ed.inf.mois.Model
skel/src/test/scala/org/example/ExampleTest.scala
skel/src/test/resources/example_test.py
~~~~~

The first one, 
[build.sbt](https://github.com/edinburgh-rbm/mois-examples/blob/master/skel/build.sbt),
contains instructions for the 
[sbt](http://www.scala-sbt.org/) build tool for how to compile the
model. This is where any dependencies are listed as well as version
information and so forth.

The actual source code for the model goes in the `src/main` sub
directory. In here is
[Example.scala](https://github.com/edinburgh-rbm/mois-examples/blob/master/skel/src/main/scala/org/example/Example.scala),
a scala source code file that implements the
model. The convention is to use a package name that is unique and
corresponds to a domain name that belongs to you. The source code goes
in a sub-directory that is this name, reversed. That is, for a package
called "foo.example.org" the files would go in
`src/main/scala/org/example/foo`.

The file in `META-INF` is important. It should contain the fully
qualified name(s) of your models, one per line. This file is how
the main Mois program, or sbt knows how to find your models to
run them.

Optionally, any other necessary files should be put in
`src/main/resources`. These can be any sort of file, but this is
mainly used for python scripting -- the python source code goes
there.

The `src/test` sub-directory mirrors the main one and is where tests
for the model go. A minimal example is
[ExampleTest.scala](https://github.com/edinburgh-rbm/mois-examples/blob/master/skel/src/test/scala/org/example/ExampleTest.scala).
It is a very good idea to have a suite of tests that
verify that different parts of the model are working as expected. This
practice helps to find bugs and can help to make sure that when a change
is made that the change doesn't also break other things that. The test
suite can also serve as useful supplementary documentation with
examples of how to use different parts of the model software.

The model can then be compiled with `sbt compile` and tested with `sbt
test` and run with `sbt run` in the usual way.
