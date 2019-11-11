import java.math.BigInteger;
import java.util.Arrays;

public class Runner {
    public static void main(String[] args) {
//        task3();
        task4();
    }




    public static void task3() {
        //TASK 3

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
    }

    public static void task4() {

    }
}
