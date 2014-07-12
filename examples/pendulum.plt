set terminal png size 640,480 enhanced font
set output "pendulum.png"
set key off
set title "Hamiltonian Pendulum"
set xrange [-5 to 5]
set xlabel "θ"
set ylabel "p"
plot "pendulum.dat" using 4:3 with lines
