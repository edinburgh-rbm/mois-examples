## open NetCDF file and get the variables
nc = netcdf("pendulum.nc", "r")
time = nc{'time'}
E = nc{'E'}
p = nc{'p'}
theta = nc{'θ'}

## plot the angle timeseries
plot(time(1:63), theta(1:63,:))
title("Planar pendulum: angular position as function of time")
xlabel("time")
ylabel("angle")
text(0.25, 30, "Each line represents a different total energy\ncorresponding to different initial momentum")
print -dpng pendulum_theta_time.png

## plot the phase-space diagram
plot(theta(1:35, :), p(1:35, :))
axis([-3*pi, 3*pi])
title("Planar pendulum: phase space diagram")
xlabel("angle")
ylabel("momentum")
text(-8, 13, "Each line represents a different total energy\ncorresponding to different initial momentum")
print -dpng pendulum_theta_p.png
