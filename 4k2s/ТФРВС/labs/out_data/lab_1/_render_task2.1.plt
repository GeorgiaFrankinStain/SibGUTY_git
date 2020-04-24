set terminal png size 1000, 600 enhanced font 'Arial, 16'


set style line 1 linecolor rgb 'red' linetype 1 linewidth 2
set style line 2 linecolor rgb 'blue' linetype 1 linewidth 2
set style line 3 linecolor rgb 'green' linetype 1 linewidth 2
set style line 4 linecolor rgb 'black' linetype 0 linewidth 2



set title "График зависимости от значения μ"



set format x "%.0f"
# set logscale x
set xlabel "Количество элементов" font "Arial, 16"
# set xrange [0:8]
set xtics font "Arial, 12"




set format y "10^{%L}"
set logscale y
set ylabel "Среднее время безотказной работы (θ)" font "Arial, 16"
# set yrange [0:9999999999999999999999999999999]
set ytics font "Arial, 12"




set output 'grathics2.1.png'
plot for [i=1:4] 'output_2.1-'.i.'.txt' using 1:2 title sprintf("%s%s", "μ = ", columnhead(1)."") with linespoints ls i