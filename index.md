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
% sbt @roessler.properties
Getting uk.ac.ed.inf mois-examples_2.11 1.99.2-SNAPSHOT ...
downloading ...
...
Error: no duration given, please specify one using -d
mois 1.99.2-SNAPSHOT
Usage: Roessler Model [options]

  -b <value> | --begin <value>
        Simulation start time (default: 0.0)
  -d <value> | --duration <value>
        Simulation duration (mandatory)
  -i <value> | --initial <value>
        Initial conditions filename (JSON)
  -s <value> | --state <value>
        Dump state at end of simulation
  -f <value> | --format <value>
        Timeseries output format (default: tsv)
  -o <value> | --output <value>
        Output file (default: stdout)
```

The helpful message displayed says that at the very least a duration
is required for the simulation. It is probably a good idea to specify
an output file for the generated data as well because otherwise it
will simply be printed in the terminal. For example,

```sbt @roessler.properties --duration 1000 --output roessler.tsv
```

The other way is to obtain a copy of the source code and run *sbt* on
its own. From the interactive shell it is possible to simply use the
*run* command, with arguments as above:

```
% sbt
> run --duration 1000 --output roessler.tsv
[info] Updating {file:.../mois-examples/}mois-examples...
[info] Resolving jline#jline;2.11 ...
[info] Done updating.
[info] Compiling 4 Scala sources to .../mois-examples/target/scala-2.11/classes...

Multiple main classes detected, select one to run:

 [1] uk.ac.ed.inf.mois.examples.SampleODEModel
 [2] uk.ac.ed.inf.mois.examples.BiStableModel
 [3] uk.ac.ed.inf.mois.examples.RoesslerModel
 [4] uk.ac.ed.inf.mois.examples.HénonModel

Enter number: 3
[info] Running uk.ac.ed.inf.mois.examples.RoesslerModel -d 1000 -o roessler.tsv
[success] Total time: 28 s, completed Jul 8, 2014 10:53:57 PM
>
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


