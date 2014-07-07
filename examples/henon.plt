set terminal png size 640,480 enhanced font
set output "henon.png"
set key off
set title "HénonModel -d 25000"
plot "henon.dat" using 2:3 with dots 
