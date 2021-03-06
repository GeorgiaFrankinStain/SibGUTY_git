package Logic.StackBooks;

import DataInputOut.InputFileBits;
import DataInputOut.InputFileBitsClass;
import Logic.RiStackBooks.StackBooks;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StackBooksTest {
    private String rootPath = "./test_files/";


    @Test
    void sharedTest() throws Exception {
        String dataPath = rootPath + "35dwt2ty2_data.bin";

        byte[] expectedArray = {1, 127, 120, -128, 0};
        Files.write(Path.of((new File(dataPath)).getAbsolutePath()), expectedArray);


        StackBooks archive = new ByteStackBooksClass();
        String codePath = rootPath + "35dwt2ty2_code.bin";
        archive.code(dataPath, codePath);
        String decodePath = rootPath + "35dwt2ty2_decode.bin";
        archive.decode(codePath, decodePath);


        byte[] actual = Files.readAllBytes(Path.of((new File(decodePath)).getAbsolutePath()));

        assertArrayEquals(expectedArray, actual);
        assert(Arrays.equals(expectedArray, actual));
    }
    @Test
    void sharedTest_oneNumber() throws Exception {
        String randomName = "35dwt2ty2246";
        String dataPath = rootPath + randomName + "_data.bin";

        byte[] expectedArray = {3};
        Files.write(Path.of((new File(dataPath)).getAbsolutePath()), expectedArray);


        StackBooks archive = new ByteStackBooksClass();
        String codePath = rootPath + randomName + "_code.bin";
        archive.code(dataPath, codePath);
        String decodePath = rootPath + randomName + "_decode.bin";
        archive.decode(codePath, decodePath);


        byte[] actual = Files.readAllBytes(Path.of((new File(decodePath)).getAbsolutePath()));

        assertArrayEquals(expectedArray, actual);
        assert(Arrays.equals(expectedArray, actual));
    }
    @Test
    void sharedTest_threeNumber() throws Exception {
        String randomName = "35dwt2ty2246";
        String dataPath = rootPath + randomName + "_data.bin";

        byte[] expectedArray = {3, 15, 40};
        Files.write(Path.of((new File(dataPath)).getAbsolutePath()), expectedArray);


        StackBooks archive = new ByteStackBooksClass();
        String codePath = rootPath + randomName + "_code.bin";
        archive.code(dataPath, codePath);
        String decodePath = rootPath + randomName + "_decode.bin";
        archive.decode(codePath, decodePath);


        byte[] actual = Files.readAllBytes(Path.of((new File(decodePath)).getAbsolutePath()));

        assertArrayEquals(expectedArray, actual);
        assert(Arrays.equals(expectedArray, actual));
    }
}