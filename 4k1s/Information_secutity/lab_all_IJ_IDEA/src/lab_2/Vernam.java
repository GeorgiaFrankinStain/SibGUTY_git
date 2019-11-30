package lab_2;


import lab_1.MyBigInteger;
import lab_1.PrimeNumber;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Vernam implements CryptoAbonent  {
    BigInteger glob_range__BI = BigInteger.valueOf(100000000);
    private BigInteger keyBI = null;




    public void _self_send_create_shared_data(CryptoAbonent communicatorCryptoAbonent) throws Exception {
        boolean keyBI__not_assigned__BOOL = this.keyBI == null;
        if (keyBI__not_assigned__BOOL) {
            Random tRandom = new Random();
            this.keyBI = new BigInteger(glob_range__BI.bitLength(), tRandom);
        }


        communicatorCryptoAbonent._set_receiv_shared_data(this.keyBI);
    }
    @Override
    public void _set_receiv_shared_data(Object input_key__byte) {
        //TODO: null test input_key__byte
        this.keyBI = (BigInteger) input_key__byte;
    }


    @Override
    public BigInteger encrypt(BigInteger material_encrypt__BI, CryptoAbonent communicatorCryptoAbonent) throws Exception {
        _self_send_create_shared_data(communicatorCryptoAbonent);


        return material_encrypt__BI.xor(this.keyBI);
    }

    @Override
    public BigInteger decrypt(BigInteger material_decript__BI) {
        if (this.keyBI == null){
            throw new NullPointerException("Connect no initialize");
        }

        return material_decript__BI.xor(this.keyBI);
    }
}