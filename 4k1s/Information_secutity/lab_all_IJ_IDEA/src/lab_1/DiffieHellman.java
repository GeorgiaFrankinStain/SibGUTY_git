package lab_1;

import java.math.BigInteger;
import java.util.Random;

public class DiffieHellman {
    final BigInteger RANGE_BI = BigInteger.valueOf(1000);

    private BigInteger shared_secret_keyBI;
    private BigInteger personal_secret_keyBI;




    public DiffieHellman() {
        this.personal_secret_keyBI = generate_personal_secret_key();
       // System.out.println(this.personal_secret_keyBI); //DEBUG_DELETE
    }
    private BigInteger generate_personal_secret_key() {
        BigInteger keyBI;
        boolean keyBI__more_then_1_BOOL;

        do {
            Random tRandom = new Random();
            BigInteger randBI = new BigInteger(this.RANGE_BI.bitLength(), tRandom);
            keyBI = randBI.mod(this.RANGE_BI);

            keyBI__more_then_1_BOOL = keyBI.compareTo(BigInteger.ONE) == 1;
        } while (!keyBI__more_then_1_BOOL);

        return keyBI;
    }




    private BigInteger gBI;
    private BigInteger pBI;
    private BigInteger shell_personal_secret_keyBI;

    public void initialize_data_encrypted_connect() {

        P_and_G tP_and_G = generate_shared_data_pg();
        this.gBI = tP_and_G.gBI;
        this.pBI = tP_and_G.pBI;
        this.shell_personal_secret_keyBI = FastExponentiationModulo.get(
                this.gBI,
                this.personal_secret_keyBI,
                this.pBI
        );




        //DEBUG_BLOCK_DELETE_start
        // System.out.println("DiffieHellman{" +
        //         "gBI=" + gBI +
        //         ", pBI=" + pBI +
        //         ", shell_personal_secret_keyBI=" + shell_personal_secret_keyBI +
        //         '}');
        //DEBUG_BLOCK_DELETE_end
    }
    private class P_and_G {
        public P_and_G(BigInteger gBI, BigInteger pBI) {
            this.gBI = gBI;
            this.pBI = pBI;
        }

        public BigInteger gBI;
        public BigInteger pBI;
    }
    private P_and_G generate_shared_data_pg() {
        BigInteger pBI, qBI;
        do {
            pBI = generate_rand_prime_number();
            qBI = pBI.multiply(BigInteger.valueOf(2)).add(BigInteger.ONE);
        } while (!is_prime_number_BOOL(qBI));




        BigInteger rand_modBI;
        boolean number_is_pseudoprime_BOOL;

        do {
            Random tRandom = new Random();
            BigInteger randBI = new BigInteger(this.RANGE_BI.bitLength(), tRandom);
            randBI = randBI.add(BigInteger.ONE);
            rand_modBI = randBI.mod(this.RANGE_BI);




            boolean fast_exponent_modulo_equal_1_BOOL =
                    FastExponentiationModulo.get(rand_modBI, qBI, pBI).equals(BigInteger.ONE);
            number_is_pseudoprime_BOOL =
                    rand_modBI.equals(BigInteger.ONE)
                            && !fast_exponent_modulo_equal_1_BOOL;

        } while (number_is_pseudoprime_BOOL
                || rand_modBI.equals(BigInteger.ZERO));

        BigInteger gBI = rand_modBI;



        P_and_G res__P_and_G = new P_and_G(gBI, pBI);
        return  res__P_and_G;
    }
    private boolean is_prime_number_BOOL(BigInteger numberBI) {

        boolean numberBI_less_or_equal_than_0_BOOL =
                numberBI.compareTo(BigInteger.ONE) <= 0;

        if (numberBI_less_or_equal_than_0_BOOL) {
            return false;
        }




        BigInteger sqrt_numberEBI = SqrtBigInteger.get(numberBI);
        for (BigInteger iBI = BigInteger.valueOf(2);
             iBI.compareTo(sqrt_numberEBI) <= 0; //i <= sqrt
             iBI = iBI.add(BigInteger.ONE)) {

            boolean remainder_of_division_equal_0_BOOL =
                    (numberBI.mod(iBI)).compareTo(BigInteger.ZERO) == 0;
            if (remainder_of_division_equal_0_BOOL) {
                return false;
            }
        }




        return true;
    }
    private BigInteger generate_rand_prime_number() {
        BigInteger rand_modBI;

        do {
            Random tRandom = new Random();
            BigInteger randBI = new BigInteger(this.RANGE_BI.bitLength(), tRandom);
            rand_modBI = randBI.mod(this.RANGE_BI);
        } while (!is_prime_number_BOOL(rand_modBI));




        return rand_modBI;
    }




    public BigInteger[] data_for_opponent_connect() {
        //LINK_AIhQdlWabQ
        BigInteger[] data_for_Shared_key_generateArrBI = {
                this.gBI,
                this.pBI,
                this.shell_personal_secret_keyBI
        };

        return data_for_Shared_key_generateArrBI;
    }


    public void to_connect_encrypted_connect(
            BigInteger[] data_for_Shared_key_generateArrBI) {

        //LINK_AIhQdlWabQ
        this.gBI = data_for_Shared_key_generateArrBI[0];
        this.pBI = data_for_Shared_key_generateArrBI[1];
        BigInteger opponen_shell_pers_secrt_keyBI = data_for_Shared_key_generateArrBI[2];
        this.shell_personal_secret_keyBI = FastExponentiationModulo.get(
                this.gBI,
                this.personal_secret_keyBI,
                this.pBI
        );




        this.shared_secret_keyBI = FastExponentiationModulo.get(
                opponen_shell_pers_secrt_keyBI,
                this.personal_secret_keyBI,
                this.pBI
        );
    }








    public BigInteger getShared_secret_keyBI() {
        return shared_secret_keyBI;
    }
}
