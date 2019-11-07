import java.math.BigInteger;
import java.util.Random;

/**
 * Created by GeorgiaFrankinStain on 2019.11.05 11:05:29
 *
 * Функция построения общего ключа для двух абонентов по 
 * схеме Диффи-хелмана.
 */
public class DiffieHellman {
    public DiffieHellman(BigInteger range_of_all_numberBI) {
        this.range_of_all_numberBI = range_of_all_numberBI;
    }

    private BigInteger range_of_all_numberBI;




    public static boolean FOR_TEST___is_prime_number_BOOL(BigInteger numberBI) {
	    return is_prime_number_BOOL(numberBI);
    }




    private static boolean is_prime_number_BOOL(BigInteger numberBI) {
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




    public static BigInteger generate_rand_prime_number(BigInteger rangeBI) {
        BigInteger rand_modBI;

	    do {
            Random tRandom = new Random();
            BigInteger randBI = new BigInteger(rangeBI.bitLength(), tRandom);
            rand_modBI = randBI.mod(randBI);
        } while (!is_prime_number_BOOL(rand_modBI));




	    return rand_modBI;
    }




    public static BigInteger[] generate_pqg(BigInteger rangeBI) {
        BigInteger pBI, qBI;
        do {
            pBI = generate_rand_prime_number(rangeBI);
            qBI = pBI.multiply(BigInteger.valueOf(2)).add(BigInteger.ONE);
        } while (!is_prime_number_BOOL(qBI));




        BigInteger rand_modBI;
        boolean number_is_pseudoprime_BOOL;

        do {
            Random tRandom = new Random();
            BigInteger randBI = new BigInteger(rangeBI.bitLength(), tRandom);
            rand_modBI = randBI.mod(randBI);




            boolean fast_exponent_modulo_equal_1_BOOL =
                    FastExponentiationModulo.get(rand_modBI, qBI, pBI).equals(BigInteger.ONE);
            number_is_pseudoprime_BOOL =
                    rand_modBI.equals(BigInteger.ONE)
                    && !fast_exponent_modulo_equal_1_BOOL;

        } while (number_is_pseudoprime_BOOL);

        BigInteger gBI = rand_modBI;




        BigInteger[] resArrBI = {pBI, qBI, gBI};

        return resArrBI;
    }




    private BigInteger generate_my_private_key() {
        BigInteger rangeBI = this.range_of_all_numberBI;




	    BigInteger keyBI;
        boolean keyBI__more_then_1_BOOL;

        do {
            Random tRandom = new Random();
            BigInteger randBI = new BigInteger(rangeBI.bitLength(), tRandom);
            keyBI = randBI.mod(randBI);

            keyBI__more_then_1_BOOL = keyBI.compareTo(BigInteger.ONE) == 1;
        } while (keyBI__more_then_1_BOOL);

        return keyBI;
    }


/*

    private
        BigInteger my_secret_keyBI;
	    BigInteger shared_secret_keyBI;
    private
        BigInteger shared_mod_numberBI;

*/

    /**
     *
     * @return BigInteger[0] - P (for operation Mod P)
     * @return BigInteger[1] - g (generator)
     * @return BigInteger[2] - additional number on the basis of senders personal secret number
     */
    private BigInteger[] generate_public_data_for_naparnik (BigInteger my_private_keyBI) {

        BigInteger[] tempArrBI = generate_pqg(this.range_of_all_numberBI);
        BigInteger pBI = tempArrBI[0];
        BigInteger gBI = tempArrBI[2];




        BigInteger additionalBI = FastExponentiationModulo.get(gBI, my_private_keyBI, pBI);



        BigInteger[] resArrBI = {pBI, additionalBI};
        return resArrBI;
    }
    private BigInteger[] generate_public_data_for_naparnik (
    		BigInteger my_private_keyBI,
    		BigInteger gBI,
    		BigInteger pBI) {

        BigInteger additionalBI = FastExponentiationModulo.get(gBI, my_private_keyBI, pBI);



        BigInteger[] resArrBI = {pBI, additionalBI};
        return resArrBI;
    }




    private BigInteger generate_shared_secret_key (
            BigInteger naparnick_keyBI,
            BigInteger my_secret_keyBI,
            BigInteger shared_mod_numberBI) {

        return FastExponentiationModulo.get(
                naparnick_keyBI,
                my_secret_keyBI,
                shared_mod_numberBI);
    }


    public static void main(String[] args) {
        BigInteger send_secret_keyBI = generate_my_private_key();


        BigInteger[] tempArrBI = generate_public_data_for_naparnik (
        		send_secret_keyBI);
        






		BigInteger p_for_modBI = 
				send_to_get_1_shared_dataArrBI[0];
		BigInteger gBI = 
				send_to_get_1_shared_dataArrBI[1];
		BigInteger additional_numb_on_basis_of_senders_secret_numberBI = 
				send_to_get_1_shared_dataArrBI[2];


        BigInteger getter_secret_keyBI = generate_my_private_key();


        BigInteger getter_shared_secret_keyBI = generate_shared_secret_key(
        		additional_numb_on_basis_of_senders_secret_numberBI,
        		getter_secret_keyBI,
        		p_for_modBI);


        BigInteger[] get_to_set_1ArrBI = generate_public_data_for_naparnik(
        		getter_secret_keyBI,
        		gBI,
        		p_for_modBI);








		// BigInteger p_for_modBI = 
		// 		get_to_set_1ArrBI[0];
		BigInteger additional_numb_on_basis_of_getters_secret_numberBI = 
				get_to_set_1ArrBI[2];
		generate_shared_secret_key(
				additional_numb_on_basis_of_getters_secret_numberBI,
				send_secret_keyBI,
				p_for_modBI);
        //отправитель генерирует общее секретное число на основе публичных данных от напарника
    }
}
