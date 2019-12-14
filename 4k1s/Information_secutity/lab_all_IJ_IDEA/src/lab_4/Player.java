package lab_4;

import javafx.util.Pair;
import lab_1.MyBigInteger;
import lab_1.PrimeNumber;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Player {
        static BigInteger global_maxBI = BigInteger.valueOf(1000);

        static String[] deckArrStr = {
                        "Tooz_piki",
                        "Tooz_cresti",
                        "10 piki",
                        "10 cresti",
                        "6 piki",
                        "6 cresti",
                        "2 piki",
                        "2 cresti"
        };

        Random sRandom = new Random();
        private int id = sRandom.nextInt();


        private BigInteger pBI = null; //public data
        private Map<BigInteger, String> matching_deck__MapBIStr = null;
        private BigInteger cBI = null, dBI = null; //secret_data

        private Player nextPlayer = null;

        private BigInteger[] my_cards__ArrBI = null;

        public void printMy_cards__ArrBI() {
                if (this.my_cards__ArrBI != null) {
                        System.out.println("--------------------" + this.id);
                        for (int j = 2; j < this.my_cards__ArrBI.length; j++) {
                                System.out.print(this.my_cards__ArrBI[j].toString());
                                System.out.print("   ");
                                System.out.print(this.matching_deck__MapBIStr.get(this.my_cards__ArrBI[j]));
                                System.out.println();
                        }
                } else {
                        System.out.println(this.my_cards__ArrBI + " (Is null)");
                }
        }

public BigInteger getpBI() {
                return pBI;
        }

        public Map<BigInteger, String> getmatching_deck__MapBIStr() {
                return matching_deck__MapBIStr;
        }

        public void setNextPlayer(Player nextPlayer) {
                this.nextPlayer = nextPlayer;
        }

        public Player getNextPlayer() {
                return nextPlayer;
        }

        public BigInteger[] getFull_deck__ArrBI() {
                return full_deck__ArrBI;
        }

        private BigInteger[] full_deck__ArrBI = null;
        public void start_deal_cards(int how_many_cards_deal) throws Exception {

                //FIXME ислючение на то, что не хватит всем карт или размер раздачи больше колоды
                //FIXME тут надо делать механизм подсчета количества игроков, а мне нехочется




                //шифруем и тусуем
                _continue_shuffling_deck_cards(this.id, this.full_deck__ArrBI);

                //раздаем и дешифруем
                _continue_deal_cars(how_many_cards_deal, this.full_deck__ArrBI);



                System.out.println("%%%%%%%%%%%%%%%");
                System.out.println("count_create_deck: " + this.count_create_deck);
        }
        public void _continue_deal_cars(
                        int how_many_cards_deal,
                        BigInteger[] deckArrBI
        ) {
                BigInteger[] my_cards__ArrBI = new BigInteger[how_many_cards_deal];

                int i = 0;
                for (; i < how_many_cards_deal; i++) {
                        my_cards__ArrBI[i] = deckArrBI[i];
                }




                int remainder_size = deckArrBI.length - how_many_cards_deal;
                if (remainder_size > 0) {
                        BigInteger[] remainderArrBI = new BigInteger[remainder_size];

                        for (int j = 0; i < deckArrBI.length; i++, j++) {
                                remainderArrBI[j] = deckArrBI[i];
                        }


                        this.nextPlayer._continue_deal_cars(
                                        how_many_cards_deal,
                                        remainderArrBI
                        );
                }


                this.my_cards__ArrBI = my_cards__ArrBI;


                if (!flag_deal_cards) {
                        this.nextPlayer._decrypt_cars(this.id, this.my_cards__ArrBI);
                        this.flag_deal_cards = true;
                }
        }
        private boolean flag_deal_cards = false;
        public void _decrypt_cars(
                        int id,
                        BigInteger[] cardsArrBI
        ) {
                for (int i = 0; i < cardsArrBI.length; i++) {
                        BigInteger ishod_value__BI = cardsArrBI[i];
                        BigInteger decryptBI = cardsArrBI[i].modPow(this.dBI, this.pBI);
                        cardsArrBI[i] = decryptBI;
                        System.out.println(this.id + " : decrypt : " + decryptBI + " = " + ishod_value__BI + " modPow(" + this.dBI + ", p: " + this.pBI + ")");
                }


                boolean back_where_you_came_from_and_do_the_last_decryption_with_your_key__BOOL =
                                this.id == id;

                if (!back_where_you_came_from_and_do_the_last_decryption_with_your_key__BOOL) {
                        this.nextPlayer._decrypt_cars(id, cardsArrBI);
                }

        }

















        public Player() throws Exception {
//                this.pBI = MyBigInteger.generae_diapason(BigInteger.valueOf(3) , global_maxBI);//я смотрел плакал блять, даун
                this.pBI = PrimeNumber.generate(global_maxBI);
                _initialization_secret_data(pBI,null, null);
        }

        public Player(Player he_set_me__Player) throws Exception {
                _initialization_secret_data(
                                he_set_me__Player.getpBI(),
                                he_set_me__Player.getFull_deck__ArrBI(),
                                he_set_me__Player.getmatching_deck__MapBIStr()
                );




                boolean is_first_item_in_the_circle__BOOL =
                                he_set_me__Player.getNextPlayer() == null;

                if (is_first_item_in_the_circle__BOOL) {
                        he_set_me__Player.setNextPlayer(this);
                        this.nextPlayer = he_set_me__Player;
                } else {
                        this.nextPlayer = he_set_me__Player.getNextPlayer();
                        he_set_me__Player.setNextPlayer(this);
                }
        }


        private void _initialization_secret_data(
                        BigInteger pBI,
                        BigInteger[] full_deck__ArrBI,
                        Map<BigInteger, String> matching_deck__MapBIStr
        ) throws Exception {
                this.pBI = pBI;
                if (matching_deck__MapBIStr == null) {
                        this.full_deck__ArrBI = create_deck();
                } else {
                        this.full_deck__ArrBI = full_deck__ArrBI;
                        this.matching_deck__MapBIStr = matching_deck__MapBIStr;
                }
//                this.cBI = PrimeNumber.coprime_generate(this.pBI.subtract(BigInteger.ONE), this.pBI);
                this.cBI = PrimeNumber.coprime_generate(this.pBI.subtract(BigInteger.ONE), this.global_maxBI);
                this.dBI = cBI.modInverse(this.pBI.subtract(BigInteger.ONE));


                System.out.println("id: " + this.id + " cBI: " + this.cBI + " dBI: " + this.dBI);
                System.out.println("++++++++++++");
        }
















/*
        public void start_shuffle_deck_cards() throws Exception {
        }*/
        static private int count_create_deck = 0;
        public BigInteger[] create_deck() throws Exception {
                this.count_create_deck++; //DEBUG_DELETE

                BigInteger[] resArrBI = new BigInteger[deckArrStr.length];
                Map<BigInteger, String> resMapStrBI = new HashMap<>();
                for (int i = 0; i < deckArrStr.length; i++) {
//                        resArrBI[i] = MyBigInteger.generae_diapason(BigInteger.ONE, this.pBI);
                        resArrBI[i] = BigInteger.valueOf(i + 1);
//                        resArrBI[i] = MyBigInteger.generae_diapason(BigInteger.ONE, global_maxBI); //просто слезки кап кап
                        resMapStrBI.put(resArrBI[i], deckArrStr[i]);
                }


                this.matching_deck__MapBIStr = resMapStrBI;





                //DEBUG_DELETE_BLOCK_START
                for (Map.Entry<BigInteger,String> entry : this.matching_deck__MapBIStr.entrySet()) {
                        BigInteger key = entry.getKey();
                        String value = entry.getValue();
                        // do stuff
                        System.out.println(key.toString() + " -> " + value);
                }
                System.out.println("=====================");
                //DEBUG_DELETE_BLOCK_END


                return resArrBI;

        }
        private boolean was_here__BOOL = false;
        public void _continue_shuffling_deck_cards(
                        int id,
                        BigInteger[] deckArrBI
        ) throws Exception {
                boolean passed_one_circle__BOOL = this.id == id && this.was_here__BOOL;
                this.was_here__BOOL = true;
                if (!passed_one_circle__BOOL) {
                        _local_shuffling_deck_cards(deckArrBI);
                        crypt_deck_cards(deckArrBI, this.cBI);
                        nextPlayer._continue_shuffling_deck_cards(id, deckArrBI);
                } else {
                        this.was_here__BOOL = false;
                }
        }
        private void _local_shuffling_deck_cards(BigInteger[] deckArrBI) {
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
        private void crypt_deck_cards(BigInteger[] deckArrBI, BigInteger key_crypt__BI) {
                for (int i = 0; i < deckArrBI.length; i++) {
                        BigInteger currentBI = deckArrBI[i];
                        BigInteger crypt_mess__BI = currentBI.modPow(
                                        key_crypt__BI,
                                        this.pBI
                        );

                        System.out.println( this.id + " : crypt : " + crypt_mess__BI + " = " + currentBI + " modPow(" + key_crypt__BI  + ", p: " + this.pBI + ")");
                        deckArrBI[i] = crypt_mess__BI;
                }
        }
}
