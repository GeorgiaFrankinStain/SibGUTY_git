package lab_6_rgz;


import javafx.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Graph {
        public Integer[] nodesArrInteger = null;
//        public Map<Integer, Integer> edgesMapIntInt = null;
        public ArrayList<Pair<Integer, Integer>> edgesArrayListPairIntInt = null;

        public Graph(Integer[] nodesArrInteger, ArrayList<Pair<Integer, Integer>> edgesArrayListPairIntInt) {
                this.nodesArrInteger = nodesArrInteger;
                this.edgesArrayListPairIntInt = edgesArrayListPairIntInt;
        }




        public void print_to_file_dot(File outputFile) throws IOException {
                FileWriter fileOut = new FileWriter(outputFile);
                fileOut.write("strict graph {\n    node [label=\"\\N\", style=filled];");




                for (int i = 0; i < this.nodesArrInteger.length; i++) {
                        int color_number = 0;
                        if (this.nodesArrInteger[i] != null) {
                                color_number = this.nodesArrInteger[i] % this.title_color__ArrStr.length; //FIXME NEVER
                                color_number *= 17;
                                color_number *= color_number;
                                color_number = color_number % this.title_color__ArrStr.length;
                        }

                        String colorStr = this.title_color__ArrStr[color_number];
                        fileOut.write("    " + i + "\t[fillcolor=" + colorStr + "];\n");
                }



                for (Pair<Integer, Integer> entry : this.edgesArrayListPairIntInt) {
                        Integer keyInt = entry.getKey();
                        Integer valueInt = entry.getValue();

                        fileOut.write("    " + keyInt + " -- " + valueInt + "\n");
                }

                fileOut.write("\n}");
                fileOut.close();
        }
        static public void create_image(String dotFile, String out_image__File) throws IOException, InterruptedException {
/*                //здесь "sleep 15" и есть ваша консольная команда

//                Process proc = Runtime.getRuntime().exec("d:");
//                Process proc = Runtime.getRuntime().exec(new String[] { "C:\\Windows\\System32\\cmd.exe"});
//                Process proc = Runtime.getRuntime().exec(new String[] { "cp -help"});
//                Process proc2 = Runtime.getRuntime().exec("dot -Tpng " + dotFile + " -o " + out_image__File);
//                Process proc = Runtime.getRuntime().exec("sleep 15");
//                Process proc = Runtime.getRuntime().exec("cp -help");
                Process proc = Runtime.getRuntime().exec("c:");
                proc.waitFor();
                proc.destroy();*/ //FIXME T:FUTURE
        }



        public void coloring_graph() {
                int current_color = 1;
                boolean exists_no_color_nodes__BOOL;
                int global_i = 0;
                do {
                        exists_no_color_nodes__BOOL = false;


                        for (int i = 0; i < this.nodesArrInteger.length; i++) {
                                boolean this_node_coloring__BOOL = this.nodesArrInteger[i] != null;
                                if (!this_node_coloring__BOOL) {
                                        if (exists_are_links_to_nodes_of_the_same_color(i, current_color)) {
                                                //пропускаем и отмечаем пропуск
                                                exists_no_color_nodes__BOOL = true;
                                        } else {
                                                this.nodesArrInteger[i] = current_color;
                                        }

                                }
                        }


                        current_color++;
                        System.out.println(global_i);
//                        global_i++;
//                        if (global_i == 2) {
//
//                                break;//FIXME
//                        }


                } while (exists_no_color_nodes__BOOL);


        }
        private boolean exists_are_links_to_nodes_of_the_same_color(int current_pos, int current_color) {
                for (int j = 0; j < current_pos; j++) {
                        if (this.nodesArrInteger[j] == null) {
                                continue;
                        }
                        //проверяем только окрашенные узлы, бесцетные узлы разумеется совпадать не будут по цвету
                        int color_in_j_node = this.nodesArrInteger[j];
                        if (color_in_j_node == current_color) {
                                Pair<Integer, Integer> findPairIntInt = new Pair<>(current_pos, j);
                                Pair<Integer, Integer> find_reverse__PairIntInt = new Pair<>(j, current_pos);

                                boolean isset_edge = this.edgesArrayListPairIntInt.contains(findPairIntInt)
                                                                     || this.edgesArrayListPairIntInt.contains(find_reverse__PairIntInt);

                                if (isset_edge) {
                                        return true;
                                }
                        }

                }

                return false;
        }
        static private String[] title_color__ArrStr = {
                        "white",
                        "aliceblue",
                        "antiquewhite",
                        "antiquewhite1",
                        "antiquewhite2",
                        "antiquewhite3",
                        "antiquewhite4",
                        "aquamarine",
                        "aquamarine1",
                        "aquamarine2",
                        "aquamarine3",
                        "aquamarine4",
                        "azure",
                        "azure1",
                        "azure2",
                        "azure3",
                        "azure4",
                        "beige",
                        "bisque",
                        "bisque1",
                        "bisque2",
                        "bisque3",
                        "bisque4",
                        "black",
                        "blanchedalmond",
                        "blue   ",
                        "blue1",
                        "blue2",
                        "blue3",
                        "blue4",
                        "blueviolet",
                        "brown",
                        "brown1",
                        "brown2",
                        "brown3",
                        "brown4",
                        "burlywood",
                        "burlywood1",
                        "burlywood2",
                        "burlywood3",
                        "burlywood4",
                        "cadetblue",
                        "cadetblue1",
                        "cadetblue2",
                        "cadetblue3",
                        "cadetblue4",
                        "chartreuse",
                        "chartreuse1",
                        "chartreuse2",
                        "chartreuse3",
                        "chartreuse4",
                        "chocolate",
                        "chocolate1",
                        "chocolate2",
                        "chocolate3",
                        "chocolate4",
                        "coral",
                        "coral1",
                        "coral2",
                        "coral3",
                        "coral4",
                        "cornflowerblue",
                        "cornsilk",
                        "cornsilk1",
                        "cornsilk2",
                        "cornsilk3",
                        "cornsilk4",
                        "crimson",
                        "cyan",
                        "cyan1",
                        "cyan2",
                        "cyan3",
                        "cyan4",
                        "darkgoldenrod",
                        "darkgoldenrod1",
                        "darkgoldenrod2",
                        "darkgoldenrod3",
                        "darkgoldenrod4",
                        "darkgreen",
                        "darkkhaki",
                        "darkolivegreen",
                        "darkolivegreen1",
                        "darkolivegreen2",
                        "darkolivegreen3",
                        "darkolivegreen4",
                        "darkorange",
                        "darkorange1",
                        "darkorange2",
                        "darkorange3",
                        "darkorange4",
                        "darkorchid",
                        "darkorchid1",
                        "darkorchid2",
                        "darkorchid3",
                        "darkorchid4",
                        "darksalmon",
                        "darkseagreen",
                        "darkseagreen1",
                        "darkseagreen2",
                        "darkseagreen3",
                        "darkseagreen4",
                        "darkslateblue",
                        "darkslategray",
                        "darkslategray1",
                        "darkslategray2",
                        "darkslategray3",
                        "darkslategray4",
                        "darkslategrey",
                        "darkturquoise",
                        "darkviolet",
                        "deeppink",
                        "deeppink1",
                        "deeppink2",
                        "deeppink3",
                        "deeppink4",
                        "deepskyblue",
                        "deepskyblue1",
                        "deepskyblue2",
                        "deepskyblue3",
                        "deepskyblue4",
                        "dimgray",
                        "dimgrey",
                        "dodgerblue",
                        "dodgerblue1",
                        "dodgerblue2",
                        "dodgerblue3",
                        "dodgerblue4",
                        "firebrick",
                        "firebrick1",
                        "firebrick2",
                        "firebrick3",
                        "firebrick4",
                        "floralwhite",
                        "forestgreen",
                        "gainsboro",
                        "ghostwhite",
                        "gold",
                        "gold1",
                        "gold2",
                        "gold3",
                        "gold4",
                        "goldenrod",
                        "goldenrod1",
                        "goldenrod2",
                        "goldenrod3",
                        "goldenrod4"
        };
}
