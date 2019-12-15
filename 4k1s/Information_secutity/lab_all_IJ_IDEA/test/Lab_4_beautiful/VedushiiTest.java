package Lab_4_beautiful;

import org.junit.Test;

import static org.junit.Assert.*;

public class VedushiiTest {

        @Test
        public void sharedTEST() throws Exception {
                Vedushii tVedushii = new Vedushii();
                tVedushii.deal_cards(3, 2);
        }
}