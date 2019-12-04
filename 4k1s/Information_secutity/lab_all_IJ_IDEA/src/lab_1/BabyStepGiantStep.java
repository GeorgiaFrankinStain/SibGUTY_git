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
        //переменную не написал secon sequence a^m,a^2m,…,a^km (mod p)


        BigInteger curretn_a_pow_j_BI = BigInteger.ONE;

        for (
                BigInteger jBI = BigInteger.ZERO;
                jBI.compareTo(mBI) == -1; // i < m
                jBI = jBI.add(BigInteger.ONE)
        ) { //y,ay,a^2 y,…,a^(m-1) y (mod p) //шаг младенца [0, m)
            //current_item = y * a^i mod p
            BigInteger current_item_BI = curretn_a_pow_j_BI.multiply(yBI).mod(pBI);
            aMapBIBI.put(current_item_BI, jBI);

            curretn_a_pow_j_BI = curretn_a_pow_j_BI.multiply(aBI);
            count_iteration_costil__Double++;
        }


        // System.out.println("aMapBIBI: " + aMapBIBI.toString()); //DEBUG_DELETE

        for (
                BigInteger iBI = BigInteger.ONE;
                (iBI.compareTo(mBI) == -1) || (iBI.compareTo(mBI) == 0); // i < m || i == m не совпадает с псевдокодом из методички
                iBI = iBI.add(BigInteger.ONE)
        ) { //a^m,a^2m,…,a^km (mod p) //шаг великана [1, m]
            count_iteration_costil__Double++; //потому что хеш константное время имеет
            //current_item = (a^m)^i(mod p), i = 1 -> k
            BigInteger current_item_BI = aBI.modPow(mBI.multiply(iBI), pBI);
            // System.out.println("current_item_BI + " + current_item_BI); //DEBUG_DELETE


            if (aMapBIBI.containsKey(current_item_BI)) {
                BigInteger jBI = aMapBIBI.get(current_item_BI);
                BigInteger xBI = iBI.multiply(mBI).subtract(jBI);
//                return xBI;
                if (FastExponentiationModulo.get(aBI, xBI, pBI).compareTo(yBI) == 0) { //КОСТЫЛЬ
                    // System.out.println("xBI: " + xBI); //DEBUG_DELETE
                    return xBI;
                }
            }
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
