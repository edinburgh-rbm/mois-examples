---
layout: default
title: Collecting Output
---

Collecting Output
=================

The mois software has the concept of a [StepHandler] which is a routine
that is run at each time step throughout a simulation after the main
calculation. These can do different things, but a major function is 
to write a timeseries of the variables to a file. There are several
output step handlers that do this:

  * [TsvWriter] writes a tab separated value file. This is useful for
    simple output where there is only one timeseries (i.e. the model
    isn't normally run several times with different initial
    conditions) and where the amount of output is relatively small.

  * [NetCDFWriter] creates an output in [NetCDF] format. This is
    useful for larger and multi-dimensional output.

  * [PlotFileWriter] and [PlotGUIWriter] produce plots and can take a
    sub-set of the variables to plot. They are not incredibly
    sophisticated and the recommendation is to use them only to get a
    rough idea of the behaviour of the system. Tools like [GNU Octave]
    and [Matlab] are better for producing more sophisticated plots and
    they can read [NetCDF] files easily.

    The GUI output will update a graphical window as the simulation
    progresses, displaying only the most recent 1000 data points.

    Care must be taken using the [PlotFileWriter] with large
    time-series or long running simulation because it stores all of
    the values that it must plot in memory. It is quite easy to run
    out of memory doing this.

As many output handlers as desired may be added when running a
model. It is quite possible to run all of them, and several copies of
the plotting flavours for different variable combinations at the same
time. But of course the more of these there are, the slower the
simulation will be. This is particularly the case with the plotting
handlers.

The way to add handlers from the command line or sbt *repl* is to
simply specify them with multiple `-o` or `--output` options. A list
of the supported arguments can be found at the end of the help
message:

~~~~~
sbt> run --help
...
Allowed output specifications:
	gui[:vars...]		Simple plot to a graphical window.
	netcdf:<file>		NetCDF output to the given file
	png:<file>[:vars...]	Simple plot to a PNG file
	tsv			Tab-separated values to standard output
	tsv:<file>		Tab-separated values to the given file
~~~~~

For example to run `FooModel` with gui plotting *x* and *y* and NetCDF
output one might do, 

~~~~~
sbt> run -d 10000 -o gui:x,y -o netcdf:foo.nc FooModel
~~~~~

Programmatically a step handler can be added to a process using the
`addStepHandler` method on [BaseProcess]. This can sometimes be useful
for debugging -- attaching a step handler to a lower level process in
a [ProcessGroup] and some other auxilliary facilities like [VarCalc]
use this to perform post-hoc calculations.

As an example,

{% highlight scala %}
import uk.ac.ed.inf.mois.{TsvWriter, ODE}

class P extends ODE("Some Process") {
  addStepHandler(new TsvWriter("debug.tsv"))
  ...
}
{% endhighlight %}

would hard-code writing out a TSV file for only this process.

[StepHandler]: https://edinburgh-rbm.github.io/mois/api/current/#uk.ac.ed.inf.mois.StepHandler
[TsvWriter]: https://edinburgh-rbm.github.io/mois/api/current/#uk.ac.ed.inf.mois.TsvWriter
[NetCDFWriter]: https://edinburgh-rbm.github.io/mois/api/current/#uk.ac.ed.inf.mois.NetCDFWriter
[PlotFileWriter]: https://edinburgh-rbm.github.io/mois/api/current/#uk.ac.ed.inf.mois.PlotfileWriter
[PlotGUIWriter]: https://edinburgh-rbm.github.io/mois/api/current/#uk.ac.ed.inf.mois.PlotGUIWriter
[NetCDF]: http://www.unidata.ucar.edu/netcdf/
[GNU Octave]: https://gnu.org/software/octave/
[Matlab]: http://www.mathworks.co.uk/products/matlab/
[BaseProcess]: https://edinburgh-rbm.github.io/mois/api/current/#uk.ac.ed.inf.mois.BaseProcess
[ProcessGroup]: https://edinburgh-rbm.github.io/mois/api/current/#uk.ac.ed.inf.mois.ProcessGroup
[VarCalc]: https://edinburgh-rbm.github.io/mois/api/current/#uk.ac.ed.inf.mois.VarCalc
