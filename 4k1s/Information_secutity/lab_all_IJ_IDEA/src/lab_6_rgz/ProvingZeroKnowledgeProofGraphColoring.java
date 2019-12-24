package lab_6_rgz;

import javafx.util.Pair;
import lab_1.MyBigInteger;
import lab_1.PrimeNumber;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class ProvingZeroKnowledgeProofGraphColoring {
        BigInteger global_max__BI = BigInteger.valueOf(10000);
        Graph coloringGraph;
        Random sharedRandom = new Random();

        public int size_bit_write;

        private BigInteger[] cArrBI = null;

        public int getSize_bit_write() {
                return size_bit_write;
        }

        public ProvingZeroKnowledgeProofGraphColoring(Graph coloringGraph) {
                this.coloringGraph = coloringGraph;
                sharedRandom.setSeed(13);
        }
        
        public BigInteger[][] get_crypt_graph() throws Exception {
                int[] mix__Arr_int = mixing_array(this.coloringGraph.count_color);




                //генерация больших чисел с заменой последних битов на цвета

                BigInteger[] musor_p_color__ArrBI = new BigInteger[this.coloringGraph.nodesArrInteger.length];
                int size_bit_write = BigInteger.valueOf(this.coloringGraph.count_color + 1).bitLength(); //LINK55412542542412541
                this.size_bit_write = size_bit_write;
                for (int i = 0; i < musor_p_color__ArrBI.length; i++) {
                        musor_p_color__ArrBI[i] = MyBigInteger.generae_diapason(BigInteger.ZERO, global_max__BI);

                        musor_p_color__ArrBI[i].shiftLeft(size_bit_write);

                        BigInteger colorBI = BigInteger.valueOf(this.coloringGraph.nodesArrInteger[i]);
                        BigInteger color_no_equal_0__BI = colorBI.add(BigInteger.ONE); //LINK55412542542412541
                        musor_p_color__ArrBI[i].add(color_no_equal_0__BI);
                }





                //формирование для каждой вершины RSA данных
                this.cArrBI = new BigInteger[musor_p_color__ArrBI.length];
                BigInteger[][] resArrArrBI = new BigInteger[musor_p_color__ArrBI.length][];
                for (int i = 0; i < musor_p_color__ArrBI.length; i++) {
                        resArrArrBI[i] = encrypt_node(musor_p_color__ArrBI[i]);

                        this.cArrBI[i] = resArrArrBI[i][3];
                        resArrArrBI[i][3] = BigInteger.ZERO;
                }


                return resArrArrBI;
        }
        public BigInteger[] sending_secret_keys_for_vertices_around_given_edge(
                        Pair<Integer, Integer> edgePariIntInt
        ) {
                int index_1 = edgePariIntInt.getKey();
                int index_2 = edgePariIntInt.getValue();
                BigInteger[] resArrBI = new BigInteger[2];
                System.out.println("real color 1: " + this.coloringGraph.nodesArrInteger[index_1]);
                System.out.println("real color 2: " + this.coloringGraph.nodesArrInteger[index_2]);
                resArrBI[0] = this.cArrBI[index_1];
                resArrBI[1] = this.cArrBI[index_2];

                this.cArrBI = null;

                return resArrBI;
        }
        
        
        
        
        
        
        private BigInteger[] encrypt_node(BigInteger rand_p_color__BI) { //return n, d, z, c
                BigInteger max_diapason__BI = global_max__BI;
                BigInteger p1BI = PrimeNumber.generate(max_diapason__BI);


                BigInteger p2BI;
                boolean p2BI__equals__p2BI__BOOL;
                do {
                        p2BI = PrimeNumber.generate(max_diapason__BI);
                        p2BI__equals__p2BI__BOOL = p1BI.compareTo(p2BI) == 0;
                } while (p2BI__equals__p2BI__BOOL);


                BigInteger nBI = p1BI.multiply(p2BI);


                BigInteger p1_minus_1__BI = p1BI.subtract(BigInteger.ONE);
                BigInteger p2_minus_1__BI = p2BI.subtract(BigInteger.ONE);
                BigInteger fi_from_n__BI = p1_minus_1__BI.multiply(p2_minus_1__BI);


                //GCD(dBI, fi_from_n__BI) = 1
                BigInteger dBI = PrimeNumber.coprime_generate(fi_from_n__BI, global_max__BI); //публичное: e - rand() нечетное и НОД(fi_from_n, e) == 1


                //cd mod fi = 1
                BigInteger cBI = dBI.modInverse(fi_from_n__BI);


                BigInteger zBI = rand_p_color__BI.modPow(dBI, nBI);

                BigInteger[] res__Arr_int = {nBI, dBI, zBI, cBI};
                return res__Arr_int;
        }
        private int[] mixing_array(int count_color) {
                int[] res__Arr_int = new int[count_color];

                for (int i = 0; i < count_color; i++) {
                        res__Arr_int[i] = i;
                }

                for (int i = 0; i < count_color; i++) {
                        int from_key = abs(sharedRandom.nextInt()) % count_color;
                        int to_key = abs(sharedRandom.nextInt()) % count_color;


                        int from_value = res__Arr_int[from_key];
                        int to_value = res__Arr_int[to_key];


                        res__Arr_int[to_key] = from_value;
                        res__Arr_int[from_key] = to_value;
                }


                return res__Arr_int;
        }



}
