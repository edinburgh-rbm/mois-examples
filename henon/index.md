---
layout: default
title: Hénon map
---

The Hénon Map
=============

A [Hénon map](https://en.wikipedia.org/wiki/H%C3%A9non_map)
is a discrete-time dynamical system described by the following
equations:

$$
\begin{aligned}
x_{n+1} &= 1 - ax_n^2 + y_n \\\\
y_{n+1} &= bx_n
\end{aligned}
$$

{% highlight scala %}
import uk.ac.ed.inf.mois.MoisMain
import uk.ac.ed.inf.mois.DiscreteProcess

case class Hénon(a: Double, b: Double) extends DiscreteProcess("Henon") {
  val x = Double("ex:x")
  val y = Double("ex:y")
  n(x) := 1.0 - a * x*x + y
  n(y) := b * x
}

object HénonModel extends MoisMain("Hénon Model") {
  val model = new Hénon(1.4, 0.3)
}
{% endhighlight %}
