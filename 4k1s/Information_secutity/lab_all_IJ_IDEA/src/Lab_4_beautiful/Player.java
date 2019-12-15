package Lab_4_beautiful;

import lab_1.PrimeNumber;

import java.math.BigInteger;
import java.util.Map;
import java.util.Random;

public class Player {

        static BigInteger max_range__BI = BigInteger.valueOf(1000);

        private BigInteger pBI = null; //public data
        private BigInteger cBI = null, dBI = null; //secret_data
        private BigInteger[] my_cards__ArrBI = null;
        private BigInteger[] my_crypt_cards__ArrBI = null;





        public BigInteger getpBI() {
                return pBI;
        }

        public BigInteger[] getMy_cards__ArrBI() {
                return my_cards__ArrBI;
        }

        public BigInteger[] getMy_crypt_cards__ArrBI() {
                return my_crypt_cards__ArrBI;
        }





        public Player() throws Exception {
                BigInteger pBI = PrimeNumber.generate(max_range__BI);
                initialization_secret_data(pBI);
        }

        public Player(Player he_set_me__Player) throws Exception {
                this.pBI = he_set_me__Player.getpBI();
                initialization_secret_data(pBI);
        }

        public Player(BigInteger pBI) throws Exception {
                initialization_secret_data(pBI);
        }

        private void initialization_secret_data(
                        BigInteger pBI
        ) throws Exception {
                this.pBI = pBI;
                this.cBI = PrimeNumber.coprime_generate(this.pBI.subtract(BigInteger.ONE), this.max_range__BI);
                this.dBI = cBI.modInverse(this.pBI.subtract(BigInteger.ONE));
//                this.cBI = BigInteger.valueOf(713); //DEBUG_DELETE
//                this.dBI = BigInteger.valueOf(41);
        }


        public void shuffle_encrypt_deck(BigInteger[] deckArrBI) {
                local_shuffling_deck_cards(deckArrBI);
                encrypt_deck_cards(deckArrBI);
        }
        private void local_shuffling_deck_cards(BigInteger[] deckArrBI) {
                for (int i = 0; i < deckArrBI.length; i++) {
                        Random tRand = new Random();
                        int first = tRand.nextInt() % deckArrBI.length;
                        int second = tRand.nextInt() % deckArrBI.length;
                        first = Math.abs(first);
                        second = Math.abs(second);

                        BigInteger tBI = deckArrBI[first];
                        deckArrBI[first] = deckArrBI[second];
                        deckArrBI[second] = tBI;
                }
        }
        public void encrypt_deck_cards(BigInteger[] deckArrBI) {
                mod_pow_deck_cards(deckArrBI, this.cBI);
        }



        public void decrypt_deck_cards(BigInteger[] deckArrBI) {
                mod_pow_deck_cards(deckArrBI, this.dBI);
        }




        public void recognition_their_cards_deck_cards(BigInteger[] deckArrBI) {
                this.my_cards__ArrBI = new BigInteger[deckArrBI.length];
                for (int i = 0; i < deckArrBI.length; i++) {
                        BigInteger currentBI = deckArrBI[i];
                        BigInteger crypt_mess__BI = currentBI.modPow(
                                        this.dBI,
                                        this.pBI
                        );

                        //System.out.println( this.id + " : crypt : " + crypt_mess__BI + " = " + currentBI + " modPow(" + key_crypt__BI  + ", p: " + this.pBI + ")"); //DEBUG_DELETE
                        this.my_cards__ArrBI[i] = crypt_mess__BI;
                }
        }


        private void mod_pow_deck_cards(
                        BigInteger[] deckArrBI,
                        BigInteger key_crypt__BI
        ) {
                for (int i = 0; i < deckArrBI.length; i++) {
                        BigInteger currentBI = deckArrBI[i];
                        BigInteger crypt_mess__BI = currentBI.modPow(
                                        key_crypt__BI,
                                        this.pBI
                        );

                        //System.out.println( this.id + " : crypt : " + crypt_mess__BI + " = " + currentBI + " modPow(" + key_crypt__BI  + ", p: " + this.pBI + ")"); //DEBUG_DELETE
                        deckArrBI[i] = crypt_mess__BI;
                }
        }






        public BigInteger[] taking_cards_from_an_encrypted_deck(
                        BigInteger[] crypt_deck__ArrBI,
                        int how_many_cards_deal) {
                BigInteger[] remainsArrBI = new BigInteger[crypt_deck__ArrBI.length - how_many_cards_deal];
                BigInteger[] my_crypt_cards__ArrBI = new BigInteger[how_many_cards_deal];

                int i = 0;
                for (; i < my_crypt_cards__ArrBI.length; i++) {
                        my_crypt_cards__ArrBI[i] = crypt_deck__ArrBI[i];
                }

                for (int j = 0; i < crypt_deck__ArrBI.length; i++, j++) {
                        remainsArrBI[j] = crypt_deck__ArrBI[i];
                }

                this.my_crypt_cards__ArrBI = my_crypt_cards__ArrBI;
                return remainsArrBI;
        }
}
