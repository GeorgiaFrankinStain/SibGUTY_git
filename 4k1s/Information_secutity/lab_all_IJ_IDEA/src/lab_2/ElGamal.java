package lab_2;

import lab_1.MyBigInteger;
import lab_1.PrimeNumber;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class ElGamal implements CryptoAbonent {
    final int range_for_biginteger = 10000;
    final BigInteger glob_range__BI = BigInteger.valueOf(range_for_biginteger);

    private BigInteger pBI, gBI, my_pub_key__BI, key_pub_of_interlocutor__BI;
    private BigInteger my_secret_key__BI;


    @Override
    public void _self_send_create_shared_data(CryptoAbonent communicatorCryptoAbonent) throws Exception {
        this.pBI = PrimeNumber.generate(glob_range__BI);


        Random tRandom = new Random();
        this.gBI = new BigInteger(glob_range__BI.bitLength(), tRandom).mod(this.pBI.subtract(BigInteger.valueOf(2)));


        //секретный ключ
        // 1 < my_secret_key__BI < p - 1
        this.my_secret_key__BI = MyBigInteger.generae_diapason(
                BigInteger.valueOf(2),
                this.pBI.subtract(BigInteger.ONE));



        //публичный ключ
        this.my_pub_key__BI = this.gBI.modPow(my_secret_key__BI, pBI);



        //отправляем публичный ключ и общие данные
        ArrayList<BigInteger> sending_shared__ArrListBI = new ArrayList<BigInteger>();
        sending_shared__ArrListBI.add(this.gBI);
        sending_shared__ArrListBI.add(this.pBI);
        sending_shared__ArrListBI.add(this.my_pub_key__BI);
        communicatorCryptoAbonent._set_receiv_shared_data(sending_shared__ArrListBI);

    }

    @Override
    public void _set_receiv_shared_data(Object input_shared_data__Object) {
        ArrayList<BigInteger> input_shared_data__ArrLIstBI = (ArrayList<BigInteger>) input_shared_data__Object;
		this.gBI = input_shared_data__ArrLIstBI.get(0);
		this.pBI = input_shared_data__ArrLIstBI.get(1);
		this.key_pub_of_interlocutor__BI = input_shared_data__ArrLIstBI.get(2);
    }

    @Override
    public BigInteger encrypt(BigInteger material_encrypt__BI, CryptoAbonent communicatorCryptoAbonent) {
        return null;
    }
    public ArrayList<BigInteger> encrypt(BigInteger material_encrypt__BI) throws Exception {
        // 1 <= k <= p - 2
        BigInteger kBI = MyBigInteger.generae_diapason(
                BigInteger.valueOf(1),
                this.pBI.subtract(BigInteger.valueOf(2))
        );


        // r = g^k mod p
        BigInteger rBI = this.gBI.modPow(kBI, this.pBI);

        // m * key_pub_of_interlocutor^k mod p
        BigInteger m_crypt__BI = material_encrypt__BI.multiply(key_pub_of_interlocutor__BI.modPow(kBI, this.pBI));

        ArrayList<BigInteger> resArrListBI = new ArrayList<BigInteger>();
        resArrListBI.add(rBI);
        resArrListBI.add(m_crypt__BI);

        return resArrListBI;
    }


    @Override
    public BigInteger decrypt(BigInteger material_decript__BI) {
        return null;
    }
    public BigInteger decrypt(ArrayList<BigInteger> r_and_mes_crypt__ArrListBI) {
        BigInteger rBI = r_and_mes_crypt__ArrListBI.get(0);
        BigInteger mes_crypt__BI = r_and_mes_crypt__ArrListBI.get(1);

        //mes_crypt * r^(p - 1 - my_secret_key) mod p
        BigInteger p_m_1_m_my_secret_key__BI = this.pBI.subtract(BigInteger.ONE).subtract(my_secret_key__BI);
        BigInteger mesBI = mes_crypt__BI.multiply(rBI.modPow(p_m_1_m_my_secret_key__BI, this.pBI)).mod(this.pBI);

        return mesBI;
    }
}
