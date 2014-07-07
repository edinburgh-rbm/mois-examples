set terminal png size 640,480 enhanced font
set output "roessler.png"
set key off
set title "RoesslerModel -d 10000"
splot "roessler.dat" using 2:3:4 with lines
