---
layout: default
title: Example Models
---

The
[mois-examples](https://github.com/edinburgh-rbm/mois-examples)
repository on Github contains, unsurprisingly, examples of 
models implemented using the
[mois](/mois)
software. These pages contain fuller information and explanations
of these models and sample output.

In general a model file needs to implement the model itself as
well as a class derived from *MoisMain* which serves to act as
an entry point for the simulation and sets up some default 
initial conditions.

A simulation is run in one of two ways. For both ways, a Java Runtime
Environment is required as is the 
[Scala Build Tool](http://www.scala-sbt.org). *sbt* is used to run the
simulation.

If the model is in some sense "finished" and has been published to
one of the standard binary class repositores such as 
[Maven Central](http://mvnrepository.com/), no extra software is
necessary to run it, only the *.properties* file that instructs
*sbt* where to get it is needed. An example *.properties* file
looks like
[this](https://github.com/edinburgh-rbm/mois-examples/blob/master/examples/roessler.properties).
It can then be downloaded and run like so:

```
sbt @roessler.properties
```

The helpful message displayed says that at the very least a duration
is required for the simulation. It is probably a good idea to specify
an output file for the generated data as well because otherwise it
will simply be printed in the terminal. For example,

```
sbt @roessler.properties --duration 1000 --output roessler.tsv
```

The other way is to obtain a copy of the source code and run *sbt* on
its own. From the interactive shell it is possible to simply use the
*run* command, with arguments as above:

```
sbt> run --duration 1000 --output roessler.tsv
```

If more than one model is available, an opportunity to pick the
desired one is presented.

This way of running the simulation is useful while the model is being
developed because it will automatically recompile any source code
files that have been changed before actually running. 

Example Models
==============


  * [Hénon map](henon)
  * [Rössler map](roessler)


