import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class Main {

    static Integer i;

    int[] test = {1};

    public static void main(String[] args) throws IOException {









/*

        FileOutputStream file = new FileOutputStream("./test_files/main.bin");

        file.write(ByteBuffer.allocate(4).putInt(555362644).array());
        file.close();
*/


//        fileWithMetadataArchive2.writeObject(intege);

        //array int text file conversion in bin file

        //permutation BWT

        //bin file with int array conversion in F012


        //Move-to-Front (Stack of books)
        //  hoffman code tree
        //  F012
    }
    private static String byteToBinareView(byte numberByte) {
        return String.format("%8s", Integer.toBinaryString(numberByte & 0xFF)).replace(' ', '0');
    }
}
