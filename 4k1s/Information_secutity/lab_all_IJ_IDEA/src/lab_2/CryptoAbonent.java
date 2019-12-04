package lab_2;

import java.math.BigInteger;

public interface CryptoAbonent {
void _self_send_create_shared_data(CryptoAbonent communicatorCryptoAbonent) throws Exception;

void _set_receiv_shared_data(Object input_key__byte) throws Exception;

public BigInteger encrypt(BigInteger material_encrypt__BI,
                          CryptoAbonent communicatorCryptoAbonent) throws Exception;


public BigInteger decrypt(BigInteger material_decript__BI) throws Exception;


public default void file_encrypt(String input_file__Str,
                                 String output_file__Str,
                                 CryptoAbonent communicatorCryptoAbonent) {
        System.out.println("default");
        //FIXME !1!
}

public default void file_decrypt(String input_file__Str,
                                 String output_file__Str) {
        //FIXME !1!
}

;
}
