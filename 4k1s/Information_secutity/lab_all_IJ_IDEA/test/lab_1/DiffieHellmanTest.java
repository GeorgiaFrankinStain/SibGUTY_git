package lab_1;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DiffieHellmanTest {

    @Test
    public void to_connect_encrypted_connect() {

        for (int i = 0; i < 7; i++) {
            {
                DiffieHellman aliceDH = new DiffieHellman();
                DiffieHellman bobDH = new DiffieHellman();


                aliceDH.initialize_data_encrypted_connect();
                BigInteger[] data_for_connect_for_bob_ArrBI =
                        aliceDH.data_for_opponent_connect();
                System.out.println(Arrays.toString(data_for_connect_for_bob_ArrBI)); //DEBUG_DELETE
                bobDH.to_connect_encrypted_connect(data_for_connect_for_bob_ArrBI);


                BigInteger[] data_for_connect_for_alice_ArrBI =
                        bobDH.data_for_opponent_connect();
                System.out.println(Arrays.toString(data_for_connect_for_alice_ArrBI)); //DEBUG_DELETE
                aliceDH.to_connect_encrypted_connect(data_for_connect_for_alice_ArrBI);



                System.out.println(bobDH.getShared_secret_keyBI());
                System.out.println(aliceDH.getShared_secret_keyBI());

                assertEquals(
                        bobDH.getShared_secret_keyBI(),
                        aliceDH.getShared_secret_keyBI()
                );
            }
        }
    }
}