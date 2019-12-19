package lab_5;

public class CounterServer {
        private String[] candidateArrStr = {
                        "one",
                        "two",
                        "tree"
        };
        private int[] golosa__Arr_int = null;

        public String[] getcandidateArrStr() {
                return this.candidateArrStr;
        }

        public CounterServer() {
                this.golosa__Arr_int = new int[candidateArrStr.length];
                for (int i = 0; i < this.golosa__Arr_int.length; i++) {
                        this.golosa__Arr_int[i] = 0;
                }
        }

        public void print_candidate() {
                for (int i = 0; i < this.candidateArrStr.length; i++) {
                        System.out.println(candidateArrStr[i]);
                }
        }

        public void print_golosa() {
                for (int i = 0; i < this.candidateArrStr.length; i++) {
                        System.out.println(candidateArrStr[i] + " - " + golosa__Arr_int[i]);
                }
        }
        public void add(int number_of_candidate) {
                this.golosa__Arr_int[number_of_candidate] += 1;
        }
}
