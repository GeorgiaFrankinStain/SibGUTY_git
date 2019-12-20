package lab_6_rgz;


import javafx.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.abs;

public class MyGraph {

        static public Graph generate_random_graph(int count_of_nodes) {
                //создаем массив вершин
                Integer[] nodesArrBI = new Integer[count_of_nodes];

                int max_count_edges = count_of_nodes * count_of_nodes / 2;
                Random tRandom = new Random();
                tRandom.setSeed(134);//FIXME
                int count_edges = abs(tRandom.nextInt() % max_count_edges);
//                int count_edges = abs(max_count_edges);

//                Map<Integer, Integer> edgesMapIntInt = new HashMap<Integer, Integer>();
                ArrayList<Pair<Integer, Integer>> edgesArrayListPairIntInt = new ArrayList<Pair<Integer, Integer>>();
                for (int i = 0; i < count_edges; i++) {
//                        tRandom.setSeed(System.nanoTime() + tRandom.nextInt());
                        int from_node = abs(tRandom.nextInt() % count_of_nodes);
                        int to_node = abs(tRandom.nextInt() % count_of_nodes);

                        if (from_node == to_node) {
                                to_node++;
                                to_node = to_node % count_of_nodes;
                        }

                        edgesArrayListPairIntInt.add(new Pair<Integer, Integer>(from_node, to_node));
                }

                return new Graph(nodesArrBI, edgesArrayListPairIntInt);
        }

}
