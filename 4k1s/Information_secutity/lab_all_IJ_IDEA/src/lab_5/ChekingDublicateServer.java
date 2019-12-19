package lab_5;

import lab_1.MyBigInteger;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ChekingDublicateServer {
        private CounterServer myCounterServer;

        public ChekingDublicateServer(CounterServer myCounterServer) {
                this.myCounterServer = myCounterServer;
        }

        public BigInteger register() throws Exception {
                return MyBigInteger.generae_diapason(BigInteger.ZERO, BigInteger.valueOf(1000));
        }

        private Map<String, Boolean> testMapBIBool = new HashMap<String, Boolean>();

        public boolean vote(BigInteger nameBI, int number_of_candidate) {
                String md5Str = MyMd5.md5CustomStr(nameBI.toString());
                if (testMapBIBool.containsKey(md5Str)) {
                        return false;
                } else {
                        testMapBIBool.put(md5Str, true);
                        this.myCounterServer.add(number_of_candidate);
                        return true;
                }
        }
}
