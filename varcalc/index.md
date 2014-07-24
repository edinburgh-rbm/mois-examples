---
layout: default
title: Post-Hoc Calculations
---

Post-Hoc Calculations
=====================

Continuing the [pendulum](../pendulum) example, it is good as far as it goes,
but it only gives answers in terms of generalised coordinates. Suppose
that we wanted to see the motion of the pendulum in cartesian
coordinates, as well as recording the total energy. This is simply
done by using the [VarCalc] trait. It enables one to write a bunch of
direct calculation rules that will be executed after the main calculation.

The new pendulum process now looks like this:

{% highlight scala %}
import uk.ac.ed.inf.mois.{HamiltonianProcess, Math, Model, VarCalc}

class Pendulum(m: Double, l: Double) 
     extends HamiltonianProcess("Planar Pendulum")
     with Math with VarCalc {
  val E = Double("ex:E")
  val x = Double("ex:x")
  val y = Double("ex:y")

  ... everything else as before ...

  // the totalEnergy method belongs to HamiltonianProcess and 
  // evaluates H(q..., p...)
  calc(E) := totalEnergy

  // do the coordinate transformation to cartesian coordinates from
  // our polar coordinates
  calc(x) := l * sin(θ)
  calc(y) := -l * cos(θ)
{% endhighlight %}

[VarCalc]: https://edinburgh-rbm.github.io/mois/api/current/#uk.ac.ed.inf.mois.VarCalc
