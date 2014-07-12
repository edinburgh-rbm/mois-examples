set terminal png size 640,480 enhanced font
set output "pendulum-3d.png"
set key off
set title "Hamiltonian Pendulum"
set xrange [-10 to 10]
set pm3d
#set view 40, 60, 1, 1
#set view 1,0,1,1
#set view 120,60,1,1
unset hidden3d
set xlabel "θ"
set ylabel "p"
set zlabel "ln(E)"
set ytics 5
set ztics 
set zrange [0 to 5]
set cbrange [0 to 5]
set palette defined
set style data points
#set logscale z
splot "pendulum.dat" using 4:3:(log($2)) with dots
