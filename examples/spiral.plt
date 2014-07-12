set terminal png size 640,480 enhanced font
set output "spiral.png"
set key off
set title "Python Spiral"
plot "spiral.dat" using 3:4 with lines
