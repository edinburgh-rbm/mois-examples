---
layout: default
title: Python Scripting
---

Python Scripting
================

Sometimes it's convenient to write software in other languages. Python
is one that is pretty commonly known and MOIS supports python scripts
for defining the behaviour of a process.

For example, consider the simple parametrised spiral
$$
\begin{aligned}
x(\theta) &= r \cos\left(\theta\right)\theta \\
y(\theta) &= r \sin\left(\theta\right)\theta \\
\theta(t) &= t\pi
\end{aligned}
$$

Writing this with Python is a little bit more involved than directly
writing it in Scala (and for a toy example like this there's no real
reason to not write it in Scala) and it looks like this:

{% highlight scala %}
case class PySpiral(val r0: Double) extends PythonProcess("Python Parametrised Spiral") {
  // declare variables
  val x = Double("ex:x")
  val y = Double("ex:y")
  // $r$ is just a constant parameter
  val r = Double("ex:r") := r0

  // calculate values of $x$ and $y$ using the python
  // function demo.spiral
  py(x, y) := Python("demo").spiral(r)
}
{% endhighlight %}

This process class takes a parameter, $r_0$ which is just meant for
scaling and is a constant, and declares the variables. Then it says
that a call to the python function `demo.spiral` should return two
values, the new ones for $x$ and $y$.

The implementation of `demo.spiral` looks like this:

{% highlight python %]
from math import cos, sin, pi

def spiral(t0, dt, r):
    "Return points on a spiral parametrised by t=t0+dt"
    theta = (t0 + dt) * pi
    x = r * cos(theta) * theta
    y = r * sin(theta) * theta
    return x, y
{% endhighlight %}

Now that we have the process defined, we need to make the model. We
don't use the process directly, but instead form a `ProcessGroup`
whose only function is to step through at a specific resolution using
`NaiveScheduler`. This is so that we can get the detailed trajectory
instead of just the final value.

{% highlight scala %}
object PySpiralModel extends MoisMain("Python Parametrised Spiral Model") {
  val model = new ProcessGroup("Python Parametrised Spiral Process Group") {
    // declare the variables that we will output
    val x = Double("ex:x")
    val y = Double("ex:y")

    // use the simple scheduler
    scheduler = new NaiveScheduler(0.01)

    // add the python process
    this += new PySpiral(0.1)
  }
}
{% endhighlight %}

