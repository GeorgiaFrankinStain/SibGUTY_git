package lab_5;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChekingDublicateServerTest {

        @Test
        public void sharedTEST() throws Exception {
                CounterServer cCounterServer = new CounterServer();
                ChekingDublicateServer cCheckDublServ = new ChekingDublicateServer(cCounterServer);
                Users cUser = new Users(cCheckDublServ);


                assertTrue(cUser.vote(1));
                assertTrue(!cUser.vote(1));


                cCounterServer.print_golosa();
                System.out.println("=============");
        }
}