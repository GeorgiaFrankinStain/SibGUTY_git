package lab_5;

import java.math.BigInteger;

public class Users {
        private ChekingDublicateServer myCheckDublServ;
        private BigInteger my_name__BI;

        public Users(ChekingDublicateServer myCheckDublServ) throws Exception {
                this.myCheckDublServ = myCheckDublServ;
                this.my_name__BI = this.myCheckDublServ.register();
        }

        public boolean vote (int number_of_candidate) {
                return this.myCheckDublServ.vote(my_name__BI, number_of_candidate);
        }
}
