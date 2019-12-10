package lab_3;

import com.sun.org.apache.bcel.internal.generic.BIPUSH;
import lab_1.MyBigInteger;
import lab_1.PrimeNumber;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Gost {
        private BigInteger range256__BI = BigInteger.ONE.shiftLeft(25).subtract(BigInteger.ONE);
        private BigInteger range1024__BI = BigInteger.ONE.shiftLeft(102).subtract(BigInteger.ONE);
        private BigInteger pBI, qBI, aBI; //public of group
        private BigInteger xBI; //personal secret key
        private BigInteger yBI; //personal public key

        public BigInteger getyBI() {
                return yBI;
        }

        public Gost(BigInteger pBI, BigInteger qBI, BigInteger aBI) throws Exception {
                this.pBI = pBI;
                this.qBI = qBI;
                this.aBI = aBI;

                generation_personal_data();
        }

        public Gost() throws Exception {
                generation_group_data();
                generation_personal_data();
        }

        public BigInteger[] get_group_data() {
                BigInteger[] resArrBI = {this.pBI, this.qBI, this.aBI};
                return resArrBI;
        }

        private void generation_personal_data() throws Exception {
                this.xBI = MyBigInteger.generae_diapason(BigInteger.ONE, range256__BI); // 0 < x < q
                // this.xBI = BigInteger.valueOf(6); //DEBUG_DELETE
                this.yBI = this.aBI.modPow(this.xBI, this.pBI); // y = a^x mod p
        }

        private void generation_group_data() throws Exception {


                BigInteger temp_q__BI;
                BigInteger temp_p__BI = null;

                boolean p_less_than_1024bit__BOOL;
                boolean p_is_prime__BOOL;
                boolean p_and_q_is_prime__BOOL;
                do {
                        //p = bq + 1, b - целое число
                        // a^q mod p = 1
                        temp_q__BI = PrimeNumber.generate(range256__BI);

                        //циклом прибавляем q пока не достигнем потолка
                        temp_p__BI = temp_q__BI.add(BigInteger.ONE);

                        do {
                                temp_p__BI = temp_p__BI.add(temp_q__BI); //p = bq + 1, b - целое число
                                p_is_prime__BOOL = PrimeNumber.is_prime(temp_p__BI);
                                if (p_is_prime__BOOL) {
                                        break;
                                }
                        } while (temp_p__BI.compareTo(range1024__BI) == -1);

                        p_and_q_is_prime__BOOL = p_is_prime__BOOL;
                } while (!p_and_q_is_prime__BOOL);
                this.pBI = temp_p__BI;
                this.qBI = temp_q__BI;
                // this.qBI = BigInteger.valueOf(11); //DEBUG_DELETE
                // this.pBI = BigInteger.valueOf(67); //DEBUG_DELETE

                generate_group_parametr_a(); // a^q mod p = 1
                // this.aBI = BigInteger.valueOf(25); //DEBUG_DELETE
        }

        private void generate_group_parametr_a() throws Exception {
                BigInteger temp_a__BI;
                boolean a_more_than_1__BOOL;
                do {
                        //g - случайное, g > 1
                        BigInteger gBI = MyBigInteger.generae_diapason(
                                        BigInteger.valueOf(2),
                                        this.range1024__BI
                        );

                        BigInteger p_m_1_del_q = this.pBI.subtract(BigInteger.ONE).divide(this.qBI);
                        temp_a__BI = gBI.modPow(p_m_1_del_q, this.pBI);
                        a_more_than_1__BOOL = temp_a__BI.compareTo(BigInteger.ONE) == 1;
                } while (!a_more_than_1__BOOL); // если a > 1, то все ок

                this.aBI = temp_a__BI;
        }

        public void create_digital_signature(
                        File messageFile,
                        File place_of_digital_signature__File
        ) throws Exception {
                MessageDigest md5Digest = MessageDigest.getInstance("MD5");
                byte[] checksum__Arr_byte = getFileChecksum(md5Digest, messageFile);
                BigInteger checksumBI = new BigInteger(checksum__Arr_byte);
                // checksumBI = BigInteger.valueOf(3); //DEBUG_DELETE




                BigInteger kBI, rBI, sBI;
                while (true) {
                        kBI = MyBigInteger.generae_diapason(BigInteger.ONE, range256__BI); //k, 0 < k < q
                        // kBI = BigInteger.valueOf(8); //DEBUG_DELETE

                        //r = (a^k mod p) mod q; если r == 0, то возвращаемся к шагу 2
                        rBI = aBI.modPow(kBI, this.pBI).mod(this.qBI);
                        if (rBI.compareTo(BigInteger.ZERO) == 0) {
                                continue;
                        }


                        //s = (kh + xr) mod q; если s == 0, то возвращаемся к шагу 2
                        //(kh + xr)
                        BigInteger khBI = kBI.multiply(checksumBI);
                        BigInteger xrBI = this.xBI.multiply(rBI);
                        sBI = khBI.add(xrBI).mod(this.qBI);
                        if (sBI.compareTo(BigInteger.ZERO) == 0) {
                                continue;
                        }
                        break;
                }


                FileOutputStream outFileOutputStream =
                                new FileOutputStream(place_of_digital_signature__File);
                ObjectOutputStream objectOutputStream =
                                new ObjectOutputStream(outFileOutputStream);

                objectOutputStream.writeObject(rBI);
                objectOutputStream.writeObject(sBI);

                outFileOutputStream.close();
        }


        public boolean check_digital_signature(
                        File messageFile,
                        File place_of_digital_signature__File,
                        Gost сreator_digital_signature__Gost
        ) throws NoSuchAlgorithmException, IOException, ClassNotFoundException {

                FileInputStream place_of_digital_signature__FileInputStream =
                                new FileInputStream(place_of_digital_signature__File);
                ObjectInputStream inObjectInputStream =
                                new ObjectInputStream(place_of_digital_signature__FileInputStream);

                BigInteger rBI = (BigInteger) inObjectInputStream.readObject();
                BigInteger sBI = (BigInteger) inObjectInputStream.readObject();

                place_of_digital_signature__FileInputStream.close();




                MessageDigest md5Digest = MessageDigest.getInstance("MD5");
                byte[] checksum__Arr_byte = getFileChecksum(md5Digest, messageFile);
                BigInteger checksumBI = new BigInteger(checksum__Arr_byte);
                // checksumBI = BigInteger.valueOf(3); //DEBUG_DELETE





                //0 < r < q, 0 < s < q
                boolean r_valid__BOOL = BigInteger.ZERO.compareTo(rBI) == -1
                                && rBI.compareTo(this.qBI) == -1;
                boolean s_valid__BOOL = BigInteger.ZERO.compareTo(sBI) == -1
                                && sBI.compareTo(this.qBI) == -1;
                boolean all_is_good__BOOL = r_valid__BOOL && s_valid__BOOL;
                if (!all_is_good__BOOL) {
                        return false;
                }




                BigInteger h_inversinon = checksumBI.modInverse(this.qBI);
                BigInteger u1BI = sBI.multiply(h_inversinon).mod(this.qBI); //u1 = s*h^(-1) mod q,
                BigInteger u2BI = rBI.multiply(BigInteger.valueOf(-1)).multiply(h_inversinon).mod(this.qBI); //u2 = -r*h^(-1) mod q

                BigInteger yBI = сreator_digital_signature__Gost.getyBI(); //v = (a^u1 * y^u2 mod p) mod q
                BigInteger firt_mod_pow__BI = aBI.modPow(u1BI, this.pBI);
                BigInteger second_mod_pow__BI = yBI.modPow(u2BI, this.pBI);
                BigInteger vBI = firt_mod_pow__BI.multiply(second_mod_pow__BI).mod(this.pBI).mod(this.qBI);



                return vBI.compareTo(rBI) == 0; //return v == r
        }




        static public byte[] getFileChecksum(MessageDigest digest, File file) throws IOException {
                //Get file input stream for reading the file content
                FileInputStream fis = new FileInputStream(file);

                //Create byte array to read data in chunks
                byte[] byteArray = new byte[1024];
                int bytesCount = 0;

                //Read file data and update in message digest
                while ((bytesCount = fis.read(byteArray)) != -1) {
                        digest.update(byteArray, 0, bytesCount);
                }
                ;

                //close the stream; We don't need it now.
                fis.close();

                //Get the hash's bytes
                byte[] bytes = digest.digest();

                return bytes;
        }
}
