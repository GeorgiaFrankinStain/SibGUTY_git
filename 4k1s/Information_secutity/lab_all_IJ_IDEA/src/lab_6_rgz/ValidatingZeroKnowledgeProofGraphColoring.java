package lab_6_rgz;

import javafx.util.Pair;

import java.math.BigInteger;
import java.util.Random;

import static java.lang.Math.abs;

public class ValidatingZeroKnowledgeProofGraphColoring {

        private Graph ishodniiGraph;

        public ValidatingZeroKnowledgeProofGraphColoring(Graph ishodniiGraph) {
                this.ishodniiGraph = ishodniiGraph;
        }

        public boolean validation(
                        int count_validations,
                        ProvingZeroKnowledgeProofGraphColoring tProvZerKnowProofGraphColor
        ) throws Exception {

                for (int i = 0; i < count_validations; i++) {

                        BigInteger[][] ecrypt_graph__ArrArrBI = tProvZerKnowProofGraphColor.get_crypt_graph();
                        Pair<Integer, Integer> rand_edge__PairIntInt =
                                        choice_of_random_edge(this.ishodniiGraph);
                        System.out.println(rand_edge__PairIntInt.getKey() + " ---- " + rand_edge__PairIntInt.getValue()); //DEBUG_DELETE
                        BigInteger[] secret_keys__ArrBI =
                                        tProvZerKnowProofGraphColor.sending_secret_keys_for_vertices_around_given_edge(
                                                        rand_edge__PairIntInt
                                        );


                        BigInteger[] crypt_keys_of_1_node__ArrBI = ecrypt_graph__ArrArrBI[rand_edge__PairIntInt.getKey()];
                        BigInteger[] crypt_keys_of_2_node__ArrBI = ecrypt_graph__ArrArrBI[rand_edge__PairIntInt.getValue()];



                        BigInteger one_n__BI = crypt_keys_of_1_node__ArrBI[0];
                        BigInteger one_d__BI = crypt_keys_of_1_node__ArrBI[1];
                        BigInteger one_z__BI = crypt_keys_of_1_node__ArrBI[2];
                        BigInteger one_c__BI = secret_keys__ArrBI[0];


                        BigInteger two_n__BI = crypt_keys_of_2_node__ArrBI[0];
                        BigInteger two_d__BI = crypt_keys_of_2_node__ArrBI[1];
                        BigInteger two_z__BI = crypt_keys_of_2_node__ArrBI[2];
                        BigInteger two_c__BI = secret_keys__ArrBI[1];


                        BigInteger one_color_and_musor__BI = one_z__BI.modPow(one_c__BI, one_n__BI);
                        BigInteger two_color_and_musor__BI = two_z__BI.modPow(two_c__BI, two_n__BI);

                        int size_bit_write = tProvZerKnowProofGraphColor.getSize_bit_write();
                        BigInteger cut_musor_bits__BI = BigInteger.valueOf(2).modPow(
                                        BigInteger.valueOf(size_bit_write),
                                        one_n__BI
                        ); //FIXME по хорошему бы еще единичку приплюсовать
                        BigInteger one_color__BI = one_color_and_musor__BI.mod(cut_musor_bits__BI);
                        BigInteger two_color__BI = two_color_and_musor__BI.mod(cut_musor_bits__BI);


                        boolean the_colors_of_the_neighboring_vertices_are_equal =
                                        one_color__BI.equals(two_color__BI);
                        if (the_colors_of_the_neighboring_vertices_are_equal)
                                return false;
                }

                return true;

        }
        private Pair<Integer, Integer> choice_of_random_edge(Graph inGraph) {
                Random tRandom = new Random();
                int number = abs(tRandom.nextInt()) % inGraph.edgesArrayListPairIntInt.size();
                return inGraph.edgesArrayListPairIntInt.get(number);
        }
}
