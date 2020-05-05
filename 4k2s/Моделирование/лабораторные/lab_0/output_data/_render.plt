set terminal png size 1000, 1200 enhanced font 'Arial, 16'


set style line 1 linecolor rgb 'red' linetype 1 linewidth 2
set style line 2 linecolor rgb 'blue' linetype 1 linewidth 2
set style line 3 linecolor rgb 'green' linetype 1 linewidth 2
set style line 4 linecolor rgb 'black' linetype 0 linewidth 2



# set title "График зависимости от значения μ"



set format x "%.0f"
# set logscale x
set xlabel "Смещение" font "Arial, 16"
# set xrange [0:8]
set xtics font "Arial, 12"




set format y "%.3f"
# set logscale y
set ylabel "Автокорреляция" font "Arial, 16"
set yrange [-0.01:0.013]
# set yrange [-1:3]
set ytics font "Arial, 12"




set output 'grathic.png'
plot for [i=0:11] 'a_'.i.'.txt' using 1:2 title columnhead(1) with linespoints ls i