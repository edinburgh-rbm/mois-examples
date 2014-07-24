---
layout: default
title: NetCDF Output and Processing
---

NetCDF output and processing
============================

[NetCDF] is a format for expressing multidimensional arrays of
numerical data. It is commonly used in geosciences and climate
modelling but is well suited to any sort of timeseries or gridded
data. There is extensive documentation on the [NetCDF] web site but
here we will look at how to make mois produce such files and some
simple ways of processing them with [GNU Octave] and [Matlab].

Our example will be the [planar pendulum] model. To write the file we
simply run the model like this:

~~~~~
sbt> run model -d 10 -o netcdf:pendulum.nc Pendulum
~~~~~

This produces the file [pendulum.nc](pendulum.nc). Some basic
operations can be done on this file with the standard utility
programs. On Debian GNU/Linux derived systems these are available in
the `netcdf-bin` package. The `ncdump` program will display some
information about the file, and optionally render its metadata in XML
format: 

~~~~
% ncdump -h pendulum.nc
netcdf pendulum {
dimensions:
	time = UNLIMITED ; // (183 currently)
	E = 41 ;
variables:
	double time(time) ;
	double E(E) ;
		E:units = "J" ;
		E:long_name = "Total energy" ;
	double p(time, E) ;
		p:units = "J.s" ;
		p:long_name = "Angular momentum" ;
...
~~~~~

Similar information, more verbosely presented can be obtained using
the command with the same name within Octave:

~~~~~
octave:1> ncdump("pendulum.nc")
nc = netcdf('pendulum.nc','noclobber');

% dimensions

nc('time') = 183;
nc('E') = 41;

% variables

nc{'time'} = ncdouble('time');  % 183 elements 

nc{'E'} = ncdouble('E');  % 41 elements 
nc{'E'}.units = ncchar('J');
nc{'E'}.long_name = ncchar('Total energy');
...
~~~~~

This output is interesting because it explains how to get at the
variables -- one opens the file with the `netcdf` command and then
just references the variable names in curly brackets. The two
dimensions, time and energy, are just arrays. The other values are two
dimensional matrices, where the rows correspond to time and the
columns correspond to energies.

Recall that in the pendulum example, the simulation is run 41 times,
for different iniital conditions (increasing momentum in steps of 0.5
from -10 to 10 J.s). These initial conditions correspond to different
energies, but within a single run of the simulation the total energy
remains the same because there are no dissipative forces. 

The metadata is also accessible, `nc{'p'}.long_name` will retrieve the
the descriptive string, and this can be used, for example, to label
plots.

For GNU Octave, we can plot the phase space diagram (angle
vs. momentum) like so:

{% highlight octave %}
nc = netcdf("pendulum.nc", "r")
p = nc{'p'}
theta = nc{'θ'}

plot(theta(1:35, :), p(1:35, :))
axis([-2*pi, 2*pi])
title([nc.title ": phase space diagram"])
xlabel(theta.long_name)
ylabel(p.long_name)
text(-8, 13, "Each line represents a different total energy\ncorresponding to different initial momentum")
print -dpng pendulum_theta_p.png
{% endhighlight %}

Which produces this picture:
![Phase space diagram](pendulum_theta_p.png)

A fuller example with Octave is
[pendulum-octave.m](pendulum-octave.m).

[NetCDF]: http://www.unidata.ucar.edu/netcdf
[GNU Octave]: https://gnu.org/software/octave/
[Matlab]: http://www.mathworks.co.uk/products/matlab/
[planar pendulum]: ../pendulum
