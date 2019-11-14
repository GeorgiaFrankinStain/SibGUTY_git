package lab_1;

import lab_1.SqrtBigInteger;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * y = a^x mod p
 * @return x
 */
public class BabyStepGiantStep {
    public static BigInteger get(
            BigInteger aBI,
            BigInteger yBI,
            BigInteger pBI,
            double count_iteration_costil__Double //при желании можно полиморфизм сделать
    ) {
        BigInteger sqrt_p_BI = SqrtBigInteger.get(pBI).add(BigInteger.ONE);
        BigInteger mBI = sqrt_p_BI;
        BigInteger kBI = sqrt_p_BI;


        //<value_mod, index_in_series_of_numbers>
        Map<BigInteger, BigInteger> aMapBIBI = new HashMap<BigInteger, BigInteger>(); //y,ay,a^2 y,…,a^(m-1) y (mod p)
        //secon sequence a^m,a^2m,…,a^km (mod p)


        BigInteger curretn_a_pow_i_BI = BigInteger.ONE;
        for (
                BigInteger iBI = BigInteger.ZERO;
                iBI.compareTo(mBI) == -1; // i < m
                iBI = iBI.add(BigInteger.ONE)
        ) { //y,ay,a^2 y,…,a^(m-1) y (mod p)
            //current_item = y * a^i mod p
            BigInteger current_item_BI = curretn_a_pow_i_BI.multiply(yBI).mod(pBI);
            aMapBIBI.put(current_item_BI, iBI);

            curretn_a_pow_i_BI = curretn_a_pow_i_BI.multiply(aBI);
            count_iteration_costil__Double++;
        }


        // System.out.println("aMapBIBI: " + aMapBIBI.toString()); //DEBUG_DELETE
        BigInteger a_pow_m_BI = aBI.pow(mBI.intValue());
        BigInteger current_a_pow_m_pow_i_BI = BigInteger.ONE;
        BigInteger next_a_pow_m_pow_i_BI = a_pow_m_BI;
        for (
                BigInteger iBI = BigInteger.ZERO;
                iBI.compareTo(kBI) == -1; // i < m
                iBI = iBI.add(BigInteger.ONE)
        ) { //a^m,a^2m,…,a^km (mod p)
            count_iteration_costil__Double++; //потому что хеш константное время имеет
            //current_item = (a^m)^i(mod p), i = 1 -> k
            BigInteger current_item_BI = current_a_pow_m_pow_i_BI.mod(pBI);
            // System.out.println("current_item_BI + " + current_item_BI); //DEBUG_DELETE


            if (aMapBIBI.containsKey(current_item_BI)) {
                BigInteger jBI = aMapBIBI.get(current_item_BI);
                BigInteger xBI = iBI.multiply(mBI).subtract(jBI);
                if (FastExponentiationModulo.get(aBI, xBI, pBI).compareTo(yBI) == 0) {
                    // System.out.println("xBI: " + xBI); //DEBUG_DELETE
                    return xBI;

                }
            }


            current_a_pow_m_pow_i_BI = next_a_pow_m_pow_i_BI;
            next_a_pow_m_pow_i_BI = next_a_pow_m_pow_i_BI.multiply(a_pow_m_BI);


        }
        // System.out.println(); //DEBUG_DELETE

        return null;
    }

    /*
        int A[m], B[k];
        map<int,int> dict;
        for(int i=0; i<m; ++i)
          dict[A[i]]=i;
        for(int i=0; i<k; ++i)
          if(dict.count(B[i]))
          {
            cout << i << " " << dict[B[i]];
            break;
          }

    */
}
