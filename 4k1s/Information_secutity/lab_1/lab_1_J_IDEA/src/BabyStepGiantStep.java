import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

/**
 * y = a^x mod p
 * @return x
 */
public class BabyStepGiantStep {
    public static BigInteger get(
            BigInteger aBI,
            BigInteger yBI,
            BigInteger pBI
    ) {
        BigInteger sqrt_p_BI = SqrtBigInteger.get(pBI);
        BigInteger mBI = sqrt_p_BI;
        BigInteger kBI = sqrt_p_BI;


        ArrayList<BigInteger> a_BIArrayList = new ArrayList<BigInteger>(); //y,ay,a^2 y,…,a^(m-1) y (mod p)
        ArrayList<BigInteger> b_BIArrayList = new ArrayList<BigInteger>(); //a^m,a^2m,…,a^km (mod p)


        BigInteger curretn_a_pow_i_BI = BigInteger.ZERO;
        BigInteger next_a_pow_i_BI = aBI;
        for (
                BigInteger iBI = BigInteger.ZERO;
                iBI.compareTo(mBI) == -1; // i < m
                iBI.add(BigInteger.ONE)
        ) { //y,ay,a^2 y,…,a^(m-1) y (mod p)
            //current_item = y * a^i mod p
            a_BIArrayList.add(yBI.multiply(curretn_a_pow_i_BI).mod(pBI));

            curretn_a_pow_i_BI = next_a_pow_i_BI;
            next_a_pow_i_BI = next_a_pow_i_BI.multiply(aBI);
        }



        BigInteger a_pow_m_BI = aBI.pow(mBI.intValue());
        BigInteger current_a_pow_m_pow_i_BI = a_pow_m_BI;
        BigInteger next_a_pow_m_pow_i_BI = a_pow_m_BI.multiply(a_pow_m_BI);
        for (
                BigInteger iBI = BigInteger.ONE;
                iBI.compareTo(kBI) == -1; // i < m
                iBI.add(BigInteger.ONE)
        ) { //a^m,a^2m,…,a^km (mod p)
            //current_item = (a^m)^i(mod p), i = 1 -> k
            b_BIArrayList.add(current_a_pow_m_pow_i_BI);


            current_a_pow_m_pow_i_BI = next_a_pow_m_pow_i_BI;
            next_a_pow_m_pow_i_BI = next_a_pow_m_pow_i_BI.multiply(a_pow_m_BI)


        }




        Collections.sort(a_BIArrayList);
        Collections.sort(b_BIArrayList);



        int iI = 0; //TODO: using int is bad
        int jI = 0;

        while (
                iI < mBI.intValue()
                && jI < kBI.intValue()
        ) {
            int result_of_compare_a_b = a_BIArrayList.get(iI).compareTo(b_BIArrayList.get(jI));
            boolean a_less_b = result_of_compare_a_b == -1;
            boolean a_more_b = result_of_compare_a_b == 1;
            boolean a_equal_b = result_of_compare_a_b == 0;


            switch (result_of_compare_a_b) {
                case -1: //a_less_b
                    iI++;
                    break;
                case 1: //a_more_b
                    jI++;
                    break;
                case 0: //a_equal_b
                    break;
            }
        }
        return xBI;
    }

    /*
        int A[m], B[k];
        sort(A,A+m);
        sort(B,B+k);
        int i=0, j=0;
        while(i<m && j<k)
        {
          if(A[i]<B[j])
            ++i;
          else if(A[i]>B[j])
            ++j;
          else break;
        }
        if(i<m && j<k)
          cout << i << " " << j;

    */
}
