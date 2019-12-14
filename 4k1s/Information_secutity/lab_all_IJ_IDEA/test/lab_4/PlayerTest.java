package lab_4;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

        @Test
        public void sharedTEST() throws Exception {
                Player firstPlayer = new Player();
                Player secondPlayer = new Player(firstPlayer);
//                Player tretiiPlayer = new Player(firstPlayer);
/*                Player p4Player = new Player(firstPlayer);
                Player p5Player = new Player(firstPlayer);
                Player p6Player = new Player(firstPlayer);
                Player p7Player = new Player(firstPlayer);*/

                firstPlayer.start_deal_cards(1);

                firstPlayer.printMy_cards__ArrBI();
                secondPlayer.printMy_cards__ArrBI();
//                tretiiPlayer.printMy_cards__ArrBI();
/*                p4Player.printMy_cards__ArrBI();
                p5Player.printMy_cards__ArrBI();
                p6Player.printMy_cards__ArrBI();
                p7Player.printMy_cards__ArrBI();*/
        }
}