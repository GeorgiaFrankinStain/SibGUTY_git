package lab_6_rgz;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class GraphTest {
        @Test
        public void sharedTEST() throws Exception {
                Graph tGraph = Graph.generate_random_graph(5);
                File output_dot__File = new File("tests_files/lab_6_rgz/print_to_file_test.dot");
                File image_output__File = new File("tests_files/lab_6_rgz/print_to_file_test.png");


                ValidatingZeroKnowledgeProofGraphColoring tValZeroKnowProofGraphColor =
                                new ValidatingZeroKnowledgeProofGraphColoring(tGraph);


                tGraph.coloring_graph();
                Graph coloringGraph = tGraph;
                coloringGraph.print_to_file_dot(output_dot__File);
                Graph.create_image(output_dot__File.getAbsolutePath(), image_output__File.getAbsolutePath());



                ProvingZeroKnowledgeProofGraphColoring tProvZeroKnowProofGraphColor =
                                new ProvingZeroKnowledgeProofGraphColoring(coloringGraph);

                assertTrue(tValZeroKnowProofGraphColor.validation(1, tProvZeroKnowProofGraphColor));

        }
}