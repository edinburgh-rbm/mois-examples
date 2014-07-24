---
layout: default
title: Annotation
---

Annotation
==========

For models of physical systems, annotating the implementation is a way
to make the results more intelligeable. For example simply making
numeric output available in a table is only useful if there is some
way to know what the values mean, what quantity they represent and in
what units. It is helpful to have longer, more descriptive names for
things as well so that some amount of automatic presentation can be
done by tools that support it.

In the mois software, any of the main entities may be annotated,
anything deriving from [BaseProcess], [Model] or [Var]. It is done by
simply calling the `annotate` method (defined in the [Annotation]
trait) with a key and a value. The key must be a string but the value
can be anything.

For example,

{% highlight scala %}
import uk.ac.ed.inf.mois.ODE

class P extends ODE("Some Process") {
  annotate("title", "A process that calculates things")
  annotate("author", "Jane R Hacker")

  val x = Double("x")
  x.annotate("long_description", "The X value")
  x.annotate("units", "cm")
  ...
}
{% endhighlight %}

[Annotation]: https://edinburgh-rbm.github.io/mois/api/current/#uk.ac.ed.inf.mois.Annotation
[BaseProcess]: https://edinburgh-rbm.github.io/mois/api/current/#uk.ac.ed.inf.mois.BaseProcess
[Model]: https://edinburgh-rbm.github.io/mois/api/current/#uk.ac.ed.inf.mois.Mode
[Var]: https://edinburgh-rbm.github.io/mois/api/current/#uk.ac.ed.inf.mois.Var
