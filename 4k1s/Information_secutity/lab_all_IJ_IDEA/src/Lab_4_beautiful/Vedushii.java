package Lab_4_beautiful;

import lab_1.MyBigInteger;
import lab_1.PrimeNumber;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static Lab_4_beautiful.Player.max_range__BI;

public class Vedushii {
        private BigInteger pBI = null;
        private Player[] allPlayer = null;
        static String[] deck_title__ArrStr = {
                        "Tooz_piki",
                        "Tooz_cresti",
                        "10 piki",
                        "10 cresti",
                        "6 piki",
                        "6 cresti",
                        "2 piki",
                        "2 cresti"
        };










        public void deal_cards(int number_of_players, int how_many_deal) throws Exception {
                //FIXME исключение на то, хватит ли карт игрокам и на прикуп
                this.pBI = PrimeNumber.generate(max_range__BI);
                this.pBI = BigInteger.valueOf(127); //DEBUG_DELETE
                BigInteger[] deckArrBI = create_deck(deck_title__ArrStr.length);
                Map<BigInteger, String> matchint_deck__MapBIStr =
                                matching_deck_with_random_number(deckArrBI, deck_title__ArrStr);
                for (Map.Entry<BigInteger, String> entry : matchint_deck__MapBIStr.entrySet()) {
                        System.out.println(entry.getKey() + " - " + entry.getValue());
                }
                System.out.println("===========================");
                System.out.println();


                this.allPlayer = new Player[number_of_players];
                for (int i = 0; i < number_of_players; i++) {
                        this.allPlayer[i] = new Player(this.pBI);
                }


                for (int i = 0; i < number_of_players; i++) {
                        this.allPlayer[i].shuffle_encrypt_deck(deckArrBI);
                }


                
                for (int i = 0; i < number_of_players; i++) {
                        deckArrBI = this.allPlayer[i].taking_cards_from_an_encrypted_deck(deckArrBI, how_many_deal);
                }

                for (int i = 0; i < number_of_players; i++) {
                        Player currentPlayer = this.allPlayer[i];
                        BigInteger[] crypt_cards_current__Player =
                                        currentPlayer.getMy_crypt_cards__ArrBI();
                        for (int j = 0; j < number_of_players; j++) {
                                if (j != i) {
                                        this.allPlayer[j].decrypt_deck_cards(crypt_cards_current__Player);
                                }
                        }
                        currentPlayer.recognition_their_cards_deck_cards(crypt_cards_current__Player);
                }


                for (int i = 0; i < number_of_players; i++) {
                        BigInteger[] cardsArrBI = this.allPlayer[i].getMy_cards__ArrBI();
                        System.out.println("--" + i);
                        for (int j = 0; j < cardsArrBI.length; j++) {
//                                System.out.println(cardsArrBI[j]);
                                System.out.println(matchint_deck__MapBIStr.get(cardsArrBI[j]));

                        }
                }
        }
        private BigInteger[] create_deck(int length_of_deck) throws Exception {

                BigInteger[] resArrBI = new BigInteger[length_of_deck];

                for (int i = 0; i < length_of_deck; i++) {
                        resArrBI[i] = MyBigInteger.generae_diapason(
                                        BigInteger.valueOf(2),
                                        this.pBI.subtract(BigInteger.valueOf(2))
                        );
//                        resArrBI[i] = BigInteger.valueOf(i + 2); //DEBUG_DELETE
                }




                return resArrBI;
        }
        private Map<BigInteger, String> matching_deck_with_random_number(
                        BigInteger[] deck_rand_number__ArrBI,
                        String[] deck_title__ArrStr
        ) throws Exception {
                if (deck_rand_number__ArrBI.length != deck_title__ArrStr.length) {
                        throw new Exception("the number of random numbers does not match the number of titles");
                }

                Map<BigInteger, String> resMapStrBI = new HashMap<>();
                for (int i = 0; i < deck_rand_number__ArrBI.length; i++) {
                        resMapStrBI.put(deck_rand_number__ArrBI[i], deck_title__ArrStr[i]);
                }

                return resMapStrBI;
        }
}
