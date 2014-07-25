---
layout: default
title: Example Models
---

The [mois-examples](https://github.com/edinburgh-rbm/mois-examples)
repository on Github contains, unsurprisingly, examples of models
implemented using the [mois](/mois) software. These pages contain
fuller information and [explanations of these models and sample
output](#example-models).

In general a model file needs to implement the model itself as
well as a class derived from `MoisMain` which serves to act as
an entry point for the simulation and sets up some default 
initial conditions.

  * [running models](running.html)
  * [collecting output](output.html)
  * [creating models](models.html)
  * [annotation and metadata](annotation.html)

Examples
========

  * DiscreteProcess
    * [Hénon map](henon)
  * ODE
    * [Rössler map](roessler)
  * PythonProcess
    * [example](python)
  * HamiltonianProcess
    * [planar pendulum](pendulum)
  * NetCDFWriter
    * [NetCDF output and processing](netcdf)
  * VarCalc
    * [Post-Hoc calculations](varcalc)
