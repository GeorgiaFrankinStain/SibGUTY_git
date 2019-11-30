package lab_2;

import lab_1.MyBigInteger;
import lab_1.PrimeNumber;

import java.math.BigInteger;

public class AdiShamir implements CryptoAbonent {
    final int range_for_biginteger = 10000;
    final BigInteger glob_range__BI = BigInteger.valueOf(range_for_biginteger);


    private BigInteger secret_key_c__BI, secret_key_d__BI;
    private BigInteger pBI;

    @Override
    public void _self_send_create_shared_data(CryptoAbonent communicatorCryptoAbonent) throws Exception {
        this.pBI = PrimeNumber.generate(glob_range__BI);

        communicatorCryptoAbonent._set_receiv_shared_data(this.pBI);

        this.secret_key_c__BI = PrimeNumber.coprime_generate(this.pBI.subtract(BigInteger.ONE), glob_range__BI);

        this.secret_key_d__BI = this.secret_key_c__BI.modInverse(this.pBI.subtract(BigInteger.ONE));
    }

    @Override
    public void _set_receiv_shared_data(Object input_p__Object) throws Exception {
        this.pBI = (BigInteger) input_p__Object;


        this.secret_key_c__BI = PrimeNumber.coprime_generate(this.pBI.subtract(BigInteger.ONE), glob_range__BI);

        this.secret_key_d__BI = this.secret_key_c__BI.modInverse(this.pBI.subtract(BigInteger.ONE));
    }

    @Override
    public BigInteger encrypt(BigInteger material_encrypt__BI, CryptoAbonent communicatorCryptoAbonent) {
        AdiShamir communicatorAdiShamir = (AdiShamir) communicatorCryptoAbonent;
        BigInteger x1BI = material_encrypt__BI.modPow(this.secret_key_c__BI, this.pBI);
        BigInteger x2BI = communicatorAdiShamir.step_x2(x1BI);
        BigInteger x3BI = x2BI.modPow(this.secret_key_d__BI, this.pBI);

        return x3BI;
    }

    public BigInteger step_x2 (BigInteger x1BI) {
        BigInteger x2BI = x1BI.modPow(this.secret_key_c__BI, this.pBI);
        return x2BI;
    }

    @Override
    public BigInteger decrypt(BigInteger material_decript__BI) {
        BigInteger x4BI = material_decript__BI.modPow(this.secret_key_d__BI, this.pBI);
        return x4BI;
    }
}
