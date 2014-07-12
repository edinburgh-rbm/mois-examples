from math import *

def spiral(t0, dt, r):
    theta = (t0 + dt) * pi
    x = r * cos(theta) * theta
    y = r * sin(theta) * theta
    return x, y
