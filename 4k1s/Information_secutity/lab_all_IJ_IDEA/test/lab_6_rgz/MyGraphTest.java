package lab_6_rgz;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class MyGraphTest {

        @Test
        public void sharedTEST() throws IOException, InterruptedException {
                Graph tGraph = MyGraph.generate_random_graph(18);
                File output_dot__File = new File("tests_files/lab_6_rgz/print_to_file_test.dot");
                File image_output__File = new File("tests_files/lab_6_rgz/print_to_file_test.png");
                tGraph.coloring_graph();
                tGraph.print_to_file_dot(output_dot__File);
//                System.out.println(output_dot__File.getCanonicalPath());
//                System.out.println();
                Graph.create_image(output_dot__File.getAbsolutePath(), image_output__File.getAbsolutePath());
        }
}