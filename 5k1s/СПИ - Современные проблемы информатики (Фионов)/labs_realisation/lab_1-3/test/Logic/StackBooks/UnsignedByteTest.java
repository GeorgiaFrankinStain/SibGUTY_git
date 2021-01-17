package Logic.StackBooks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnsignedByteTest {

    @Test
    void setGetInt_fori() {
        for (int i = 0; i < 255; i++) {
            byte code = UnsignedByte.toSignedByte(i);
            assertEquals(i, UnsignedByte.getInt(code));
        }
    }
}