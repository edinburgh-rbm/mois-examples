%% open NetCDF file and get the variables
time = ncread('pendulum.nc','time');
E = ncread('pendulum.nc','E');
p = ncread('pendulum.nc','p');
theta = ncread('pendulum.nc','θ');

%% plot the angle timeseries
figure(1)
plot(time(1:63), theta(:,1:63))
title('Planar pendulum: angular position as function of time')
xlabel('time')
ylabel('angle')
text(0.25, 30, 'Each line represents a different total energy\ncorresponding to different initial momentum')


%% plot the phase-space diagram
figure(2)
plot(theta(:,1:35)', p(:,1:35)')
set(gca, 'xlim', [-2*pi, 2*pi])
title('Planar pendulum: phase space diagram')
xlabel('angle')
ylabel('momentum')
text(-8, 13, 'Each line represents a different total energy\ncorresponding to different initial momentum')
print -dpng pendulum_theta_p.png
