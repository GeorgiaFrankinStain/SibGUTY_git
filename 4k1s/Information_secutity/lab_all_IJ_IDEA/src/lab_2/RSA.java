package lab_2;

import lab_1.ExtendedGCD;
import lab_1.MyBigInteger;
import lab_1.PrimeNumber;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class RSA implements CryptoAbonent {
    private BigInteger p1BI = null, p2BI = null, fi_from_n__BI = null, cBI = null;
    private BigInteger dBI = null, nBI = null; //публичные, отправляемые, это является замком, который всем отправляют
    final int range_for_biginteger = 10000;
    final BigInteger glob_range__BI = BigInteger.valueOf(range_for_biginteger);


    public BigInteger getdBI() {
        return dBI;
    }

    public BigInteger getnBI() {
        return nBI;
    }

    public void _self_send_create_shared_data(CryptoAbonent communicatorCryptoAbonent) throws Exception {
        BigInteger max_diapason__BI = BigInteger.valueOf(range_for_biginteger);
        this.p1BI = PrimeNumber.generate(max_diapason__BI);


        boolean p2BI__equals__p2BI__BOOL;
        do {
            this.p2BI = PrimeNumber.generate(max_diapason__BI);
            p2BI__equals__p2BI__BOOL = p1BI.compareTo(this.p2BI) == 0;
        } while (p2BI__equals__p2BI__BOOL);


        this.nBI = p1BI.multiply(p2BI);


        BigInteger p1_minus_1__BI = p1BI.subtract(BigInteger.ONE);
        BigInteger p2_minus_1__BI = p2BI.subtract(BigInteger.ONE);
        this.fi_from_n__BI = p1_minus_1__BI.multiply(p2_minus_1__BI);


        //GCD(dBI, fi_from_n__BI) = 1
        this.dBI = PrimeNumber.coprime_generate(fi_from_n__BI, glob_range__BI); //публичное: e - rand() нечетное и НОД(fi_from_n, e) == 1



        //cd mod fi = 1
        this.cBI = this.dBI.modInverse(fi_from_n__BI);


        ArrayList<BigInteger> sending_d_and_n__ArrListBI = new ArrayList<BigInteger>();
        sending_d_and_n__ArrListBI.add(dBI);
        sending_d_and_n__ArrListBI.add(nBI);


        if (communicatorCryptoAbonent != null) {
            communicatorCryptoAbonent._set_receiv_shared_data(sending_d_and_n__ArrListBI);
        }
    }


    @Override
    public void _set_receiv_shared_data(Object input_shared_data__Object) {
        ArrayList<BigInteger> input_shared_data__ArrLIstBI = (ArrayList<BigInteger>) input_shared_data__Object;
        this.dBI = input_shared_data__ArrLIstBI.get(0);
        this.nBI = input_shared_data__ArrLIstBI.get(1);
    }




    @Override
    public BigInteger encrypt(BigInteger material_encrypt__BI, CryptoAbonent communicatorCryptoAbonent) {
        //mes_crypt = mes^e mod n
        BigInteger material_for_encrypt__BI = material_encrypt__BI;
        BigInteger mes_crypt__BI = material_for_encrypt__BI.modPow(this.dBI, this.nBI);
        return mes_crypt__BI;
    }

    @Override
    public BigInteger decrypt(BigInteger material_decript__BI) throws Exception {
        //проверка, что мы являемся отправителями замка
        boolean we_are_the_senders_lock__BOOL = p1BI != null; //если у нас есть секретные данные, то мы отрпавители (плохая проверка)
        if (!we_are_the_senders_lock__BOOL) {
            throw new Exception("we not are the senders lock");
        }

        //mes = mes_cript^d mod n
        return material_decript__BI.modPow(this.cBI, this.nBI);
    }







    public BigInteger secret_key_encrypt(BigInteger material_for_crypt__BI) {
        return material_for_crypt__BI.modPow(this.cBI, this.nBI);
    }
    static public BigInteger public_key_compensation_secret_key(
            BigInteger material_for_decrypt__BI,
            BigInteger d_of_verifiable__BI,
            BigInteger n_of_verifiable__BI
    ) {
        return material_for_decrypt__BI.modPow(d_of_verifiable__BI, n_of_verifiable__BI);
    }
}
